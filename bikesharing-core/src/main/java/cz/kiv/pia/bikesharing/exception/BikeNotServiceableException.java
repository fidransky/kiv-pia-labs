package cz.kiv.pia.bikesharing.exception;

import cz.kiv.pia.bikesharing.domain.Bike;

/**
 * Thrown when {@link Bike} has not passed its service period yet.
 */
public class BikeNotServiceableException extends RuntimeException {
    public final Bike bike;

    public BikeNotServiceableException(Bike bike) {
        super("Bike %s has not passed its service period yet.".formatted(bike));
        this.bike = bike;
    }
}
