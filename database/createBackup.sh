#!/bin/bash
set -e

# Example:
# database/createBackup.sh

CONTAINER_NAME=techmarket-db
DB_USER=techmarket
DB_NAME=techmarket

# Usage database:
# docker exec -it techmarket-db bash

formattedDate=$(date '+%Y-%m-%d_%H-%M-%S')
fileName=db_${formattedDate}.gz
backupDir=$(dirname "$0")/backups

echo "Creating backup file: ${fileName}"
mkdir -p ${backupDir}
docker exec -it techmarket-db bash -c "pg_dump -U ${DB_USER} -d ${DB_NAME} | gzip > /tmp/${fileName}"
docker cp techmarket-db:/tmp/${fileName} ${backupDir}/${fileName}
docker exec -it techmarket-db bash -c "rm /tmp/${fileName}"
ls -lah ${backupDir}/${fileName}
echo "Created backup file: ${backupDir}/${fileName}"
