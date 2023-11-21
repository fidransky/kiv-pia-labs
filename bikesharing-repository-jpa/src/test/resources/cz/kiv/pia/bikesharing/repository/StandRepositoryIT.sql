-- this script is loaded into database thanks to @Sql annotation at StandRepositoryIT class

DELETE FROM `stand`;

INSERT INTO `stand` (`id`, `name`, `latitude`, `longitude`) VALUES
(UUID_TO_BIN(UUID()), 'náměstí Republiky', '49.7479433N', '13.3786114E'),
(UUID_TO_BIN(UUID()), 'Fakulta aplikovaných věd ZČU', '49.7269708N', '13.3516872E');