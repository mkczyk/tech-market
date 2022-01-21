#!/bin/bash
set -e

# Example:
# database/restoreBackup.sh
# database/restoreBackup.sh database/backups/db_2022-01-21_22-47-08.gz

CONTAINER_NAME=techmarket-db
DB_USER=techmarket
DB_NAME=techmarket

backupPath=$1
backupFileName=$(basename "$backupPath")

if [ -z "$backupPath" ]; then
  backupDir=$(dirname "$0")/backups
  latestBackupFileName=$(ls -Art $backupDir | tail -n 1)
  echo "You don't pass backup file."
  echo "Found: $(ls -Artm $backupDir)"
  read -p "Are you sure to use latest file: ${latestBackupFileName}? (y/n)? " choice
  if [ "$choice" != "y" ]; then
    exit 0;
  fi
  backupPath=${backupDir}/${latestBackupFileName}
  backupFileName=$latestBackupFileName
fi

read -p "Are you sure to delete database '${DB_NAME}' for restoring backup from file '${backupFileName}' (y/n)? " choice
if [ "$choice" != "y" ]; then
  exit 0;
fi

echo "Restoring backup file: ${backupPath}"
docker cp ${backupPath} techmarket-db:/tmp/${backupFileName}
docker exec -it techmarket-db bash -c "dropdb -U ${DB_USER} ${DB_NAME} && createdb -U ${DB_USER} ${DB_NAME}"
docker exec -it techmarket-db bash -c \
    "gunzip -c /tmp/${backupFileName} | psql --set ON_ERROR_STOP=on -U ${DB_USER} -d ${DB_NAME}"
echo "Restored backup file: ${backupPath}"
