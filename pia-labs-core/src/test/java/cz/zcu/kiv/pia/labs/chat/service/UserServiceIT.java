package cz.zcu.kiv.pia.labs.chat.service;

import cz.zcu.kiv.pia.labs.BeanConfig;
import cz.zcu.kiv.pia.labs.chat.ITConfiguration;
import cz.zcu.kiv.pia.labs.chat.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        BeanConfig.class,
        ITConfiguration.class,
})
class UserServiceIT {
    private final static User currentUser = new User("john.doe");

    @Autowired
    private DefaultUserService userService;

    @BeforeAll
    static void beforeAll() {
        var authentication = new UsernamePasswordAuthenticationToken(currentUser, null);

        TestSecurityContextHolder.setAuthentication(authentication);
    }

    @Test
    void getCurrentUser() {
        var result = userService.getCurrentUser();

        assertEquals(currentUser, result.block());
    }
}
