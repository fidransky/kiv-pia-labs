package cz.kiv.pia.bikesharing.exception;

import cz.kiv.pia.bikesharing.domain.Bike;

/**
 * Thrown when {@link Bike} is not found.
 */
public class BikeNotFoundException extends IllegalStateException {
    public final Bike bike;

    public BikeNotFoundException(Bike bike) {
        super("Bike %s was not found.".formatted(bike));
        this.bike = bike;
    }
}
