package cz.zcu.kiv.pia.labs.repository.entity;

import cz.zcu.kiv.pia.labs.domain.DamageState;
import cz.zcu.kiv.pia.labs.domain.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "damage")
public class DamageDTO {
    @Id
    private UUID id;

    @Column(name = "insured_user_id", insertable = false, updatable = false)
    private UUID insuredUserId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "insured_user_id", referencedColumnName = "id")
    private UserDTO insuredUser;

    @Column(name = "impaired_user_id", insertable = false, updatable = false)
    private UUID impairedUserId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "impaired_user_id", referencedColumnName = "id")
    private UserDTO impairedUser;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Embedded
    private LocationDTO location;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "state", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private DamageState state;

    public DamageDTO() {
    }

    public DamageDTO(UUID id, UserDTO insuredUser, UserDTO impairedUser, Instant timestamp, LocationDTO location, String description, DamageState state) {
        this.id = id;
        this.insuredUser = insuredUser;
        this.impairedUser = impairedUser;
        this.timestamp = timestamp;
        this.location = location;
        this.description = description;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    // allows reading the ID directly without the unnecessary JOIN query
    public UUID getInsuredUserId() {
        return insuredUserId;
    }

    public UserDTO getInsuredUser() {
        return insuredUser;
    }

    public void setInsuredUser(UserDTO insuredUser) {
        this.insuredUserId = insuredUser.getId();
        this.insuredUser = insuredUser;
    }

    // allows reading the ID directly without the unnecessary JOIN query
    public UUID getImpairedUserId() {
        return impairedUserId;
    }

    public UserDTO getImpairedUser() {
        return impairedUser;
    }

    public void setImpairedUser(UserDTO impairedUser) {
        this.impairedUserId = impairedUser.getId();
        this.impairedUser = impairedUser;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DamageState getState() {
        return state;
    }

    public void setState(DamageState state) {
        this.state = state;
    }
}
