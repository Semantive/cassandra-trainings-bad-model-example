package com.semantive.generator.threads;

import com.datastax.driver.core.Session;

abstract class CassandraThread extends Thread {

    protected static Integer APPS_WIH_URLS_NO = 2;
    protected static Integer PAGES_PER_APP_KEY_MIN = 32;
    protected static Integer PAGES_PER_APP_KEY_MAX = 512;
    protected static Integer VIEWS_PER_APP_KEY_MIN = 256 * 4;
    protected static Integer VIEWS_PER_APP_KEY_MAX = 256 * 256;
    protected static Integer SCORES_PER_APP_KEY_MIN = 16;
    protected static Integer SCORES_PER_APP_KEY_MAX = 256;

    final protected Session session;

    protected CassandraThread(final Session session) {
        this.session = session;
    }
}
