package cz.zcu.kiv.pia.labs.chat.service;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {

    User DEFAULT_USER = new User("john.doe");

    /**
     * Registers user
     *
     * @param username User's unique username
     * @return Registered user
     */
    Mono<User> registerUser(String username);

    /**
     * @return Currently authenticated user
     */
    Mono<User> getCurrentUser();
}
