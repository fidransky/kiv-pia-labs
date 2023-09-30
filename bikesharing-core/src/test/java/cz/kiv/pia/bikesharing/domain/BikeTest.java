package cz.kiv.pia.bikesharing.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BikeTest {
    private static final Period serviceInterval = Period.ofMonths(2);

    @Nested
    class isDueForService {
        @Test
        void yes() {
            var bike = new Bike(null, null, LocalDate.MIN, null);

            var actualResult = bike.isDueForService(serviceInterval);

            assertTrue(actualResult);
        }

        @Test
        void no() {
            var bike = new Bike(null, null, LocalDate.now(), null);

            var actualResult = bike.isDueForService(serviceInterval);

            assertFalse(actualResult);
        }
    }

    @Test
    void addToStand() {
        var stand = new Stand(UUID.randomUUID(), "náměstí Republiky", new Location("49.7479433N", "13.3786114E"));
        var bike = new Bike(UUID.randomUUID(), new Location("49.7479433N", "13.3786114E"), LocalDate.now(), null);

        bike.addToStand(stand);

        assertEquals(stand, bike.getStand());
        assertTrue(stand.getBikes().contains(bike));
    }

    @Test
    void removeFromStand() {
        var stand = new Stand(UUID.randomUUID(), "náměstí Republiky", new Location("49.7479433N", "13.3786114E"));
        var bike = new Bike(UUID.randomUUID(), new Location("49.7479433N", "13.3786114E"), LocalDate.now(), stand);

        bike.removeFromStand();

        assertNull(bike.getStand());
        assertFalse(stand.getBikes().contains(bike));
    }
}
