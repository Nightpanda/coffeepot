# Coffeepot Client

## Developer Guide

### Install dependencies
```
$ lein install
```

### Initialize app for development
 > $ ./init-dev.sh

### Start the app locally
 > $ ./start-coffeepot.sh
 
### Testing
#### Cypress testing
All test related code can be found in the folder cypress
##### Initial folder structure
- fixtures: Contains data to be used in tests
- integration: Tests related to integration with other systems, like firebase authentication
- plugins: to load plugins if needed
- support: supporting functionality for tests

##### Running tests
To start the UI for running cypress tests, run the cypress script defined in package.json in coffeepot root folder
> $ npm run cypress

When prompted for a project, choose the root folder for the coffeepot repository. It will automatically look for the cypress folder and list all the tests for you. You can now run tests one by one or all of them. Have fun with test driven development!
  
### Code analysis
#### Kibit
[Kibit](https://github.com/jonase/kibit) searches code patterns that could be rewritten.

To run kibit code analyzer:
  > $ lein kibit
