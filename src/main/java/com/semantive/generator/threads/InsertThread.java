package com.semantive.generator.threads;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.semantive.generator.Generator;
import com.semantive.generator.model.Page;
import com.semantive.generator.model.PageView;
import com.semantive.generator.model.UrlSimilarityScore;

import java.util.HashSet;
import java.util.Set;

import static sun.misc.Version.println;

public class InsertThread extends CassandraThread {

    public InsertThread(final Session session) {
        super(session);
    }

    @Override
    public void run() {
        try {
            Generator generator = new Generator();

            while (true) {
                Integer threshold = generator.intFromRange(0, 64);
                Set<String> appKeys = new HashSet<>();

                for(String appKey : Generator.appKeys.keySet()) {
                    if(Generator.appKeys.get(appKey) >= threshold) {
                        System.out.println("Will add: " + appKey);
                        appKeys.add(appKey);
                    }
                }

                for(String appKey: appKeys) {
                    Set<Page> pages = generator.pages(appKey,
                            generator.intFromRange(PAGES_PER_APP_KEY_MIN, PAGES_PER_APP_KEY_MAX));

                    Set<PageView> views = generator.views(appKey,
                            generator.intFromRange(VIEWS_PER_APP_KEY_MIN, VIEWS_PER_APP_KEY_MAX));

                    Set<UrlSimilarityScore> scores = generator.scores(appKey,
                            generator.intFromRange(SCORES_PER_APP_KEY_MIN, SCORES_PER_APP_KEY_MAX));

                    System.out.println("Data generated: " + threshold);

                    PreparedStatement stmtPage = session.prepare("INSERT INTO pages (" +
                            "cleaned_url, app_key, attributes, created_at, " +
                            "image_url, keywords, raw_html, recommendable, " +
                            "text_content, text_content_trunc, title, url)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                    PreparedStatement stmtView = session.prepare("INSERT INTO page_views (" +
                            "app_key, id, att_sec, b, " +
                            "client_timestamp, ip, raw_url, received_at, " +
                            "session_id, uid, url, " +
                            "useragent) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                    PreparedStatement stmtScore = session.prepare("INSERT INTO url_similarity_score (" +
                            "app_key, url1, url2, sim_score) VALUES (?, ?, ?, ?)");

                    for (Page page : pages) {
                        session.execute(stmtPage.bind(
                                page.getCleanedUrl(), page.getAppKey(), page.getAttributes(), page.getCreatedAt(),
                                page.getImageUrl(), page.getKeywords(), page.getRawHtml(), page.getRecommendable(),
                                page.getContent(), page.getContentTruncated(), page.getTitle(), page.getUrl()
                        ));
                    }

                    System.out.println("Pages stored: " + pages.size());

                    for (PageView view : views) {
                        session.execute(stmtView.bind(
                                view.getAppKey(), view.getId(), view.getAttSec(), view.getB(),
                                view.getClientTimestamp(), view.getIp(), view.getRawUrl(), view.getReceivedAt(),
                                view.getSessionId(), view.getUid(), view.getUrl(),
                                view.getUseragent()
                        ));
                    }

                    System.out.println("Views stored: " + views.size());

                    for (UrlSimilarityScore score : scores) {
                        session.execute(stmtScore.bind(
                                score.getAppKey(), score.getUrl1(), score.getUrl2(), score.getSimScore()
                        ));
                    }

                    System.out.println("Scores stored for iteration: " + scores.size());

                    sleep(1000);
                }
            }
        }
        catch(InterruptedException e) {
            System.out.println("InterruptedException: " + e.getMessage());
        }
        catch(Exception any) {
            System.out.println("Exception: " + any.getMessage());
            throw any;
        }
    }
}
