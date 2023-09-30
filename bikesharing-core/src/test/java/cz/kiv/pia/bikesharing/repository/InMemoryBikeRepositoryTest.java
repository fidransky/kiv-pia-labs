package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.domain.Location;
import cz.kiv.pia.bikesharing.exception.BikeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryBikeRepositoryTest {
    private static final Period serviceInterval = Period.ofMonths(2);
    private InMemoryBikeRepository bikeRepository;

    @BeforeEach
    void setUp() {
        this.bikeRepository = new InMemoryBikeRepository(serviceInterval);
    }

    @Test
    void getRideableBikes() {
        // DATA
        bikeRepository.store.add(new Bike(UUID.randomUUID(), new Location("49.7479433N", "13.3786114E"), LocalDate.now(), InMemoryStandRepository.DEFAULT_STAND));
        bikeRepository.store.add(new Bike(UUID.randomUUID(), new Location("49.7479433N", "13.3786114E"), LocalDate.now(), InMemoryStandRepository.DEFAULT_STAND));
        bikeRepository.store.add(new Bike(UUID.randomUUID(), new Location("49.7479433N", "13.3786114E"), LocalDate.now().minusMonths(6), InMemoryStandRepository.DEFAULT_STAND));

        // TESTS
        var result = bikeRepository.getRideableBikes();

        assertEquals(2, result.size());
        assertAll(result.stream().map(bike -> () -> {
            assertFalse(bike.isDueForService(serviceInterval));
        }));
    }

    @Test
    void getBikesDueForService() {
        // DATA
        bikeRepository.store.add(new Bike(UUID.randomUUID(), new Location("49.7479433N", "13.3786114E"), LocalDate.now(), InMemoryStandRepository.DEFAULT_STAND));
        bikeRepository.store.add(new Bike(UUID.randomUUID(), new Location("49.7479433N", "13.3786114E"), LocalDate.now().minusMonths(6), InMemoryStandRepository.DEFAULT_STAND));
        bikeRepository.store.add(new Bike(UUID.randomUUID(), new Location("49.7479433N", "13.3786114E"), LocalDate.now().minusMonths(3), InMemoryStandRepository.DEFAULT_STAND));

        // TESTS
        var result = bikeRepository.getBikesDueForService();

        assertEquals(2, result.size());
        assertAll(result.stream().map(bike -> () -> {
            assertTrue(bike.isDueForService(serviceInterval));
        }));

        var resultIterator = result.iterator();
        var bike1 = resultIterator.next();
        var bike2 = resultIterator.next();
        assertTrue(bike1.getLastServiceTimestamp().isBefore(bike2.getLastServiceTimestamp()));
    }

    @Nested
    class getById {
        @Test
        void bikeFound() {
            // DATA
            var bike = new Bike(UUID.randomUUID(), new Location("49.7479433N", "13.3786114E"), LocalDate.now(), InMemoryStandRepository.DEFAULT_STAND);
            var bikeId = bike.getId();

            bikeRepository.store.add(bike);

            // TESTS
            var result = bikeRepository.getById(bikeId);

            assertEquals(bike, result);
        }

        @Test
        void bikeNotFound() {
            var bikeId = UUID.randomUUID();

            assertThrows(BikeNotFoundException.class, () -> bikeRepository.getById(bikeId));
        }
    }
}
