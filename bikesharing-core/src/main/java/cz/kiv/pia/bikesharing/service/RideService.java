package cz.kiv.pia.bikesharing.service;

import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.domain.Ride;
import cz.kiv.pia.bikesharing.domain.Stand;
import cz.kiv.pia.bikesharing.domain.User;
import cz.kiv.pia.bikesharing.exception.RideNotFoundException;

import java.util.Collection;

/**
 * Service supporting all use cases with bike {@link Ride}.
 */
public interface RideService {
    /**
     * Starts a new {@link Ride} of given <code>user</code> and <code>bike</code> starting at given <code>startStand</code>.
     *
     * @param user User riding bike
     * @param bike Bike to be ridden by user
     * @return Started ride
     */
    Ride startRide(User user, Bike bike);

    /**
     * Completes given <code>ride</code> at given <code>endState</code>, i.e. returns {@link Bike} to the {@link Stand}.
     *
     * @param ride Started ride to be completed
     * @param endStand End stand where the ride is completed
     * @throws RideNotFoundException when ride is not found
     * @throws IllegalStateException when ride is not started and therefore cannot be completed
     */
    void completeRide(Ride ride, Stand endStand);

    /**
     * Retrieves all {@link Ride}s that given <code>user</code> has completed in the past, including
     * any {@link Ride} which might be ongoing at the moment.
     *
     * @param user User doing bike rides
     * @return All rides of given user
     */
    Collection<Ride> getRides(User user);
}
