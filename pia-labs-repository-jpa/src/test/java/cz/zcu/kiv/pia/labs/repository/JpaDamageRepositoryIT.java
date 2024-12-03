package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import cz.zcu.kiv.pia.labs.repository.entity.DamageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests of {@link JpaDamageRepository} using Testcontainers to run MariaDB instance in a Docker container.
 */
// autoconfigure DataSource and JdbcTemplate beans
@DataJpaTest
@TestPropertySource(properties = {
        // do not use embedded database
        "spring.test.database.replace=none",
        // run tests against real MariaDB database running in Docker using Testcontainers
        "spring.datasource.url=jdbc:tc:mariadb:11:///pia_labs",
})
// populate database with data from JpaDamageRepositoryIT.sql script in test resources
@Sql
class JpaDamageRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JpaRepository<DamageDTO, UUID> internalDamageRepository;

    private DamageRepository damageRepository;

    @BeforeEach
    void setUp() {
        this.damageRepository = new JpaDamageRepository(internalDamageRepository);
    }

    @Test
    void create() {
        // test data
        var insured = new User(UUID.randomUUID(), "John Doe", "john.doe@example.com", UserRole.INSURED);
        var impaired = new User(UUID.randomUUID(), "Jack Doe", "jack.doe@example.com", UserRole.IMPAIRED);
        var timestamp = Instant.parse("2007-12-03T10:15:30.00Z");
        var location = new Location("49.7269708", "13.3516872");
        var description = "Lorem ipsum";

        var damage = insured.reportDamage(impaired, timestamp, location, description);

        // tested method
        var result = damageRepository.create(damage);

        // verifications
        assertEquals(damage, result, "Created damage differs");
    }

    @Test
    void retrieveReportedDamage() {
        // test data
        var insured = new User(UUID.randomUUID(), "John Doe", "john.doe@example.com", UserRole.INSURED);

        // tested method
        var result = damageRepository.retrieveReportedDamage(insured);

        // verifications
        assertEquals(2, result.size());
    }
}
