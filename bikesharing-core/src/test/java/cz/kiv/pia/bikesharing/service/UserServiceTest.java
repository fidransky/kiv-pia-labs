package cz.kiv.pia.bikesharing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        this.userService = new UserService() {};
    }

    @Test
    void getCurrentUser() {
        var result = userService.getCurrentUser();

        assertNotNull(result);
    }
}