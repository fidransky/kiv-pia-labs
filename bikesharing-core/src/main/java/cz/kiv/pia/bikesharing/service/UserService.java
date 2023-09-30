package cz.kiv.pia.bikesharing.service;

import cz.kiv.pia.bikesharing.domain.User;

import java.util.UUID;

public interface UserService {
    /**
     * Retrieves currently authenticated {@link User} from authentication store.
     *
     * @return currently authenticated user
     */
    default User getCurrentUser() {
        return new User(UUID.randomUUID(), "Jane Doe", "jane.doe@example.com", User.Role.REGULAR);
    }
}
