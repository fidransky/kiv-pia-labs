package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.configuration.JdbcConfiguration;
import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.domain.ProjectState;
import cz.zcu.kiv.pia.labs.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests of {@link JdbcProjectRepository} using Testcontainers to run MariaDB instance in a Docker container.
 */
// autoconfigure DataSource bean
@JdbcTest
@ContextConfiguration(classes = JdbcConfiguration.class)
@TestPropertySource(properties = {
        // do not use embedded database
        "spring.test.database.replace=none",
        // run tests against real MariaDB database running in Docker using Testcontainers
        "spring.datasource.url=jdbc:tc:mariadb:11:///pia_labs",
})
class JdbcProjectRepositoryIT {

    @Autowired
    private DataSource dataSource;

    private ProjectRepository projectRepository;

    @BeforeAll
    static void beforeAll() {
        // set timezone to UTC to match MariaDB database configuration
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));

        // needed because of a Testcontainers bug: https://github.com/testcontainers/testcontainers-java/issues/11212
        System.setProperty("api.version", "1.44");
    }

    @BeforeEach
    void setUp() {
        this.projectRepository = new JdbcProjectRepository(dataSource, new ProjectRowMapper());
    }

    @Nested
    class store {
        @Test
        // populate database with data from JdbcProjectRepositoryIT$store.success.sql script in test resources
        @Sql
        void success() {
            var customer = new User(UUID.fromString("abec3bc8-7701-4a80-81d7-cdda56f4a9cc"));
            var project = new Project(UUID.randomUUID(), customer, null, Locale.forLanguageTag("CS"), new byte[]{0}, null, ProjectState.CREATED, Instant.parse("2025-11-25T08:27:10.123456Z"));

            assertDoesNotThrow(() -> projectRepository.store(project));
        }

        @Test
        void nonExistentCustomer() {
            var customer = new User(UUID.randomUUID());
            var project = new Project(UUID.randomUUID(), customer, null, Locale.forLanguageTag("CS"), new byte[]{0}, null, ProjectState.CREATED, Instant.parse("2025-11-25T08:27:10.123456Z"));

            assertDoesNotThrow(() -> projectRepository.store(project));
        }
    }

    @Nested
    class getAll {
        @Test
        // populate database with data from JdbcProjectRepositoryIT$getAll.success.sql script in test resources
        @Sql
        void success() {
            var firstProject = new Project(UUID.fromString("945f8cc6-7547-4f6d-b4a0-cc96b7cc50e2"), new User(UUID.fromString("abec3bc8-7701-4a80-81d7-cdda56f4a9cc")), null, Locale.forLanguageTag("CS"), new byte[]{0}, null, ProjectState.CREATED, Instant.parse("2025-11-25T08:27:10.123456Z"));

            var result = projectRepository.getAll();

            assertEquals(2, result.size());
            assertEquals(firstProject, result.getFirst());
            // TODO: other assertions...
        }

        @Test
        void none() {
            var result = projectRepository.getAll();

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class findById {
        @Test
        // populate database with data from JdbcProjectRepositoryIT$findById.success.sql script in test resources
        @Sql
        void success() {
            var project = new Project(UUID.fromString("945f8cc6-7547-4f6d-b4a0-cc96b7cc50e2"), new User(UUID.fromString("abec3bc8-7701-4a80-81d7-cdda56f4a9cc")), null, Locale.forLanguageTag("CS"), new byte[]{0}, null, ProjectState.CREATED, Instant.parse("2025-11-25T08:27:10.123456Z"));

            var result = projectRepository.findById(project.getId());

            assertEquals(project, result);
        }

        @Test
        void nonExistent() {
            var result = projectRepository.findById(UUID.randomUUID());

            assertNull(result);
        }
    }
}
