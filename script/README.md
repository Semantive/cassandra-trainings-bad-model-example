# Scripts for C* schema (and optionally data) setup
Schema creation is supported in two different ways: using CQL shell (and creating only the structure) or by cassandra-stress-test tool (along with populating the tables).

## Using cqlsh
Use `cqlsh -f schema.overload.cql` to load CQL schema. This import will not create any data inside the structure (only keyspace and tables).

## Using cassandra-stress-test
1. Load `pages` table using test tool, eg: 
  `cassandra-stress "user profile=file://.../stress-pages.yml ops(insert=1, single=1, full-row=1) n=1000000000"`
2. Load `page_views` table, eg. 
  `cassandra-stress "user profile=file://.../stress-page-views.yml ops(insert=1, single=1, full-row=1) n=1000000000"`
3. Load `url_similarity_score` table, eg. 
  `cassandra-stress "user profile=file://.../stress-urs.yml ops(insert=1, full-row=1) n=1000000000"` 
  (no `single` SELECT test here!)

Make sure to replace `...` with actual directory containing .yml files. This import will create tables and populate them, number of created object is controlled via `n` parameter.
