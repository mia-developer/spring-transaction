GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

CREATE DATABASE sample DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
grant replication slave on *.* to 'root'@'%';
