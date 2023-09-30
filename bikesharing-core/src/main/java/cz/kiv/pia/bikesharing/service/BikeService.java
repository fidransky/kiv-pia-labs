package cz.kiv.pia.bikesharing.service;

import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.exception.BikeNotServiceableException;

import java.util.Collection;

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
     * @throws BikeNotServiceableException when bike is not due for service yet
     */
    void markServiced(Bike bike);
}
