package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.User;

public interface UserService {
    /**
     * @return currently logged-in user
     */
    User getCurrentUser();

    // other service methods here
}
