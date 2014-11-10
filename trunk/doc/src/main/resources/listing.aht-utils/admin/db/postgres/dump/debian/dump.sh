#!/bin/sh

file=$(date +"@@@APP@@@.%Y-%m-%d.%H.sql")
pg_dump --blobs --format=c --verbose --file=/home/dev/@@@APP@@@-dump/$file @@@APP@@@