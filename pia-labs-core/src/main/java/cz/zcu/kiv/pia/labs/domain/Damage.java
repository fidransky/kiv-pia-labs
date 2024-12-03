package cz.zcu.kiv.pia.labs.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Damage implements Serializable {
    private final UUID id;
    private User insured;
    private User impaired;
    private Instant timestamp;
    private Location location;
    private String description;
    private DamageState state;

    public Damage(User insured, User impaired, Instant timestamp, Location location, String description) {
        this.id = UUID.randomUUID();
        this.insured = insured;
        this.impaired = impaired;
        this.timestamp = timestamp;
        this.location = location;
        this.description = description;
        this.state = DamageState.STARTED;
    }

    // constructor used when referencing the object in other domain objects where only ID is known
    public Damage(UUID id) {
        this.id = id;
    }

    // constructor used when referencing the full object
    public Damage(UUID id, User insured, User impaired, Instant timestamp, Location location, String description, DamageState state) {
        this.id = id;
        this.insured = insured;
        this.impaired = impaired;
        this.timestamp = timestamp;
        this.location = location;
        this.description = description;
        this.state = state;
    }

    public void startProcessing() {
        if (!DamageState.STARTED.equals(state)) {
            throw new IllegalStateException("Cannot start processing of damage in '%s' state.".formatted(state));
        }

        this.state = DamageState.PROCESSING;
    }

    public Document requestDocument(String description) {
        if (!DamageState.STARTED.equals(state) && !DamageState.PROCESSING.equals(state)) {
            throw new IllegalStateException("Documents cannot be requested for damage in '%s' state.".formatted(state));
        }

        return new Document(this, description);
    }

    public UUID getId() {
        return id;
    }

    public User getInsured() {
        return insured;
    }

    public User getImpaired() {
        return impaired;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Location getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public DamageState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Damage{" +
                "id=" + id +
                ", insured=" + insured +
                ", impaired=" + impaired +
                ", timestamp=" + timestamp +
                ", location=" + location +
                ", description='" + description + '\'' +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Damage damage)) return false;
        return Objects.equals(id, damage.id) && Objects.equals(insured, damage.insured) && Objects.equals(impaired, damage.impaired) && Objects.equals(timestamp, damage.timestamp) && Objects.equals(location, damage.location) && Objects.equals(description, damage.description) && state == damage.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, insured, impaired, timestamp, location, description, state);
    }
}
