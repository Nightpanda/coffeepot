#! /bin/bash
# First checks that the environmental config file exists and starts Coffeepot with 'lein dev'

#Check for database in local MySQL
DATABASE_NAME="coffeepot_development"
RESULT=`mysqlshow --user=root --password=mysql $DATABASE_NAME | grep -o $DATABASE_NAME`
if [ "$RESULT" == "$DATABASE_NAME" ]; then
    echo "Local database seems fine, moving on."
else
    echo "Oh boy, things going bad."
    echo "Attempting to create $DATABASE_NAME database. Give local MySQL root user password."
    echo "create database $DATABASE_NAME" | mysql -u root -p
fi

config=./src/cljs/coffeepot/envconfig.cljs
if [ -e "$config" ]; then
    echo "Environmental config found. Starting coffeepot."
    lein dev
else
    echo "Environmental config not found. Please run the initiation script first."
fi
