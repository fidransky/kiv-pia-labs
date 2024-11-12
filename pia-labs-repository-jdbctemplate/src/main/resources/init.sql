-- this script is used to populate database by DataSourceInitializer bean (see JdbcConfiguration)

CREATE TABLE IF NOT EXISTS `damage` (
  `id` UUID NOT NULL,
  `timestamp` TIMESTAMP(3) NOT NULL,
  `longitude` DECIMAL(11, 8) NOT NULL,
  `latitude` DECIMAL(10, 8) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  PRIMARY KEY `id` (`id`)
);

INSERT INTO `damage` (`id`, `timestamp`, `latitude`, `longitude`, `description`) VALUES
(UUID(), '2024-08-13 21:53:12.672', 49.7339986, 13.4018103, 'I got hit by a motorbike');
