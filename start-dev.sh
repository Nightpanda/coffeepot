#! /bin/bash
# Improve this starting script by first looking whether the config file exists and copy only if the file exists.
# If the config file is missing it might be best not to start figwheel at all.
cp config/dev.cljs src/cljs/coffeepot/envconfig.cljs
lein figwheel
