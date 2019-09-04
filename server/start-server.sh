#! /bin/bash
# First checks that the environmental config file exists and starts Coffeepot server with 'lein ring server'

# config=./src/clj/coffeepot/envconfig.cljs
# if [ -e "$config" ]; then
#     echo "Environmental config found. Starting coffeepot."
    echo "Starting coffeepot server."
    lein ring server
# else
#     echo "Environmental config not found. Please run the initiation script first."
# fi

### Packaging and running as standalone jar
#```
#lein do clean, ring uberjar
#java -jar target/server.jar
#```
### Packaging as war
#`lein ring uberwar`


