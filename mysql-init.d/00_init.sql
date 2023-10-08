GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

CREATE DATABASE sample DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
create table sample (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
