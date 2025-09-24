package cz.zcu.kiv.pia.labs.domain;

import java.time.Instant;
import java.util.UUID;

// NOTE: setters are intentionally not provided for the sake of encapsulation
public class Feedback {
    private UUID id;
    private Project project;
    private String text;
    private Instant createdAt;

    // constructor used when referencing the object in other domain objects where only ID is known
    public Feedback(UUID id) {
        this.id = id;
    }

    // constructor used when referencing the full object
    public Feedback(Project project, String text) {
        this.id = UUID.randomUUID();
        this.project = project;
        this.text = text;
        this.createdAt = Instant.now();
    }

    //<editor-fold desc="getters" defaultstate="collapsed">
    public Project getProject() {
        return project;
    }

    public String getText() {
        return text;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    //</editor-fold>
}
