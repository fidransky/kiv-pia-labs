package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.User;

/**
 * Mock implementation of {@link UserService} always returning the same user.
 */
public class MockUserService implements UserService {
    private final User mockUser;

    public MockUserService() {
        this(User.createCustomer("John Doe", "john.doe@example.com"));
    }

    public MockUserService(User mockUser) {
        this.mockUser = mockUser;
    }

    @Override
    public User getCurrentUser() {
        return mockUser;
    }
}
