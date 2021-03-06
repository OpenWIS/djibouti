#!/bin/bash
echo - Finalizing MySQL configuration
echo

yum install -y nc

echo
echo - Waiting for MySQL to start...

while ! nc -z localhost 3306; do
  echo - Waiting...
  sleep 1
done

echo
echo - MySQL starterd. Configuring...
echo
mysql -u root -h localhost -p123456 < awisc-docker-injected-mysql-configuration-script
exit

echo
echo - Configuration completed
