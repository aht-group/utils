psql -U postgres -d @@@APP@@@ -c "ALTER TABLE geography_columns OWNER TO @@@APP@@@;"
psql -U postgres -d @@@APP@@@ -c "ALTER TABLE geometry_columns OWNER TO @@@APP@@@;"
psql -U postgres -d @@@APP@@@ -c "ALTER TABLE spatial_ref_sys OWNER TO @@@APP@@@;"