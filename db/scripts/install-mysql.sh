#!/bin/bash

#wget https://dev.mysql.com/get/mysql-apt-config_0.8.13-1_all.deb
#sudo dpkg -i mysql-apt-config_0.8.13-1_all.deb
#sudo apt-get update
#sudo apt-get install mysql-server
#sudo mysql_secure_installation
#systemctl status mysql.service
#sudo chmod -R 770 /var/lib/mysql
#sudo chgrp -R mysql /var/lib/mysql
#sudo chmod 755 /etc/rc.d/init.d/mysqld
#sudo apt-get -y update
#sudo apt-get -y install mysql-server

#sudo apt-get install mysql-server mysql-client
#sudo apt-get install mysql-client-core-8.0 mysql-server libmysqlclient-dev
sudo apt-get install mysql-server

#IF PROBLEMS ARISE, THIS IS EPIC REBOOT
#sudo su
#apt-get remove --purge 'mysql*'
#apt-get autoremove
#apt-get autoclean
#rm -f /etc/mysql /var/lib/mysql
#apt-get install mysql-server
