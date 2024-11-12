package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests of {@link JdbcTemplateDamageRepository} using Testcontainers to run MariaDB instance in a Docker container.
 */
// autoconfigure DataSource and JdbcTemplate beans
@JdbcTest
@TestPropertySource(properties = {
        // do not use embedded database
        "spring.test.database.replace=none",
        // run tests against real MariaDB database running in Docker using Testcontainers
        "spring.datasource.url=jdbc:tc:mariadb:11:///pia_labs",
})
// populate database with data from JdbcTemplateDamageRepositoryIT.sql script in test resources
@Sql
class JdbcTemplateDamageRepositoryIT {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private DamageRepository damageRepository;

    @BeforeEach
    void setUp() {
        this.damageRepository = new JdbcTemplateDamageRepository(jdbcTemplate, new DamageRowMapper());
    }

    @Test
    void create() {
        // test data
        var insured = new User(UUID.randomUUID(), null, null, UserRole.INSURED);
        var impaired = new User(UUID.randomUUID(), null, null, UserRole.IMPAIRED);
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
        var insured = new User(UUID.randomUUID(), null, null, UserRole.INSURED);

        // tested method
        var result = damageRepository.retrieveReportedDamage(insured);

        // verifications
        assertEquals(2, result.size());
    }
}
