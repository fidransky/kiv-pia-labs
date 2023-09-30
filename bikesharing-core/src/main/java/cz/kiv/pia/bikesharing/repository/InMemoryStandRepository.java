package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Location;
import cz.kiv.pia.bikesharing.domain.Stand;

import java.util.*;

public class InMemoryStandRepository implements StandRepository {
    public static final Stand DEFAULT_STAND = new Stand(UUID.randomUUID(), "náměstí Republiky", new Location("49.7479433N", "13.3786114E"));

    final Set<Stand> store;

    public InMemoryStandRepository() {
        this.store = new HashSet<>();
    }

    void initialize() {
        // populate Stand store
        this.store.add(DEFAULT_STAND);
        this.store.add(new Stand(UUID.randomUUID(), "Fakulta aplikovaných věd ZČU", new Location("49.7269708N", "13.3516872E")));
        this.store.add(new Stand(UUID.randomUUID(), "Menza ZČU IV", new Location("49.7237950N", "13.3523658E")));
    }

    @Override
    public Collection<Stand> getAll() {
        return Collections.unmodifiableSet(store);
    }
}
