#!/bin/bash
#Script for creating mysql locally

#Checks whether mysql is installed. If mysql is found, starts local database
echo "Checking whether MySQL is already installed."
if [ -x "$(command -v mysql)" ]; then
    echo "MySQL found"
    UP=$(pgrep mysql | wc -l);
    if [ "$UP" -ne 1 ];
    then
        echo "Starting local MySQL.";
        sudo service mysql start
    else
        echo "Local MySQL database already running."
    fi
else
    echo "Install MySQL first"
fi
