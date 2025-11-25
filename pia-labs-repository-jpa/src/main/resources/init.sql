-- this script is used to populate database by DataSourceInitializer bean (see JdbcConfiguration)

-- User table
CREATE TABLE user (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    role ENUM('CUSTOMER', 'TRANSLATOR', 'ADMINISTRATOR') NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE user ADD CONSTRAINT pk_user PRIMARY KEY (id);
ALTER TABLE user ADD CONSTRAINT uk_user_email UNIQUE (email_address);

-- User language junction table (for translators)
CREATE TABLE user_language (
    user_id UUID NOT NULL,
    language CHAR(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE user_language ADD CONSTRAINT pk_user_language PRIMARY KEY (user_id, language);
ALTER TABLE user_language ADD CONSTRAINT fk_user_language_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE;

-- Project table
CREATE TABLE project (
    id UUID NOT NULL,
    customer_id UUID NOT NULL,
    translator_id UUID,
    target_language CHAR(2) NOT NULL,
    source_file LONGBLOB NOT NULL,
    translated_file LONGBLOB,
    state ENUM('CREATED', 'ASSIGNED', 'COMPLETED', 'APPROVED', 'CLOSED') NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE project ADD CONSTRAINT pk_project PRIMARY KEY (id);
ALTER TABLE project ADD CONSTRAINT fk_project_customer FOREIGN KEY (customer_id) REFERENCES user(id);
ALTER TABLE project ADD CONSTRAINT fk_project_translator FOREIGN KEY (translator_id) REFERENCES user(id);

-- Feedback table
CREATE TABLE feedback (
    id UUID NOT NULL,
    project_id UUID NOT NULL,
    text TEXT NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE feedback ADD CONSTRAINT pk_feedback PRIMARY KEY (id);
ALTER TABLE feedback ADD CONSTRAINT fk_feedback_project FOREIGN KEY (project_id) REFERENCES project(id);
