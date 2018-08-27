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
#### Development

Install dependencies
$ lein install
$ lein npm install
Start local development server
$ lein figwheel

#### Cypress testing
$ lein npm install
$ lein npm run cypress

#### Local hosting to test production like environment
$ firebase serve

#### Set up Firebase account

 1. Login and open the developer console at [Google Firebase](https://firebase.google.com/)
 2. Add a project
    - Give your project a name and click 'Continue' and the click 'Create project'
    - When your project has been created click 'Continue' and the project overview opens up
 3. Select 'Authentication' from the menu on the left
 4. Click 'Set up sign-in method'
 5. Select sign-in methods
    - Enable Email/Password
    - Enable Google
 6. Select 'Database' from the menu on the left
 7. Scroll down to 'Realtime Database' and click 'Create database'
 8. Select 'Start in test mode' and click 'Enable'
 9. Select 'Hosting' from the menu on the left
10. Install Firebase tools
	```
	$ npm install -g firebase-tools
	```
11. After installing Firebase command line tools, click 'Continue'
12. Finish setting up hosting
    - Sign in to Google
	    ```
		$ firebase login
		```
    - Initiate your project
	    ```
		$ firebase init
		```
13. Get your project details
    - Select 'Authentication' from the menu on the left
    - Click 'Web setup'
    - At least make a mental note of
      - apiKey
      - authDomain
      - databaseURL

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
