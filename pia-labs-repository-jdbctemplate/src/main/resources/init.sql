-- this script is used to populate database by DataSourceInitializer bean (see JdbcConfiguration)

INSERT INTO `damage` (`id`, `timestamp`, `latitude`, `longitude`, `description`) VALUES
(UUID(), '2024-08-13 21:53:12.672', 49.7339986, 13.4018103, 'I got hit by a motorbike');
