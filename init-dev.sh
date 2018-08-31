#! /bin/bash
# First checks that the config file for dev environment exists and then copies it to src/cljs/coffeepot
config=./config/dev.cljs
if [ -e "$config" ]; then
    echo "Dev config found. Copying it to coffeepot."
    cp config/dev.cljs src/cljs/coffeepot/envconfig.cljs
else
    echo "Dev config not found. Please add your own env config."
fi
