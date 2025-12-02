package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of {@link UserService} using Spring Security.
 */
public class SpringSecurityUserService implements UserService, UserDetailsService {

    private final SecurityContextHolderStrategy contextHolderStrategy;

    // use in-memory user repository, NOT ready for production use!
    private final Map<String, UserDetails> repo;

    public SpringSecurityUserService(SecurityContextHolderStrategy contextHolderStrategy) {
        this.contextHolderStrategy = contextHolderStrategy;
        this.repo = new ConcurrentHashMap<>();

        var user1 = User.createCustomer("John Doe", "john.doe@example.com");
        this.repo.put(user1.getUsername(), user1);
        var user2 = User.createTranslator("Jane Doe", "jane.doe@example.com", Set.of(Locale.FRENCH, Locale.GERMAN));
        this.repo.put(user2.getUsername(), user2);
        var user3 = new User(UUID.randomUUID(), "Pavel Fidranský", "pavel.fidransky@yoso.fi", UserRole.ADMINISTRATOR, Collections.emptySet(), Instant.now());
        this.repo.put(user3.getUsername(), user3);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.get(username);
    }

    @Override
    public User getCurrentUser() {
        var authentication = contextHolderStrategy.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails user) {
            return (User) loadUserByUsername(user.getUsername());
        }

        throw new IllegalStateException("Authentication principal is not a supported user object.");
    }
}
