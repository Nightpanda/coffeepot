# Environmental configs

Coffeepot uses environmental configs that for example makes it possible to have a firebase account for development and a separate account for test/prod etc.

In order to use the development configs and run the application locally one must first initialize app for development with init-dev.sh script and then start the app with start-coffeepot.sh script.

* init-dev.sh script copies developers own configs (dev.cljs) from the config folder to /src/cljs/coffeepot as envconfig.cljs
* start-coffeepot.sh starts coffeepot app after verifying that env config file exists

## Firebase

Set up environmental config for development usage:

- In config folder, run the script create-dev-config.sh which will
  - In config folder, create a file named dev.cljs
  - Add the following line declaring the namespace to the top of the file
    ``(ns coffeepot.envconfig)``
  - Add the following def with the map to the file:
    ``(def firebase {:apiKey "your-api-key"
                  :authDomain "your-auth-domain"
                  :databaseURL "your-database-url"})``
- Replace placeholders with your Firebase account details
  - Go to your project in [firebase console](https://console.firebase.google.com)
- Initialize dev environment with init-dev.sh
- Start coffeepot with start-coffeepot.sh
