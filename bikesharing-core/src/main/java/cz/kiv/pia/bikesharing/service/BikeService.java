package cz.kiv.pia.bikesharing.service;

import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.domain.Location;
import cz.kiv.pia.bikesharing.exception.BikeNotFoundException;
import cz.kiv.pia.bikesharing.exception.BikeNotServiceableException;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Service supporting all use cases involving bikes.
 */
public interface BikeService {
    /**
     * Retrieves all {@link Bike}s which are NOT due for service.
     *
     * @return All bikes which are NOT due for service
     */
    Collection<Bike> getRideableBikes();

    /**
     * Retrieves all {@link Bike}s which are due for service, sorted by {@link Bike#lastServiceTimestamp} in ascending order
     *
     * @return All bikes which are due for service
     */
    Collection<Bike> getBikesDueForService();

    /**
     * Marks <code>bike</code> as serviced right now.
     *
     * @param bike Serviced bike
     * @throws BikeNotFoundException when bike is not found
     * @throws BikeNotServiceableException when bike is not due for service yet
     */
    void markServiced(Bike bike);

    /**
     * Moves {@link Bike} with given <code>bikeId</code> to a new <code>location</code>.
     *
     * @param bikeId Unique bike identifier
     * @param location New bike location
     */
    void moveBike(UUID bikeId, Location location);

    /**
     * Stream {@link Bike} location updates to given <code>subscriber</code>.
     *
     * @param bikeId Unique bike identifier
     * @param subscriber Bike location subscriber
     */
    void watchBikeLocation(UUID bikeId, Consumer<Location> subscriber);
}
