-- this database migration script is executed by Flyway

CREATE TABLE IF NOT EXISTS `user` (
  `id` UUID NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `email_address` VARCHAR(255) NOT NULL,
  `role` TINYINT(1) NOT NULL,
  PRIMARY KEY `id` (`id`)
);

CREATE TABLE IF NOT EXISTS `damage` (
  `id` UUID NOT NULL,
  `insured_user_id` UUID NOT NULL,
  `impaired_user_id` UUID NOT NULL,
  `timestamp` TIMESTAMP(3) NOT NULL,
  `longitude` DECIMAL(11, 8) NOT NULL,
  `latitude` DECIMAL(10, 8) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `state` TINYINT(1) NOT NULL,
  PRIMARY KEY `id` (`id`)
);

ALTER TABLE `damage`
ADD CONSTRAINT `FK_insured_user_id` FOREIGN KEY (`insured_user_id`) REFERENCES `user` (`id`),
ADD CONSTRAINT `FK_impaired_user_id` FOREIGN KEY (`impaired_user_id`) REFERENCES `user` (`id`);
