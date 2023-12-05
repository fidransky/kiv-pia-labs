package cz.kiv.pia.bikesharing.service;

import cz.kiv.pia.bikesharing.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DummyUserService extends InMemoryUserDetailsManager implements UserService, UserDetailsService {
    private static final User user1 = new User(UUID.randomUUID(), "Pavel Fidranský", "pavel.fidransky@yoso.fi", User.Role.REGULAR);

    public DummyUserService() {
        super(user1);
    }
}
