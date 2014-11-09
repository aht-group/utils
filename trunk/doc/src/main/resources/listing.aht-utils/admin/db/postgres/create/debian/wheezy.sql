SELECT pg_terminate_backend(procpid) FROM pg_stat_activity WHERE procpid <> pg_backend_pid() AND datname = '@@@APP@@@';
CREATE USER @@@APP@@@ WITH PASSWORD '@@@APP@@@';
CREATE DATABASE @@@APP@@@ OWNER @@@APP@@@ ENCODING 'UTF8';
GRANT ALL PRIVILEGES ON DATABASE @@@APP@@@ TO @@@APP@@@