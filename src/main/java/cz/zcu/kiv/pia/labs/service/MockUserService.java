package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;

import java.util.UUID;

/**
 * Mock implementation of {@link UserService} always returning the same user.
 */
public class MockUserService implements UserService {
    private final User mockUser;

    public MockUserService() {
        this(new User(UUID.randomUUID(), "John Doe", "john.doe@example.com", UserRole.INSURED));
    }

    public MockUserService(User mockUser) {
        this.mockUser = mockUser;
    }

    @Override
    public User getCurrentUser() {
        return mockUser;
    }
}
