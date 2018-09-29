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
#### Install dependencies
```
$ lein install
$ lein npm install
```

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

#### Make sure the project is active for Firebase CLI      

List available Firebase projects
 > $ firebase list

Show active project
 > $ firebase use

If no project is currently active or the active project is not the correct one, activate another project
 > $ firebase use my-project-id

#### Set up environmental config for Firebase account

[Environmental configs for Firebase](config/README.md#firebase)

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
