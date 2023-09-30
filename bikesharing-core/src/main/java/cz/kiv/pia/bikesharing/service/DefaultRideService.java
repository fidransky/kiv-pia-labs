package cz.kiv.pia.bikesharing.service;

import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.domain.Ride;
import cz.kiv.pia.bikesharing.domain.Stand;
import cz.kiv.pia.bikesharing.domain.User;
import cz.kiv.pia.bikesharing.repository.RideRepository;
import org.slf4j.Logger;

import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

public class DefaultRideService implements RideService {
    private static final Logger LOG = getLogger(DefaultRideService.class);

    private final RideRepository rideRepository;

    public DefaultRideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Override
    public Ride startRide(User user, Bike bike) {
        LOG.info("Starting new bike ride of user {} on bike {}", user, bike);

        // TODO: implement location proximity check

        var ride = user.startRide(bike);

        LOG.debug("New bike ride {} is started, saving it", ride);

        return rideRepository.create(ride);
    }

    @Override
    public void completeRide(Ride ride, Stand endStand) {
        LOG.info("Completing existing bike ride {} at stand {}", ride, endStand);

        var existingRide = rideRepository.getById(ride.getId());

        LOG.debug("Ride {} exists", existingRide);

        if (!existingRide.isStarted()) {
            LOG.info("Bike ride {} is not started", existingRide);

            throw new IllegalStateException("Ride %s is currently not started, it cannot be completed.".formatted(ride.getId()));
        }

        existingRide.complete(endStand);

        LOG.debug("Ride {} is completed, saving it", existingRide);

        rideRepository.save(existingRide);
    }

    @Override
    public Collection<Ride> getRides(User user) {
        LOG.info("Getting rides of user {}", user);

        return rideRepository.getByUser(user);
    }
}
