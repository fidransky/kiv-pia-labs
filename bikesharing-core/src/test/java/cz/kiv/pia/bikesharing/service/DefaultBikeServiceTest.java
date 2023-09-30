package cz.kiv.pia.bikesharing.service;

import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.exception.BikeNotFoundException;
import cz.kiv.pia.bikesharing.exception.BikeNotServiceableException;
import cz.kiv.pia.bikesharing.repository.BikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultBikeServiceTest {
    @Mock
    private BikeRepository bikeRepository;

    private BikeService bikeService;

    @BeforeEach
    void setUp() {
        this.bikeService = new DefaultBikeService(bikeRepository, Period.ofMonths(2));
    }

    @Nested
    class getRideableBikes {
        @Test
        void bikesFound() {
            // DATA
            var bike1 = new Bike(UUID.randomUUID(), null, null, null);
            var bike2 = new Bike(UUID.randomUUID(), null, null, null);

            // MOCKS
            when(bikeRepository.getRideableBikes()).thenReturn(List.of(bike1, bike2));

            // TESTS
            var result = bikeService.getRideableBikes();

            verify(bikeRepository).getRideableBikes();

            assertTrue(result.contains(bike1));
            assertTrue(result.contains(bike2));
        }

        @Test
        void noBikesFound() {
            // TESTS
            var result = bikeService.getRideableBikes();

            verify(bikeRepository).getRideableBikes();

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class getBikesDueForService {
        @Test
        void bikesFound() {
            // DATA
            var bike1 = new Bike(UUID.randomUUID(), null, LocalDate.now().minusMonths(4), null);
            var bike2 = new Bike(UUID.randomUUID(), null, LocalDate.now().minusMonths(3), null);

            // MOCKS
            when(bikeRepository.getBikesDueForService()).thenReturn(List.of(bike1, bike2));

            // TESTS
            var result = bikeService.getBikesDueForService();

            verify(bikeRepository).getBikesDueForService();

            assertTrue(result.contains(bike1));
            assertTrue(result.contains(bike2));
        }

        @Test
        void noBikesFound() {
            // TESTS
            var result = bikeService.getBikesDueForService();

            verify(bikeRepository).getBikesDueForService();

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class markServiced {
        @Test
        void happyPath() {
            // DATA
            var bike = new Bike(UUID.randomUUID(), null, LocalDate.MIN, null);
            var bikeCaptor = ArgumentCaptor.forClass(Bike.class);

            // MOCKS
            when(bikeRepository.getById(bike.getId())).thenReturn(bike);

            // TESTS
            bikeService.markServiced(bike);

            verify(bikeRepository).save(bikeCaptor.capture());

            assertEquals(LocalDate.now(), bikeCaptor.getValue().getLastServiceTimestamp());
        }

        @Test
        void bikeNotFound() {
            // DATA
            var bike = new Bike(UUID.randomUUID(), null, LocalDate.MIN, null);

            // MOCKS
            when(bikeRepository.getById(bike.getId())).thenThrow(BikeNotFoundException.class);

            // TESTS
            assertThrows(BikeNotFoundException.class, () -> bikeService.markServiced(bike));

            verify(bikeRepository, never()).save(any(Bike.class));
        }

        @Test
        void bikeNotServiceable() {
            // DATA
            var bike = new Bike(UUID.randomUUID(), null, LocalDate.now(), null);

            // MOCKS
            when(bikeRepository.getById(bike.getId())).thenReturn(bike);

            // TESTS
            assertThrows(BikeNotServiceableException.class, () -> bikeService.markServiced(bike));

            verify(bikeRepository, never()).save(any(Bike.class));
        }
    }
}
