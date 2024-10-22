package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

class InMemoryDamageRepositoryTest {
    private InMemoryDamageRepository damageRepository;

    @BeforeEach
    void setUp() {
        this.damageRepository = new InMemoryDamageRepository();
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
        assertEquals("Created damage differs", damage, result);
        assertFalse("Repository is empty", damageRepository.data.isEmpty());
        assertTrue("Repository doesn't contain created damage", damageRepository.data.containsKey(damage.getId()));
    }

    @Test
    void retrieveReportedDamage() {
        // test data
        var insured1 = new User(UUID.randomUUID(), null, null, UserRole.INSURED);
        var damage1 = insured1.reportDamage(null, null, null, null);
        damageRepository.data.put(damage1.getId(), damage1);

        var insured2 = new User(UUID.randomUUID(), null, null, UserRole.INSURED);
        var damage2 = insured2.reportDamage(null, null, null, null);
        damageRepository.data.put(damage2.getId(), damage2);

        // tested method
        var result = damageRepository.retrieveReportedDamage(insured1);

        // verifications
        assertFalse("Result is empty", result.isEmpty());
        assertTrue("Result doesn't contain damage 1", result.contains(damage1));
        assertFalse("Result contains damage 2", result.contains(damage2));
    }
}
