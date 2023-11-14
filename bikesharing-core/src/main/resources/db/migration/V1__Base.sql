-- this database migration script is executed by Flyway

CREATE TABLE IF NOT EXISTS `stand` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  `longitude` varchar(255) NOT NULL,
  `latitude` varchar(255) NOT NULL,
  PRIMARY KEY `id` (`id`),
  UNIQUE `UK_name` (`name`)
);