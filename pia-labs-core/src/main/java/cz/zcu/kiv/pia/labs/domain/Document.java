package cz.zcu.kiv.pia.labs.domain;

import java.time.Instant;
import java.util.UUID;

public class Document {
    private final UUID id;
    private Damage damage;
    private String description;
    private DocumentState state;
    private Instant requestedAt;
    private Instant uploadedAt;
    private Instant approvedAt;
    private byte[] data;

    public Document(Damage damage, String description) {
        this.id = UUID.randomUUID();
        this.damage = damage;
        this.description = description;
        this.state = DocumentState.REQUESTED;
        this.requestedAt = Instant.now();
    }

    // constructor used when referencing the object in other domain objects where only ID is known
    public Document(UUID id) {
        this.id = id;
    }

    // constructor used when referencing the full object
    public Document(UUID id, Damage damage, String description, DocumentState state, Instant requestedAt, Instant uploadedAt, byte[] data) {
        this.id = id;
        this.damage = damage;
        this.description = description;
        this.state = state;
        this.requestedAt = requestedAt;
        this.uploadedAt = uploadedAt;
        this.data = data;
    }

    public void upload(byte[] data) {
        if (!DocumentState.REQUESTED.equals(state)) {
            throw new IllegalStateException("Data cannot be uploaded for document in '%s' state.".formatted(state));
        }

        this.state = DocumentState.UPLOADED;
        this.uploadedAt = Instant.now();
        this.data = data;
    }

    public void approve() {
        if (!DocumentState.REQUESTED.equals(state)) {
            throw new IllegalStateException("Data cannot be uploaded for document in '%s' state.".formatted(state));
        }

        this.state = DocumentState.APPROVED;
        this.approvedAt = Instant.now();
    }
}
