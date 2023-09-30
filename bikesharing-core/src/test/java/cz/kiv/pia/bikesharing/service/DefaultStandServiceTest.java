package cz.kiv.pia.bikesharing.service;

import cz.kiv.pia.bikesharing.domain.Location;
import cz.kiv.pia.bikesharing.domain.Stand;
import cz.kiv.pia.bikesharing.repository.StandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultStandServiceTest {
    @Mock
    private StandRepository standRepository;

    private StandService standService;

    @BeforeEach
    void setUp() {
        this.standService = new DefaultStandService(standRepository);
    }

    @Nested
    class getAll {
        @Test
        void standsFound() {
            // DATA
            var stand = new Stand(UUID.randomUUID(), "náměstí Republiky", new Location("49.7479433N", "13.3786114E"));

            // MOCKS
            when(standRepository.getAll()).thenReturn(List.of(stand));

            // TESTS
            var result = standService.getAll();

            verify(standRepository).getAll();

            assertTrue(result.contains(stand));
        }

        @Test
        void noStandsFound() {
            // TESTS
            var result = standService.getAll();

            verify(standRepository).getAll();

            assertTrue(result.isEmpty());
        }
    }
}