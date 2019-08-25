# Coffeepot
Coffeepot is a social networking service for coffee lovers. With Coffeepot, users can rate and share their coffee experiences.
  > Coffeepot is a social network web app that allows users to post and share their:
  > - brewed coffees
  > - drank coffee (self or barista made)
  > - brew recipes
  > - favorite beans, roasteries, cafes etc.
  >  
  >  Brewing and drinking coffee also earns the users badges/achievements

Coffeepot is made with ClojureScript and Firebase.

## How to participate
### Slack
Join our slack TBD
### Trello

We use Trello to organize our development.

[Coffeepot Trello board](https://trello.com/b/mRJY0Av7/coffeepot)

## Developer guide

### Main technologies

 - ClojureScript
 - Reagent
 - Material UI
 - re-com
 - MySQL

### How to start
#### Important git business
This project probably receives commits and is being worked on with both CRLF and LF line endings. This means that you should set your git config to handle pulling and pushing files with correct line endings for your OS.

```
$ git config --global core.autocrlf true
```

#### Get the codes
- Make sure you have git installed
- Clone the Coffeepot repo to your computer:
	```
	$ git clone xxxxxxxxxxxxx
	```
- We love coffee, but we also love branches so just for the sake of branching:
	```
	$ git checkout -b awesome-dev-branch
	```
#### Install dependencies
```
$ lein install
$ npm init -y
$ npm install cypress --save-dev
```

#### MySQL database

##### Database tool such as DBeaver

First download your choice of database tool with MySQL compatibility. Here we install DBeaver.
DBeaver can be downloaded from: https://dbeaver.io/download/

DBeaver can be started with:
```
$ dbeaver
```

##### Installation

MySQL database can be installed directly on your machine
```
$ ./db/scripts/install-mysql.sh
```
  
or it can be run in a docker container
  
First add yourself to docker group
```
$ sudo groupadd docker
$ sudo usermod -aG docker $USER
$ newgrp docker #OR maybe better that you restart your computer
```

Create and start the database.
```
$ ./db/scripts/create-mysql-docker.sh
``` 

Get into the MySQL and create a database for coffeepot. Default password for user root: root
``` 
$ docker exec -it coffee-devdb mysql -uroot -p
mysql> CREATE DATABASE coffeepot_dev;
``` 

#### Access locally running MySQL database as root.
```
$ mysql -u root -h localhost -p
```

#### Access docker MySQL database as root.
```
$ docker exec -it coffee-devdb mysql -uroot -p
```


##### Setup DBeaver

Driver properties tab and set allowPublicKeyRetrieval to true



#### Migrate database
Going up:
```
$ node_modules/.bin/sequelize db:migrate
```
Undoing migration:
```
$ node_modules/.bin/sequelize db:migrate:undo
```

#### Seed database
Sequelize can be used to seed data to the database.
```
$ node_modules/.bin/sequelize db:seed --seed db/seeders/20190621163301-demo-user.js
$ node_modules/.bin/sequelize db:seed:undo --seed db/seeders/20190621163301-demo-user.js
```

#### Initialize app for development
 > $ ./init-dev.sh

#### Start the app locally
 > $ ./start-coffeepot.sh
 
### Deployment
#### Firebase tools
To deploy the app with Firebase hosting, firebase tools must be installed with npm:
 > $ npm install -g firebase-tools

#### Emulate hosting
To emulate hosting and functions locally
 > $ firebase serve

#### Deploy to Firebase
To build the app and deploy it to your Firebase account's hosting site (asks for firebase login first if you are not logged in)
 > $ build_deploy.sh

#### Deploy Firebase DB rules
To deploy the database rules set in database_rules.json to Firebase
 > $ firebase deploy --only database
 
#### (Optional) Deploy base dataset to DB
*Warning* This will overwrite your entire database.

This is mostly needed for the current cypress tests. You can either set the required data manually
to your own database or tweak your own user-data for example.

To set a base structure for coffeepot in your firebase database.
 > $ firebase database:set / cypress/fixtures/base-setup-database.json
 
Import the needed authed user for Cypress tests by running the following command (this will not overwrite your other authed accounts).
 > $ firebase auth:import cypress/fixtures/firebase-authed-users.json

### Testing
#### Cypress testing
All test related code can be found in the folder cypress
##### Initial folder structure
- fixtures: Contains data to be used in tests
- integration: Tests related to integration with other systems, like firebase authentication
- plugins: to load plugins if needed
- support: supporting functionality for tests

##### Running tests
To start the UI for running cypress tests, run the cypress script defined in project.clj
> $ lein npm run cypress

When prompted for a project, choose the root folder for the coffeepot repository. It will automatically look for the cypress folder and list all the tests for you. You can now run tests one by one or all of them. Have fun with test driven development!
  
### Code analysis
#### Kibit
[Kibit](https://github.com/jonase/kibit) searches code patterns that could be rewritten.

To run kibit code analyzer:
  > $ lein kibit
