-- insert user if missing, otherwise ignore
INSERT INTO `user` (`id`, `username`)
VALUES (UUID_TO_BIN(UUID()), 'john.doe')
ON DUPLICATE KEY UPDATE `username` = 'john.doe';
