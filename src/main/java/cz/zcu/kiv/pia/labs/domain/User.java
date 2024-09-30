package cz.zcu.kiv.pia.labs.domain;

import java.time.Instant;
import java.util.UUID;

public class User {
    private final UUID id;
    private String name;
    private String emailAddress;
    private UserRole role;

    // constructor used when referencing the object in other domain objects where only ID is known
    public User(UUID id) {
        this.id = id;
    }

    // constructor used when referencing the full object
    public User(UUID id, String name, String emailAddress, UserRole role) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
        this.role = role;
    }

    public Damage reportDamage(User impaired, Instant timestamp, Location location, String description) {
        if (!UserRole.INSURED.equals(role)) {
            throw new IllegalStateException("Damage cannot be reported by user with '%s' role.".formatted(role));
        }

        return new Damage(this, impaired, timestamp, location, description);
    }
}
