#!/bin/bash

# Run:
# bash database/recreate_database.sh

CONTAINER_NAME=techmarket-db
DB_USER=techmarket
DB_PASSWORD=123456
DB_NAME=techmarket

echo "Recreating database"

docker rm -f $CONTAINER_NAME
docker run -e POSTGRES_USER=$DB_USER -e POSTGRES_PASSWORD=$DB_PASSWORD -e POSTGRES_DB=techmarket \
  -p 5432:5432 --name $CONTAINER_NAME -d postgres:9.6.19

echo "Database created"

# Usage:
# docker exec -it techmarket-db bash
# psql -h localhost -U techmarket -d 123456

