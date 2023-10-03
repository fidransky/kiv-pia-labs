package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.exception.BikeNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.Period;
import java.util.*;

@Repository
public class InMemoryBikeRepository implements BikeRepository {

    final Set<Bike> store;
    private final Period serviceInterval;

    public InMemoryBikeRepository(Period serviceInterval) {
        this.store = new HashSet<>();
        this.serviceInterval = serviceInterval;
    }

    @Override
    public Collection<Bike> getRideableBikes() {
        return store.stream()
                .filter(bike -> !bike.isDueForService(serviceInterval))
                .toList();
    }

    @Override
    public Collection<Bike> getBikesDueForService() {
        return store.stream()
                .filter(bike -> bike.isDueForService(serviceInterval))
                .sorted(Comparator.comparing(Bike::getLastServiceTimestamp))
                .toList();
    }

    @Override
    public Bike getById(UUID bikeId) {
        return store.stream()
                .filter(thisBike -> thisBike.getId().equals(bikeId))
                .findFirst()
                .orElseThrow(() -> new BikeNotFoundException(new Bike(bikeId)));
    }

    @Override
    public void save(Bike bike) {
        // since Bike is an object reference, it is already updated in the store
        // nothing to do here
    }
}
