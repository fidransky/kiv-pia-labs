package cz.zcu.kiv.pia.labs.domain;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

// NOTE: setters are intentionally not provided for the sake of encapsulation
public class Project {
    private UUID id;
    private User customer;
    private User translator;
    private Locale targetLanguage;
    private byte[] sourceFile;
    private byte[] translatedFile;
    private ProjectState state;
    private Instant createdAt;

    // constructor used when referencing the object in other domain objects where only ID is known
    public Project(UUID id) {
        this.id = id;
    }

    // constructor used when referencing the full object
    public Project(User customer, Locale targetLanguage, byte[] sourceFile) {
        this.id = UUID.randomUUID();
        this.customer = customer;
        this.translator = null;
        this.targetLanguage = targetLanguage;
        this.sourceFile = sourceFile;
        this.translatedFile = null;
        this.state = ProjectState.CREATED;
        this.createdAt = Instant.now();
    }

    public void assignTranslator(User user) {
        // TODO: check that user is a TRANSLATOR
        // TODO: check that project state is CREATED

        this.translator = user;
        this.state = ProjectState.ASSIGNED;
    }

    public void close() {
        // TODO: check that project state is CREATED or APPROVED

        this.state = ProjectState.CLOSED;
    }

    public void complete(byte[] translatedFile) {
        // TODO: check that project state is ASSIGNED
        // TODO: check that translatedFile is not empty but also not too big

        this.translatedFile = translatedFile;
        this.state = ProjectState.COMPLETED;
    }

    public void approve() {
        // TODO: check that project state is COMPLETED

        this.state = ProjectState.APPROVED;
    }

    public Feedback reject(String feedbackText) {
        // TODO: check that project state is COMPLETED
        // TODO: check that feedbackText is not empty

        this.state = ProjectState.ASSIGNED;

        return new Feedback(this, feedbackText);
    }

    //<editor-fold desc="getters" defaultstate="collapsed">
    public UUID getId() {
        return id;
    }

    public User getCustomer() {
        return customer;
    }

    public User getTranslator() {
        return translator;
    }

    public Locale getTargetLanguage() {
        return targetLanguage;
    }

    public byte[] getSourceFile() {
        return sourceFile;
    }

    public byte[] getTranslatedFile() {
        return translatedFile;
    }

    public ProjectState getState() {
        return state;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    //</editor-fold>
}
