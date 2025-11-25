INSERT INTO user (id, name, email_address, role, created_at) VALUES
('dc642942-0867-4743-8bec-caa726ef5ef3', 'John Doe', 'john.doe@example.com', 'TRANSLATOR', NOW()),
('abec3bc8-7701-4a80-81d7-cdda56f4a9cc', 'Jane Doe', 'jane.doe@example.com', 'CUSTOMER', NOW());

INSERT INTO user_language (user_id, language) VALUES
('dc642942-0867-4743-8bec-caa726ef5ef3', 'DE'),
('dc642942-0867-4743-8bec-caa726ef5ef3', 'DK');

INSERT INTO project (id, customer_id, target_language, source_file, state, created_at) VALUES
('faaf8607-07ea-46cd-aed4-0cbf9beafe1c', 'abec3bc8-7701-4a80-81d7-cdda56f4a9cc', 'ES', 0x00, 'CREATED', '2025-11-25 04:27:10.123456'),
('945f8cc6-7547-4f6d-b4a0-cc96b7cc50e2', 'abec3bc8-7701-4a80-81d7-cdda56f4a9cc', 'CS', 0x00, 'CREATED', '2025-11-25 08:27:10.123456');