package cz.kiv.pia.bikesharing.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents one ride of {@link User} on a {@link Bike}.
 */
public class Ride {
    /**
     * Unique identifier
     */
    private final UUID id;
    /**
     * User riding the {@link #bike}
     */
    private User user;
    /**
     * Bike ridden by the {@link #user}
     */
    private Bike bike;
    /**
     * Current state of the ride
     */
    private State state;
    /**
     * Starting timestamp of the ride
     */
    private Instant startTimestamp;
    /**
     * Starting {@link Stand} of the ride
     */
    private Stand startStand;
    /**
     * Ending timestamp of the ride. Null until the ride is finished
     */
    private Instant endTimestamp;
    /**
     * Ending {@link Stand} of the ride. Null until the ride is finished
     */
    private Stand endStand;

    // constructor used when a reference to Ride is needed in other object but full Ride object is not loaded from storage
    public Ride(UUID id) {
        this.id = id;
    }

    // constructor used when starting a new Ride
    Ride(User user, Bike bike, Stand startStand) {
        this(UUID.randomUUID(), user, bike, State.STARTED, Instant.now(), startStand, null, null);
    }

    // constructor used when full Ride object is loaded from storage
    public Ride(UUID id, User user, Bike bike, State state, Instant startTimestamp, Stand startStand, Instant endTimestamp, Stand endStand) {
        this.id = id;
        this.user = user;
        this.bike = bike;
        this.state = state;
        this.startTimestamp = startTimestamp;
        this.startStand = startStand;
        this.endTimestamp = endTimestamp;
        this.endStand = endStand;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Bike getBike() {
        return bike;
    }

    public State getState() {
        return state;
    }

    public Instant getStartTimestamp() {
        return startTimestamp;
    }

    public Stand getStartStand() {
        return startStand;
    }

    public Instant getEndTimestamp() {
        return endTimestamp;
    }

    public Stand getEndStand() {
        return endStand;
    }

    /**
     * Completes this ride at given {@link Stand}.
     *
     * @param endStand End stand where the ride is completed
     */
    public void complete(Stand endStand) {
        this.state = State.COMPLETED;
        this.endTimestamp = Instant.now();
        this.endStand = endStand;
        this.bike.addToStand(endStand);
    }

    /**
     * @return true if this ride is started, false otherwise
     */
    public boolean isStarted() {
        return state == State.STARTED;
    }

    /**
     * @return true if this ride is completed, false otherwise
     */
    public boolean isCompleted() {
        return state == State.COMPLETED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ride ride)) return false;
        return Objects.equals(id, ride.id) && Objects.equals(user, ride.user) && Objects.equals(bike, ride.bike) && state == ride.state && Objects.equals(startTimestamp, ride.startTimestamp) && Objects.equals(startStand, ride.startStand) && Objects.equals(endTimestamp, ride.endTimestamp) && Objects.equals(endStand, ride.endStand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, bike, state, startTimestamp, startStand, endTimestamp, endStand);
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", user=" + user +
                ", bike=" + bike +
                ", state=" + state +
                ", startTimestamp=" + startTimestamp +
                ", startStand=" + startStand +
                ", endTimestamp=" + endTimestamp +
                ", endStand=" + endStand +
                '}';
    }

    /**
     * State of a {@link Ride}.
     */
    public enum State {
        /**
         * Ride has started, cannot be started again but can be completed
         */
        STARTED,
        /**
         * Ride has completed, cannot be either started or completed again
         */
        COMPLETED,
    }
}
