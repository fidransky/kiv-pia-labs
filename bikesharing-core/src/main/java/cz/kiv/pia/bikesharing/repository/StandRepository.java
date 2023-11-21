package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Stand;

import java.util.Collection;

/**
 * Repository storing all information related to bike stands.
 */
public interface StandRepository {
    /**
     * Retrieves all stands currently in the system.
     *
     * @return All stands
     */
    Collection<Stand> getAll();

    /**
     * Retrieves all stands matching given name currently in the system.
     *
     * @param q Partial stand name
     * @return Stands matching given name
     */
    default Collection<Stand> getAllByName(String q) {
        throw new UnsupportedOperationException();
    }
}
