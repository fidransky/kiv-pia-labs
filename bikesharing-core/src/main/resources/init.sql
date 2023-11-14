-- this script is used to populate database by DataSourceInitializer bean (see JdbcConfiguration)

INSERT INTO `stand` (`id`, `name`, `latitude`, `longitude`) VALUES
(UUID_TO_BIN(UUID()), 'náměstí Republiky', '49.7479433N', '13.3786114E'),
(UUID_TO_BIN(UUID()), 'Fakulta aplikovaných věd ZČU', '49.7269708N', '13.3516872E'),
(UUID_TO_BIN(UUID()), 'Menza ZČU IV', '49.7237950N', '13.3523658E');