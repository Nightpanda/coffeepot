#!/bin/bash
#Script for creating mysql docker

#Checks whether docker is installed. If docker is found, pulls image and starts docker database
echo "Checking whether docker is already installed."
if [ -x "$(command -v docker)" ]; then
    if [[ "$(command docker -v 2> /dev/null)" == *"Docker version"* ]]; then
        echo "Docker found"
        echo "Checking whether MySQL image is already downloaded"
        if [[ "$(docker images -q mysql/mysql-server:5.7 2> /dev/null)" == "" ]]; then
            echo "MySQL docker image mysql-server not found. Downloading image."
            docker pull mysql/mysql-server:5.7
        else
            echo "MySQL docker image mysql-server found."
        fi
        if [[ "$(docker inspect -f '{{.State.Running}}' coffee-devdb 2> /dev/null)" == "true" ]]; then
            echo "Docker MySQL database already running."
        else
            echo "Starting MySQL docker"
            docker run --name=coffee-devdb -p 3306:3306 -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=root -d mysql/mysql-server:5.7
        fi
    else
        echo "Docker found but $USER does not have permission to run docker. Check README.md for help."
    fi

else
    echo "Install docker first"
fi
