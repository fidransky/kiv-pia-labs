INSERT INTO `user` (`id`, `username`)
VALUES (UUID_TO_BIN('567219c1-525a-44b1-93d3-383008a5a029'), 'jane.doe');

INSERT INTO room (`id`, `name`, `administrator_id`)
VALUES (UUID_TO_BIN('75ae2ef7-8cf5-48d3-b03f-d137a5d43b1f'), 'running', UUID_TO_BIN('567219c1-525a-44b1-93d3-383008a5a029'));
