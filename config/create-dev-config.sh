#!/bin/bash
currentFolder=$(basename "$PWD")
configFolderName=config
devConfigFileName=dev.cljs

if [ $currentFolder != $configFolderName  ]
then
    echo "Please run this command in the ${configFolderName} folder".
else
    if [ -f $devConfigFileName ]
    then
        echo "${devConfigFileName} found, remove it first if you want a fresh start."
    else
        echo "${devConfigFileName} not found, creating a boilerplate for you!"
        touch dev.cljs
        echo -e '(ns coffeepot.envconfig) \n' > dev.cljs
        echo -e '(def firebase {:apiKey "your-api-key" :authDomain "your-auth-domain" :databaseURL "your-database-url"})' >> dev.cljs
    fi
fi
