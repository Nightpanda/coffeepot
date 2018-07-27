# coffeepot
Proof of technology for the base of a coffee rating webapp with Clojurescript and Firebase.


## Needed to deploy with firebase hosting if you have the rights
$ npm install -g firebase-tools
$ firebase login
$ firebase deploy

## Development

$ lein npm install
$ lein figwheel

### Local hosting to test production like environment
$ firebase serve

### Cypress testing
$ lein npm install
$ lein npm run cypress

## Deployment
Builds cljs and deploys with firebase, asks for firebase login first if you are not logged in.
$ build_deploy.sh
