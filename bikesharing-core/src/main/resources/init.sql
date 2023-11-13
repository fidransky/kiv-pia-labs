CREATE TABLE IF NOT EXISTS `stand` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  `longitude` varchar(255) NOT NULL,
  `latitude` varchar(255) NOT NULL,
  PRIMARY KEY `id` (`id`),
  UNIQUE `UK_name` (`name`)
);

INSERT INTO `stand` (`id`, `name`, `latitude`, `longitude`) VALUES
(UUID_TO_BIN(UUID()), 'náměstí Republiky', '49.7479433N', '13.3786114E'),
(UUID_TO_BIN(UUID()), 'Fakulta aplikovaných věd ZČU', '49.7269708N', '13.3516872E'),
(UUID_TO_BIN(UUID()), 'Menza ZČU IV', '49.7237950N', '13.3523658E');