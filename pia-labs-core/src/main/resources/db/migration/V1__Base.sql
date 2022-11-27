CREATE TABLE IF NOT EXISTS `user` (
  `id` binary(16) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY `id` (`id`),
  UNIQUE `UK_username` (`username`)
);

CREATE TABLE IF NOT EXISTS `room` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  `administrator_id` binary(16) NOT NULL,
  PRIMARY KEY `id` (`id`),
  CONSTRAINT `FK_room_administrator` FOREIGN KEY (`administrator_id`) REFERENCES `user` (`id`)
);
