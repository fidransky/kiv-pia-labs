package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of {@link UserService} using Spring Security.
 */
public class SpringSecurityUserService implements UserService, UserDetailsService {

    // in-memory user repository, NOT sufficient for production use!
    private final Map<String, UserDetails> repo;

    public SpringSecurityUserService() {
        this.repo = new ConcurrentHashMap<>();

        var user1 = new User(UUID.randomUUID(), "John Doe", "john.doe@example.com", UserRole.INSURED);
        this.repo.put(user1.getUsername(), user1);

        var user2 = new User(
                UUID.fromString("00000000-0000-0000-0000-000000000001"), // Fixed UUID for AI assistant
                "AI Assistant",
                "ai-assistant@mcp.internal",
                UserRole.INSURED // Set proper user role
        );
        this.repo.put(user2.getUsername(), user2);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.get(username);
    }

    @Override
    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails user) {
            return (User) loadUserByUsername(user.getUsername());
        }

        throw new IllegalStateException("Authentication principal is not a supported user object.");
    }
}
