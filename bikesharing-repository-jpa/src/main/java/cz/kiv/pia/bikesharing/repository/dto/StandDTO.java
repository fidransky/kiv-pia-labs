package cz.kiv.pia.bikesharing.repository.dto;

import jakarta.persistence.*;
import java.util.UUID;

/**
 * Bike stand stored in database.
 */
@Entity(name = "Stand")
@Table(name = "stand")
public class StandDTO {
    /**
     * Unique bike stand identifier.
     */
    @Id
    private UUID id;
    /**
     * Bike stand name.
     */
    @Column
    private String name;
    /**
     * Bike stand location.
     */
    @Embedded
    private StandLocationDTO location;

    public StandDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StandLocationDTO getLocation() {
        return location;
    }

    public void setLocation(StandLocationDTO location) {
        this.location = location;
    }
}
