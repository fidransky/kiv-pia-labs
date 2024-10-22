package cz.zcu.kiv.pia.labs.converter.rest;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DamageConverterTest {
    private DamageConverter damageConverter;

    @BeforeEach
    void setUp() {
        this.damageConverter = new DamageConverter();
    }

    @Test
    void convert() {
        var insured = new User(UUID.randomUUID(), null, null, UserRole.INSURED);
        var impaired = new User(UUID.randomUUID(), null, null, UserRole.IMPAIRED);
        var timestamp = Instant.parse("2007-12-03T10:15:30.00Z");
        var location = new Location("49.7269708", "13.3516872");
        var description = "Lorem ipsum";

        var damage = new Damage(insured, impaired, timestamp, location, description);

        var result = damageConverter.convert(damage);

        assertEquals(damage.getId(), result.getId());
        assertEquals(damage.getDescription(), result.getDescription());
    }
}
