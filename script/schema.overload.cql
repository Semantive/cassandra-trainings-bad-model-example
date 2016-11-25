CREATE KEYSPACE IF NOT EXISTS overload WITH replication = {
    'class': 'SimpleStrategy', 
    'replication_factor': '1'
} AND durable_writes = true;

CREATE TABLE overload.page_views (
    app_key text,
    id timeuuid,
    att_sec int,
    b int,
    client_timestamp timestamp,
    ip inet,
    raw_url text,
    received_at timestamp,
    session_id text,
    uid text,
    url text,
    useragent text,
    PRIMARY KEY (app_key, id)
) WITH CLUSTERING ORDER BY (id ASC);

CREATE TABLE overload.pages (
    cleaned_url text,
    app_key text,
    attributes text,
    created_at timestamp,
    image_url text,
    keywords text,
    raw_html text,
    recommendable boolean,
    text_content text,
    text_content_trunc text,
    title text,
    url text,
    PRIMARY KEY (cleaned_url, app_key)
) WITH CLUSTERING ORDER BY (app_key ASC);

CREATE TABLE overload.url_similarity_score (
    app_key text,
    url1 text,
    sim_score float,
    url2 text,
    PRIMARY KEY (app_key, url1, sim_score, url2)
) WITH CLUSTERING ORDER BY (url1 DESC, sim_score DESC, url2 ASC);
