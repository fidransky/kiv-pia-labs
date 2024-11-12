-- this script is used to populate database thanks to @Sql annotation at JdbcTemplateDamageRepositoryIT test class

INSERT INTO `damage` (`id`, `timestamp`, `latitude`, `longitude`, `description`) VALUES
(UUID(), '2024-08-13 21:53:12.672', 49.7339986, 13.4018103, 'I got hit by a motorbike'),
(UUID(), '2022-06-26 14:31:01.243', 49.7269708, 13.3516872, 'server room got flooded');