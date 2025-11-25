-- this script is used to populate database by DataSourceInitializer bean (see JdbcConfiguration)

INSERT IGNORE INTO user (id, name, email_address, role, created_at) VALUES
('cf0af874-2858-4003-8b22-3d813b93bd5f', 'Pavel Fidranský', 'pavel.fidransky@yoso.fi', 'ADMINISTRATOR', NOW());
