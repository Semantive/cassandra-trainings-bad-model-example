package com.semantive.generator.threads;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.semantive.generator.Generator;
import com.semantive.generator.model.Page;
import com.semantive.generator.model.PageView;
import com.semantive.generator.model.UrlSimilarityScore;

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
                Set<String> appKeys = generator.appKeys(APPS_WIH_URLS_NO);

                Set<Page> pages = generator.pages(appKeys,
                        generator.intFromRange(PAGES_PER_APP_KEY_MIN, PAGES_PER_APP_KEY_MAX));

                Set<PageView> views = generator.views(appKeys,
                        generator.intFromRange(VIEWS_PER_APP_KEY_MIN, VIEWS_PER_APP_KEY_MAX));

                Set<UrlSimilarityScore> scores = generator.scores(appKeys,
                        generator.intFromRange(SCORES_PER_APP_KEY_MIN, SCORES_PER_APP_KEY_MAX));

                System.out.println("Data generated");

                PreparedStatement stmtPage = session.prepare("SELECT * " +
                        "FROM pages " +
                        "WHERE cleaned_url = ?");

                PreparedStatement stmtView = session.prepare("SELECT * " +
                        "FROM page_views " +
                        "WHERE app_key = ?");

                PreparedStatement stmtScore = session.prepare("SELECT * " +
                        "FROM url_similarity_score " +
                        "WHERE app_key = ?");

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
            }
        }
        catch(Exception any) {
            System.out.println("Exception: " + any.getMessage());
            throw any;
        }
    }
}
