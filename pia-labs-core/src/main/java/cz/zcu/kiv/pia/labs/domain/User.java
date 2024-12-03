package cz.zcu.kiv.pia.labs.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

public class User implements UserDetails, Serializable {
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

    public UUID getId() {
        return id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return getEmailAddress();
    }

    @Override
    public String getPassword() {
        return "$2a$12$o2UonTQ6TKQOsURKMRSRI.HsAwSEfqrWeEOXsPCcLtUQF0.pKzxRW";
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(emailAddress, user.emailAddress) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, emailAddress, role);
    }
}
