package cz.kiv.pia.bikesharing.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a single user of the service.
 */
public class User {
    /**
     * Unique identifier.
     */
    private final UUID id;
    /**
     * Full name of the user.
     */
    private final String name;
    /**
     * Email address of the user, used for log in.
     */
    private final String emailAddress;
    /**
     * Role of the user.
     */
    private final Role role;

    public User(UUID id, String name, String emailAddress, Role role) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Role getRole() {
        return role;
    }

    /**
     * Starts a new bike ride of this user on given bike.
     *
     * @param bike Bike used for the bike ride
     * @return Started bike ride
     */
    public Ride startRide(Bike bike) {
        var startStand = bike.getStand();

        bike.removeFromStand();

        return new Ride(this, bike, startStand);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(emailAddress, user.emailAddress) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, emailAddress, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", role=" + role +
                '}';
    }

    public enum Role {
        /**
         * Regular user
         */
        REGULAR,
        /**
         * Serviceman, can do everything that regular users but also maintains bikes
         */
        SERVICEMAN,
    }
}
