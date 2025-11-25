package cz.zcu.kiv.pia.labs.repository.entity;

import cz.zcu.kiv.pia.labs.domain.ProjectState;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "project")
public class ProjectDTO {
    @Id
    private UUID id;

    @Column(name = "customer_id", insertable = false, updatable = false)
    private UUID customerId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private UserDTO customer;

    @Column(name = "translator_id", insertable = false, updatable = false)
    private UUID translatorId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "translator_id", referencedColumnName = "id")
    private UserDTO translator;

    @Column(name = "state", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProjectState state;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public ProjectDTO() {
        // used by JPA to create new empty instances
    }

    public ProjectDTO(UUID id, UUID customerId, UserDTO customer, UUID translatorId, UserDTO translator, ProjectState state, Instant createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.customer = customer;
        this.translatorId = translatorId;
        this.translator = translator;
        this.state = state;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    // allows reading the ID directly without the unnecessary JOIN query
    public UUID getCustomerId() {
        return customerId;
    }

    public UserDTO getCustomer() {
        return customer;
    }

    public void setCustomer(UserDTO customer) {
        this.customerId = customer.getId();
        this.customer = customer;
    }

    // allows reading the ID directly without the unnecessary JOIN query
    public UUID getTranslatorId() {
        return translatorId;
    }

    public UserDTO getTranslator() {
        return translator;
    }

    public void setTranslator(UserDTO translator) {
        this.translatorId = translator.getId();
        this.translator = translator;
    }

    public ProjectState getState() {
        return state;
    }

    public void setState(ProjectState state) {
        this.state = state;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
