#! /bin/bash
# First checks that the config folder can be found and the file given environment exists and then copies it to src/cljs/coffeepot

env=$1
if [ -z $env ] ; then
    echo "Please provide environment as first argument: dev, test"
else
    config=./config/$env.cljs
    if [ -d "config"  ] && [ -e "$config" ] ; then
        echo "$env config found. Copying it to coffeepot."
        cp config/$env.cljs src/cljs/coffeepot/envconfig.cljs
    else
        echo "$env config not found. Please add your own env config."
        echo "Or you did not run this script at the root folder of the repository."
    fi
fi
