package cz.zcu.kiv.pia.labs.domain;

import java.time.Instant;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
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
        // check that this user is a CUSTOMER
        if (role != UserRole.CUSTOMER) {
            throw new IllegalStateException("Only customers can create projects");
        }

        // TODO: check that sourceFile is not empty but also not too big

        return new Project(this, targetLanguage, sourceFile);
    }

    public void completeProject(Project project, byte[] translatedFile) {
        // check that this user is a TRANSLATOR
        if (role != UserRole.TRANSLATOR) {
            throw new IllegalStateException("Only translators can complete projects");
        }

        if (translatedFile == null || translatedFile.length == 0) {
            throw new IllegalArgumentException("Translated file cannot be empty");
        }

        project.complete(translatedFile);
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


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(emailAddress, user.emailAddress) && role == user.role && Objects.equals(languages, user.languages) && Objects.equals(createdAt, user.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, emailAddress, role, languages, createdAt);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", role=" + role +
                ", languages=" + languages +
                ", createdAt=" + createdAt +
                '}';
    }
}
