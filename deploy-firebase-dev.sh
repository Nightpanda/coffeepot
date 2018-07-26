#! /bin/bash
# Script for deploying current local version with dev config to firebase account
cp config/dev.cljs src/cljs/coffeepot/envconfig.cljs
./build_deploy.sh
