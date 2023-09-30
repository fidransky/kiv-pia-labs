package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Ride;
import cz.kiv.pia.bikesharing.domain.User;
import cz.kiv.pia.bikesharing.exception.RideNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class InMemoryRideRepository implements RideRepository {
    final Set<Ride> store;

    public InMemoryRideRepository() {
        this.store = new HashSet<>();
    }

    @Override
    public Ride getById(UUID rideId) {
        return store.stream()
                .filter(thisRide -> thisRide.getId().equals(rideId))
                .findFirst()
                .orElseThrow(() -> new RideNotFoundException(new Ride(rideId)));
    }

    @Override
    public Collection<Ride> getByUser(User user) {
        return store.stream()
                .filter(thisRide -> thisRide.getUser().getId().equals(user.getId()))
                .toList();
    }

    @Override
    public Ride create(Ride ride) {
        store.add(ride);

        return ride;
    }

    @Override
    public void save(Ride ride) {
        // since Ride is an object reference, it is already updated in the store
        // nothing to do here
    }
}
