INSERT INTO user (id, name, email_address, role, created_at) VALUES
('abec3bc8-7701-4a80-81d7-cdda56f4a9cc', 'Jane Doe', 'jane.doe@example.com', 'CUSTOMER', NOW());

INSERT INTO project (id, customer_id, target_language, source_file, state, created_at) VALUES
('945f8cc6-7547-4f6d-b4a0-cc96b7cc50e2', 'abec3bc8-7701-4a80-81d7-cdda56f4a9cc', 'CS', 0x00, 'CREATED', '2025-11-25 08:27:10.123456');