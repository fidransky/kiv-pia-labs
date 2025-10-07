package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.assertEquals;

class MockUserServiceTest {
    private final User expectedResult = User.createCustomer("John Doe", "john.doe@example.com");

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