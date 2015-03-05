psql -c "CREATE USER @@@APP@@@ WITH PASSWORD '@@@APP@@@';"
psql -c "CREATE DATABASE @@@APP@@@ OWNER @@@APP@@@ TEMPLATE template_postgis ENCODING 'UTF8';"
psql -c "GRANT ALL PRIVILEGES ON DATABASE @@@APP@@@ TO @@@APP@@@;"