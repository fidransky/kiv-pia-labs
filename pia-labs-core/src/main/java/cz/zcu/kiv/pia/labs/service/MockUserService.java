package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

/**
 * Mock implementation of {@link UserService} always returning the same user.
 */
public class MockUserService implements UserService {
    private final User mockUser;

    public MockUserService() {
        this(new User(UUID.fromString("0d707c7c-fade-4078-9bcd-655de77cc6b3"), "John Doe", "john.doe@example.com", UserRole.CUSTOMER, Collections.emptySet(), Instant.now()));
    }

    public MockUserService(User mockUser) {
        this.mockUser = mockUser;
    }

    @Override
    public User getCurrentUser() {
        return mockUser;
    }
}
