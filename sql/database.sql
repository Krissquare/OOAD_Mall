CREATE DATABASE IF NOT EXISTS oomall DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

CREATE USER 'dbuser'@'localhost' IDENTIFIED BY '12345678';
CREATE USER 'dbuser'@'%' IDENTIFIED BY '12345678';

GRANT ALL ON oomall.* TO 'dbuser'@'localhost';
GRANT ALL ON oomall.* TO 'dbuser'@'%';

FLUSH PRIVILEGES;