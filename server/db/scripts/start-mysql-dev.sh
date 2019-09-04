#if [ -z "$1" ]
#then
#      echo "Give developer username in operating system as the first argument"
#else
#      sudo adduser $1 mysql
#      sudo mkdir /var/run/mysqld
#      sudo chgrp mysql /var/run/mysqld
#      sudo chmod -R +wr /var/run/mysqld
#      sudo chown mysql:adm /var/log/mysql/error.log
#      sudo chown mysql:adm /var/log/mysql
#      sudo chown root:syslog /var/log
#      sudo chown root:root /var
#      sudo chmod 0640 /var/log/mysql/error.log
#      sudo chmod 0750 /var/log/mysql
#      sudo chmod 0775 /var/log
#      sudo chmod 0755 /var
#      sudo chgrp -R mysql /var/log/mysql/
#      sudo chmod -R +wr /var/log/mysql/
#      sudo chown -R $1 /var/lib/mysql/
#      sudo chown -R $1
#      sudo mysqld --user=mysql start
#fi

sudo service mysql start
