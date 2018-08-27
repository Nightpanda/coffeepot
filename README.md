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
 - Firebase (hosting and database)

### How to start

## Development

$ lein npm install
$ lein figwheel

## Cypress testing
$ lein npm install
$ lein npm run cypress

## Local hosting to test production like environment
$ firebase serve

#### Install dependencies

TBD

#### Set up environmental config for Firebase account

[Environmental configs for Firebase](config/README.md#firebase)

#### Start the app
Start the app locally for development with the startup script start-dev.sh

### Deployment
Builds cljs and deploys with firebase, asks for firebase login first if you are not logged in.
$ build_deploy.sh
#### Firebase
 
To deploy the app with Firebase hosting, firebase tools must be installed with npm:
   > $ npm install -g firebase-tools

To build and deploy the app, run the build_deploy.sh script. The script builds cljs app and deploys it with firebase. If you haven't logged in to Firebase, you are first prompted to login.
  >$ build_deploy.sh
