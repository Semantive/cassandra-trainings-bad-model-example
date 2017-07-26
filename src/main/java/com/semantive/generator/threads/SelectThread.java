package com.semantive.generator.threads;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.semantive.generator.Generator;
import com.semantive.generator.model.Page;
import com.semantive.generator.model.PageView;
import com.semantive.generator.model.UrlSimilarityScore;

import java.util.HashSet;
import java.util.Set;

public class SelectThread extends CassandraThread {

    public SelectThread(final Session session) {
        super(session);
    }

    @Override
    public void run() {
        try {
            Generator generator = new Generator();

            while(true) {
                Integer threshold = generator.intFromRange(0, 64);
                Set<String> appKeys = new HashSet<>();

                for(String appKey : Generator.appKeys.keySet()) {
                    if(Generator.appKeys.get(appKey) >= threshold) {
                        System.out.println("Will get: " + appKey);
                        appKeys.add(appKey);
                    }
                }

                for(String appKey: appKeys) {

                    Set<Page> pages = generator.pages(appKey,
                            generator.intFromRange(PAGES_PER_APP_KEY_MIN, PAGES_PER_APP_KEY_MAX) / 4);

                    Set<PageView> views = generator.views(appKey,
                            generator.intFromRange(VIEWS_PER_APP_KEY_MIN, VIEWS_PER_APP_KEY_MAX) / 4);

                    Set<UrlSimilarityScore> scores = generator.scores(appKey,
                            generator.intFromRange(SCORES_PER_APP_KEY_MIN, SCORES_PER_APP_KEY_MAX) / 4);

                    System.out.println("Data generated: " + threshold);

                    PreparedStatement stmtPage = session
                        .prepare("SELECT * FROM pages WHERE cleaned_url = ?")
                        .setConsistencyLevel(ConsistencyLevel.ONE);

                    PreparedStatement stmtView = session
                        .prepare("SELECT * FROM page_views WHERE app_key = ?")
                        .setConsistencyLevel(ConsistencyLevel.ONE);

                    PreparedStatement stmtScore = session
                        .prepare("SELECT * FROM url_similarity_score WHERE app_key = ?")
                        .setConsistencyLevel(ConsistencyLevel.ONE);

                    for (Page page : pages) {
                        session.execute(stmtPage.bind(page.getCleanedUrl()));
                    }

                    System.out.println("Pages queried.");

                    for (PageView view : views) {
                        session.execute(stmtView.bind(view.getAppKey()));
                    }

                    System.out.println("Views queries.");

                    for (UrlSimilarityScore score : scores) {
                        session.execute(stmtScore.bind(score.getAppKey()));
                    }

                    System.out.println("Scores queried for iteration.");
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
