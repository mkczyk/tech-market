#!/bin/bash
set -e

# Example:
# database/runDatabase.sh
# database/runDatabase.sh -r

CONTAINER_NAME=techmarket-db
DB_USER=techmarket
DB_PASSWORD=123456
DB_NAME=techmarket

# Usage database after creating/starting:
# docker exec -it techmarket-db bash
# psql -h localhost -U techmarket -d 123456
# or one-liner:
# docker exec -it techmarket-db psql -d techmarket -U techmarket -c "select id, name, date from batches"

function get_status {
  local status=$(docker container inspect -f '{{.State.Status}}' $CONTAINER_NAME)
  echo ${status:-"non-existent"}
}

function run_container {
  local status=$(get_status)

  if [ "$status" == "running" ]; then
    echo "${CONTAINER_NAME} is running"
    exit 0
  fi

  echo "Status ${CONTAINER_NAME}: ${status}"

  if [ "$status" == "exited" ]; then
    echo "Starting ${CONTAINER_NAME}..."
    docker start $CONTAINER_NAME
    exit 0
  fi
}

function recreate {
  read -p "Are you sure to delete database ${DB_NAME} (y/n)? " choice
  if [ "$choice" != "y" ]; then
    exit 0;
  fi

  echo "Recreating database ${DB_NAME}"
  docker rm -f $CONTAINER_NAME
  docker run -e POSTGRES_USER=$DB_USER -e POSTGRES_PASSWORD=$DB_PASSWORD -e POSTGRES_DB=techmarket \
    -p 5432:5432 --name $CONTAINER_NAME -d postgres:9.6.19
  echo "Database created"
}

while getopts ":r" opt; do
  case $opt in
    r)
      recreate
      exit 0
    ;;
    \?)
      echo "Invalid option -$OPTARG" >&2
      exit 1
    ;;
  esac
done

if [ $# -eq 0 ]; then
    run_container
    exit 0
fi

