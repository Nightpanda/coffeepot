#! /bin/bash
# First checks that the config folder can be found and the file for dev environment exists and then copies it to src/cljs/coffeepot
config=./config/dev.cljs
if [ -d "config"  ] && [ -e "$config" ] ; then
    echo "Dev config found. Copying it to coffeepot."
    cp config/dev.cljs src/cljs/coffeepot/envconfig.cljs
else
    echo "Dev config not found. Please add your own env config."
    echo "Or you did not run this script at the root folder of the repository."
fi
