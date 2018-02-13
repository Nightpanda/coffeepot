# coffeepot
Proof of technology for the base of a coffee rating webapp with Clojurescript and Firebase.


## Needed to deploy with firebase hosting if you have the rights
$ npm install -g firebase-tools
$ firebase login
$ firebase deploy

## Deployment
Builds cljs and deploys with firebase, asks for firebase login first if you are not logged in.
$ build_deploy.sh
