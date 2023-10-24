package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Location;
import cz.kiv.pia.bikesharing.domain.Stand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryStandRepositoryTest {
    private InMemoryStandRepository standRepository;

    @BeforeEach
    void setUp() {
        this.standRepository = new InMemoryStandRepository();
    }

    @Test
    void getAll() {
        // DATA
        var stand1 = new Stand(UUID.randomUUID(), "náměstí Republiky", new Location("49.7479433N", "13.3786114E"));
        var stand2 = new Stand(UUID.randomUUID(), "Fakulta aplikovaných věd ZČU", new Location("49.7269708N", "13.3516872E"));

        standRepository.store.clear();
        standRepository.store.add(stand1);
        standRepository.store.add(stand2);

        // TESTS
        var result = standRepository.getAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(stand1));
        assertTrue(result.contains(stand2));
    }
}