package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Ride;
import cz.kiv.pia.bikesharing.domain.User;
import cz.kiv.pia.bikesharing.exception.RideNotFoundException;

import java.util.Collection;
import java.util.UUID;

/**
 * Repository storing all information about bike rides.
 */
public interface RideRepository {
    /**
     * Retrieves ride with given ID.
     *
     * @param rideId Ride ID
     * @return Found ride
     * @throws RideNotFoundException when ride with given ID is not found
     */
    Ride getById(UUID rideId);

    /**
     * Retrieves all rides that given user has completed in the past, including any ride which
     * might be ongoing at the moment.
     *
     * @param user User doing bike rides
     * @return All rides of given user
     */
    Collection<Ride> getByUser(User user);

    /**
     * Stores a new ride.
     *
     * @param ride Ride to be stored
     * @return Stored ride
     */
    Ride create(Ride ride);

    /**
     * Saves new state of an existing ride.
     *
     * @param ride Ride to be saved
     */
    void save(Ride ride);
}
