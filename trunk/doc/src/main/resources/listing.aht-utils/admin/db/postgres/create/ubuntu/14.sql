CREATE USER **dbName** WITH PASSWORD '**dbName**';
CREATE DATABASE **dbName** OWNER **dbName** ENCODING 'UTF8';
GRANT ALL PRIVILEGES ON DATABASE **dbName** TO **dbName**;
psql -U postgres -d **dbName** -f /usr/share/postgresql/9.3/contrib/postgis-2.1/postgis.sql
psql -U postgres -d **dbName** -f /usr/share/postgresql/9.3/contrib/postgis-2.1/postgis_comments.sql
psql -U postgres -d **dbName** -f /usr/share/postgresql/9.3/contrib/postgis-2.1/spatial_ref_sys.sql