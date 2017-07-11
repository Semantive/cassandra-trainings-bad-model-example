package com.semantive.generator.threads;

import com.datastax.driver.core.Session;

abstract class CassandraThread extends Thread {

    protected static Integer PAGES_PER_APP_KEY_MIN = 8;
    protected static Integer PAGES_PER_APP_KEY_MAX = 64;
    protected static Integer VIEWS_PER_APP_KEY_MIN = 256;
    protected static Integer VIEWS_PER_APP_KEY_MAX = 512;
    protected static Integer SCORES_PER_APP_KEY_MIN = 2;
    protected static Integer SCORES_PER_APP_KEY_MAX = 8;

    final protected Session session;

    protected CassandraThread(final Session session) {
        this.session = session;
    }
}
