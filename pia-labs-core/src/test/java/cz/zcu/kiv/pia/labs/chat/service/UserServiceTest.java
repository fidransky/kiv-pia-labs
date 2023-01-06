package cz.zcu.kiv.pia.labs.chat.service;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import cz.zcu.kiv.pia.labs.chat.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.security.test.context.support.ReactorContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListener;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DefaultUserService userService;

    private static final User currentUser = new User("john.doe");
    private static final TestExecutionListener reactorContextTestExecutionListener = new ReactorContextTestExecutionListener();

    @BeforeAll
    static void beforeAll() throws Exception {
        var authentication = new UsernamePasswordAuthenticationToken(currentUser, null);

        TestSecurityContextHolder.setAuthentication(authentication);
        reactorContextTestExecutionListener.beforeTestMethod(null);
    }

    @AfterAll
    static void afterAll() throws Exception {
        reactorContextTestExecutionListener.afterTestMethod(null);
    }

    @Test
    void registerUser() {
        var user = new User("john.doe");

        when(userRepository.registerUser(argThat(arg -> arg.getUsername().equals(user.getUsername())))).thenReturn(Mono.just(user));

        var result = userService.registerUser(user.getUsername());

        assertEquals(user, result.block());

        verify(userRepository).registerUser(argThat(arg -> arg.getUsername().equals(user.getUsername())));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findByUsername() {
        var user = new User("john.doe");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Mono.just(user));

        var result = userService.findByUsername(user.getUsername());

        assertEquals(user, result.block());

        verify(userRepository).findByUsername(user.getUsername());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getCurrentUser() {
        // https://stackoverflow.com/a/58855957

        var result = userService.getCurrentUser();

        assertEquals(currentUser, result.block());
    }
}
