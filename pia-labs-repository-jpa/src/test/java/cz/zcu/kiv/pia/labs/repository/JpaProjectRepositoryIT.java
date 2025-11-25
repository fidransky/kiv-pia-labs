package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.configuration.JpaConfiguration;
import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.repository.entity.ProjectDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZoneOffset;
import java.util.TimeZone;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests of {@link JpaProjectRepository} using Testcontainers to run MariaDB instance in a Docker container.
 */
// autoconfigure JPA beans
@DataJpaTest
@Import(JpaConfiguration.class)
@TestPropertySource(properties = {
        // do not use embedded database
        "spring.test.database.replace=none",
        // run tests against real MariaDB database running in Docker using Testcontainers
        "spring.datasource.url=jdbc:tc:mariadb:11:///pia_labs",
})
class JpaProjectRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JpaRepository<ProjectDTO, UUID> internalProjectRepository;

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
        this.projectRepository = new JpaProjectRepository(internalProjectRepository);
    }

    @Test
    // populate database with data from JpaProjectRepositoryIT.getAll.sql script in test resources
    @Sql
    void getAll() {
        var firstProject = new Project(UUID.fromString("945f8cc6-7547-4f6d-b4a0-cc96b7cc50e2"));

        var result = projectRepository.getAll();

        assertEquals(2, result.size());
        assertEquals(firstProject, result.getFirst());
        // TODO: other assertions...
    }
}
