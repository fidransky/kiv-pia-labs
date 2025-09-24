package cz.zcu.kiv.pia.labs.domain;

import java.time.Instant;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

// NOTE: setters are intentionally not provided for the sake of encapsulation
public class User {
    private UUID id;
    private String name;
    private String emailAddress;
    private UserRole role;
    private Set<Locale> languages;
    private Instant createdAt;

    // constructor used when referencing the object in other domain objects where only ID is known
    public User(UUID id) {
        this.id = id;
    }

    // constructor used when referencing the full object
    private User(String name, String emailAddress, UserRole role, Set<Locale> languages) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.emailAddress = emailAddress;
        this.role = role;
        this.languages = languages;
        this.createdAt = Instant.now();
    }

    public static User createCustomer(String name, String emailAddress) {
        // TODO: check that name is not empty
        // TODO: check that emailAddress is not empty and it is a valid email address

        return new User(name, emailAddress, UserRole.CUSTOMER, Collections.emptySet());
    }

    public static User createTranslator(String name, String emailAddress, Set<Locale> languages) {
        // TODO: check that name is not empty
        // TODO: check that emailAddress is not empty and it is a valid email address
        // TODO: check that there is at least one language

        return new User(name, emailAddress, UserRole.TRANSLATOR, languages);
    }

    public Project createProject(Locale targetLanguage, byte[] sourceFile) {
        // TODO: check that this user is a CUSTOMER
        // TODO: check that sourceFile is not empty but also not too big

        return new Project(this, targetLanguage, sourceFile);
    }

    //<editor-fold desc="getters" defaultstate="collapsed">
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public UserRole getRole() {
        return role;
    }

    public Set<Locale> getLanguages() {
        return languages;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    //</editor-fold>
}
