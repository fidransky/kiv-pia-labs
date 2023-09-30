package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.exception.BikeNotFoundException;

import java.util.Collection;
import java.util.UUID;

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
     * Retrieves bike with given ID.
     *
     * @param bikeId Bike ID
     * @return Found bike
     * @throws BikeNotFoundException when bike with given ID is not found
     */
    Bike getById(UUID bikeId);

    /**
     * Saves new state of an existing bike.
     *
     * @param bike Bike to be saved
     */
    void save(Bike bike);
}
