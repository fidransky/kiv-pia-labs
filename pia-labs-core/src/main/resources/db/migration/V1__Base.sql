-- this database migration script is executed by Flyway

CREATE TABLE IF NOT EXISTS `damage` (
  `id` UUID NOT NULL,
  `timestamp` TIMESTAMP(3) NOT NULL,
  `longitude` DECIMAL(11, 8) NOT NULL,
  `latitude` DECIMAL(10, 8) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  PRIMARY KEY `id` (`id`)
);