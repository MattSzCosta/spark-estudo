CREATE KEYSPACE IF NOT EXISTS test WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

CREATE TABLE test.netflix(show_id text, typeMovie text, title text, date_added text, release_year int, rating text, duration text, description text, PRIMARY KEY(show_id));