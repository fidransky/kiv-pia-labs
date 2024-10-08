package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import cz.zcu.kiv.pia.labs.repository.DamageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultDamageServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private DamageRepository damageRepository;

    private DamageService damageService;

    @BeforeEach
    void setUp() {
        this.damageService = new DefaultDamageService(userService, damageRepository);
    }

    @Test
    void create() {
        // test data
        var insured = new User(UUID.randomUUID(), null, null, UserRole.INSURED);
        var impaired = new User(UUID.randomUUID(), null, null, UserRole.IMPAIRED);
        var timestamp = Instant.parse("2007-12-03T10:15:30.00Z");
        var location = new Location("49.7269708", "13.3516872");
        var description = "Lorem ipsum";

        // mocks
        when(userService.getCurrentUser()).thenReturn(insured);

        // tested method
        damageService.create(impaired, timestamp, location, description);

        // verifications
        verify(damageRepository).create(any(Damage.class));
    }

    @Test
    void create_impaired() {
        // test data
        var impaired = new User(UUID.randomUUID(), null, null, UserRole.IMPAIRED);
        var timestamp = Instant.parse("2007-12-03T10:15:30.00Z");
        var location = new Location("49.7269708", "13.3516872");
        var description = "Lorem ipsum";

        // mocks
        when(userService.getCurrentUser()).thenReturn(impaired);

        // tested method
        assertThrows(IllegalStateException.class, () -> damageService.create(impaired, timestamp, location, description));

        // verifications
        verify(damageRepository, never()).create(any(Damage.class));
    }

    @Test
    void create_investigator() {
        // test data
        var impaired = new User(UUID.randomUUID(), null, null, UserRole.IMPAIRED);
        var investigator = new User(UUID.randomUUID(), null, null, UserRole.INVESTIGATOR);
        var timestamp = Instant.parse("2007-12-03T10:15:30.00Z");
        var location = new Location("49.7269708", "13.3516872");
        var description = "Lorem ipsum";

        // mocks
        when(userService.getCurrentUser()).thenReturn(investigator);

        // tested method
        assertThrows(IllegalStateException.class, () -> damageService.create(impaired, timestamp, location, description));

        // verifications
        verify(damageRepository, never()).create(any(Damage.class));
    }
}
