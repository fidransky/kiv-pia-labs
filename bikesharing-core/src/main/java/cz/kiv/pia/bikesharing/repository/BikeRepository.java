package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Bike;

import java.util.Collection;

/**
 * Repository storing all information related to bikes.
 */
public interface BikeRepository {
    /**
     * Retrieves all bikes which are NOT due for service.
     *
     * @return All bikes which are NOT due for service
     */
    Collection<Bike> getRideableBikes();

    /**
     * Retrieves all bikes which are due for service, sorted by {@link Bike#lastServiceTimestamp} in ascending order
     *
     * @return All bikes which are due for service
     */
    Collection<Bike> getBikesDueForService();

    /**
     * Saves new state of an existing bike.
     *
     * @param bike Bike to be saved
     */
    void saveBike(Bike bike);
}
