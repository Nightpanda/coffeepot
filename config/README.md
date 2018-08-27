# Environmental configs

Coffeepot uses environmental configs that for example makes it possible to have a firebase account for development and a separate account for test/prod etc.

In order to use the development configs and run the application locally must be started with the start-dev.sh script.
  > The script copies developers own configs (dev.cljs) from the config folder to /src/cljs/coffeepot as envconfig.cljs

## Firebase

Set up environmental config for development usage:

- In config folder, create a file named dev.cljs
  > coffeepot/config/dev.cljs
- Add the following line declaring the namespace to the top of the file
 ``(ns coffeepot.envconfig)``
- Add the following def with the map to the file:
 ``(def firebase {:apiKey "your-api-key" 
                  :authDomain "your-auth-domain"
                  :databaseURL "your-database-url"})``
- Replace placeholders with your Firebase account details
- Start coffeepot with start-dev.sh
