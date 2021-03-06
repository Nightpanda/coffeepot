#! /bin/bash
# First checks that the environmental config file exists and starts Coffeepot with 'lein figwheel'
config=./src/cljs/coffeepot/envconfig.cljs
if [ -e "$config" ]; then
    echo "Environmental config found. Starting coffeepot."
    lein figwheel
else
    echo "Environmental config not found. Please run the initiation script first."
fi
