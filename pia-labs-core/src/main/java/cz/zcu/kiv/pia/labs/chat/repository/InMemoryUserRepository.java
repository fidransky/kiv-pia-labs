package cz.zcu.kiv.pia.labs.chat.repository;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import cz.zcu.kiv.pia.labs.chat.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<UUID, User> usersMap;

    public InMemoryUserRepository() {
        this.usersMap = new HashMap<>();
    }

    @PostConstruct
    private void postConstruct() {
        usersMap.put(UserService.DEFAULT_USER.getId(), UserService.DEFAULT_USER);

        var secondUser = new User("jane.doe");
        usersMap.put(secondUser.getId(), secondUser);
    }

    @Override
    public Mono<User> registerUser(User user) {
        usersMap.put(user.getId(), user);

        return Mono.just(usersMap.get(user.getId()));
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        for (User user : usersMap.values()) {
            if (user.getUsername().equals(username)) {
                return Mono.just(user);
            }
        }

        return Mono.empty();
    }
}
