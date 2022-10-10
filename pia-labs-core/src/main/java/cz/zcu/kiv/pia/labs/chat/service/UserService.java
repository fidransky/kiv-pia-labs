package cz.zcu.kiv.pia.labs.chat.service;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import cz.zcu.kiv.pia.labs.chat.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    public static final User DEFAULT_USER = new User("john.doe");
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers user
     *
     * @param username User's unique username
     * @return Registered user
     */
    public Mono<User> registerUser(String username) {
        var user = new User(username);

        return userRepository.registerUser(user);
    }
}
