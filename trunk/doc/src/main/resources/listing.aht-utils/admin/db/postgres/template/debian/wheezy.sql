createdb template_postgis
createlang plpgsql template_postgis
psql -d template_postgis -c "UPDATE pg_database SET datistemplate=true WHERE datname='template_postgis'"
psql -d template_postgis -f /usr/share/postgresql/9.1/contrib/postgis-2.0/postgis.sql
psql -d template_postgis -f /usr/share/postgresql/9.1/contrib/postgis-2.0/spatial_ref_sys.sql
psql -d template_postgis -f /usr/share/postgresql/9.1/contrib/postgis-2.0/postgis_comments.sql