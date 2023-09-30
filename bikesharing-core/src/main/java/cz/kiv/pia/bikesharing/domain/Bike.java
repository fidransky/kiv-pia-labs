package cz.kiv.pia.bikesharing.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Class representing a single bike.
 */
public class Bike {
    /**
     * Unique identifier
     */
    private final UUID id;
    /**
     * Current location of the bike, updated in real-time.
     */
    private final Location location;
    /**
     * Date of the last service.
     */
    private LocalDate lastServiceTimestamp;
    /**
     * Stand that the bike is currently placed at. Can be null if the bike is ridden atm.
     */
    private final Stand stand;

    public Bike(UUID id, Location location, LocalDate lastServiceTimestamp, Stand stand) {
        this.id = id;
        this.location = location;
        this.lastServiceTimestamp = lastServiceTimestamp;
        this.stand = stand;
    }

    public UUID getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public LocalDate getLastServiceTimestamp() {
        return lastServiceTimestamp;
    }

    public Stand getStand() {
        return stand;
    }

    /**
     * Marks this bike as serviced now.
     */
    public void markServiced() {
        this.lastServiceTimestamp = LocalDate.now();
    }

    /**
     * Adds this bike to given stand.
     *
     * @param stand Stand to add the bike to
     */
    public void addToStand(Stand stand) {
        this.stand = stand;
        this.stand.addBike(this);
    }

    /**
     * Removes this bike from its current stand.
     */
    public void removeFromStand() {
        this.stand.removeBike(this);
        this.stand = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bike bike)) return false;
        return Objects.equals(id, bike.id) && Objects.equals(location, bike.location) && Objects.equals(lastServiceTimestamp, bike.lastServiceTimestamp) && Objects.equals(stand, bike.stand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, lastServiceTimestamp, stand);
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", location=" + location +
                ", lastServiceTimestamp=" + lastServiceTimestamp +
                ", stand=" + stand +
                '}';
    }
}
