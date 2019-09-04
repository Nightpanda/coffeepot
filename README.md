# Coffeepot
Coffeepot is a social networking service for coffee lovers. With Coffeepot, users can rate and share their coffee experiences.
  > Coffeepot is a social network web app that allows users to post and share their:
  > - brewed coffees
  > - drank coffee (self or barista made)
  > - brew recipes
  > - favorite beans, roasteries, cafes etc.
  >  
  >  Brewing and drinking coffee also earns the users badges/achievements

Coffeepot is made with Clojure and ClojureScript.

## How to participate
### Slack
Join our slack TBD
### Trello

We use Trello to organize our development.

[Coffeepot Trello board](https://trello.com/b/mRJY0Av7/coffeepot)

## Developer guide

### Main technologies

 - Clojure
 - ClojureScript
 - Leiningen
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
 
### Install dependencies etc.
```
$ npm init -y
$ npm install cypress --save-dev
```

#### Server
[Server readme](server/README.md)

#### Client
[Client readme](client/README.md)
