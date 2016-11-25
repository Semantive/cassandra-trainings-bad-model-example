package com.semantive.generator;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.semantive.generator.model.Page;
import com.semantive.generator.model.PageView;
import com.semantive.generator.model.UrlSimilarityScore;

import java.util.Set;

public class App {

    private static Integer APPS_WIH_URLS_NO = 2;
    private static Integer PAGES_PER_APP_KEY_MIN = 32;
    private static Integer PAGES_PER_APP_KEY_MAX = 512;
    private static Integer VIEWS_PER_APP_KEY_MIN = 256 * 4;
    private static Integer VIEWS_PER_APP_KEY_MAX = 256 * 256;
    private static Integer SCORES_PER_APP_KEY_MIN = 16;
    private static Integer SCORES_PER_APP_KEY_MAX = 256;

    public static void main(String[] args) {

        if(args.length < 3) {
            System.out.println("Parameters: <host> <keyspace> <number of iterations> " +
                    "<number of apps> <number of pages> <number of visits>");
            return;
        }

        String host = args[0];
        String keyspace = args[1];
        Integer iterations = Integer.parseInt(args[2]);

        Cluster cluster = null;
        Session session = null;

        try {
            Generator generator = new Generator();

            cluster = Cluster.builder()
                    .addContactPoint(host)
                    .build();

            session = cluster.connect(keyspace);

            System.out.println("Connected to C* server");

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

            for(int i = 0; i < iterations; ++ i) {
                System.out.println("ITR: " + i);

                Integer apps = (args.length > 3)
                    ? Integer.parseInt(args[3])
                    : APPS_WIH_URLS_NO;

                Integer pagesNo = (args.length > 4)
                    ? Integer.parseInt(args[4])
                    : generator.intFromRange(PAGES_PER_APP_KEY_MIN, PAGES_PER_APP_KEY_MAX);

                Integer viewsNo = (args.length > 5)
                    ? Integer.parseInt(args[5])
                    : generator.intFromRange(VIEWS_PER_APP_KEY_MIN, VIEWS_PER_APP_KEY_MAX);

                Integer scoresNo = (args.length > 6)
                    ? Integer.parseInt(args[6])
                    : generator.intFromRange(SCORES_PER_APP_KEY_MIN, SCORES_PER_APP_KEY_MAX);

                Set<String> appKeys = generator.appKeys(apps);
                Set<Page> pages = generator.pages(appKeys, pagesNo);
                Set<PageView> views = generator.views(appKeys, viewsNo);
                Set<UrlSimilarityScore> scores = generator.scores(appKeys, scoresNo);

                System.out.println("Data generated");

                for (Page page : pages) {
                    session.execute(stmtPage.bind(
                        page.getCleanedUrl(), page.getAppKey(), page.getAttributes(), page.getCreatedAt(),
                        page.getImageUrl(), page.getKeywords(), page.getRawHtml(), page.getRecommendable(),
                        page.getContent(), page.getContentTruncated(), page.getTitle(), page.getUrl()
                    ));
                }

                System.out.println("Pages stored.");

                for (PageView view : views) {
                    session.execute(stmtView.bind(
                        view.getAppKey(), view.getId(), view.getAttSec(), view.getB(),
                        view.getClientTimestamp(), view.getIp(), view.getRawUrl(), view.getReceivedAt(),
                        view.getSessionId(), view.getUid(), view.getUrl(),
                        view.getUseragent()
                    ));
                }

                System.out.println("Views stored.");

                for (UrlSimilarityScore score: scores) {
                    session.execute(stmtScore.bind(
                        score.getAppKey(), score.getUrl1(), score.getUrl2(), score.getSimScore()
                    ));
                }

                System.out.println("Scores stored.");
            }
        }
        catch(Exception any) {
            System.out.println("Error happened: " + any.getMessage());
        }
        finally {
            if(cluster != null)
                cluster.close();
        }
    }
}
