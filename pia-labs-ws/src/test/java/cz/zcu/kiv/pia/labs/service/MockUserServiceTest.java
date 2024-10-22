package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.springframework.test.util.AssertionErrors.assertEquals;

class MockUserServiceTest {
    private final User expectedResult = new User(UUID.randomUUID(), "John Doe", "john.doe@example.com", UserRole.INSURED);

    private UserService userService;

    @BeforeEach
    void setUp() {
        this.userService = new MockUserService(expectedResult);
    }

    @Test
    void getCurrentUser() {
        var actualResult = userService.getCurrentUser();

        assertEquals("User doesn't match", expectedResult, actualResult);
    }
}
