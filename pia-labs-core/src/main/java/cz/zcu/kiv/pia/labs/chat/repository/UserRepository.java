package cz.zcu.kiv.pia.labs.chat.repository;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface UserRepository {
    /**
     * Registers user
     *
     * @param user User to be registered
     * @return Registered user
     */
    Mono<User> registerUser(User user);

    /**
     * Finds user by his unique username
     *
     * @param username User's unique username
     * @return Found user
     */
    Mono<UserDetails> findByUsername(String username);
}
