# Coffeepot Server

## Developer Guide

### Install dependencies etc.
```
$ lein install
$ npm init -y
$ npm install mysql2 --save
$ npm install sequelize --save
$ npm install npx -g
$ npm install sequelize-cli --save-dev 
```


### MySQL database

#### Database tool such as DBeaver

First download your choice of database tool with MySQL compatibility. Here we install DBeaver.
DBeaver can be downloaded from: https://dbeaver.io/download/

DBeaver can be started with:
```
$ dbeaver
```

#### Installation

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
#### Setup DBeaver
- Create new Connection
- Select MySql as the database type
- Fill in the general database details:
  ```
  Server Host: localhost
  Port: 3306
  Database: coffeepot_dev
  User name: root
  Password: root
  ```
- Open the *Driver properties* tab and set *allowPublicKeyRetrieval* to true
- Click *Test connection*
  - If database connection test is successful the opening popup should say 'Connected (*n* ms)'

#### Migrate database with Sequelize CLI
Going up:
```
$ npx sequelize db:migrate
```
Undoing migration:
```
$ npx sequelize db:migrate:undo
```

#### Seed database
Sequelize can be used to seed data to the database.
```
$ npx sequelize db:seed --seed db/seeders/20190621163301-demo-user.js
$ npx sequelize db:seed:undo --seed db/seeders/20190621163301-demo-user.js
```

#### Initialize server for development TODO
 > $ ./init-dev.sh

#### Start the server locally
 > $ ./start-server.sh
 
