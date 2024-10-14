package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;

import java.time.Instant;
import java.util.Collection;

public interface DamageService {
    /**
     * Creates a new {@link Damage} with given properties on behalf of current {@link User}.
     *
     * @param impaired impaired user
     * @param timestamp timestamp when the damage occurred
     * @param location location where the damage took place
     * @param description description of the damage
     */
    Damage create(User impaired, Instant timestamp, Location location, String description);

    /**
     * Retrieves {@link Damage} reported by current {@link User}.
     *
     * @return all damage reported by current user or empty collection if no matching damage is found
     */
    Collection<Damage> retrieveReportedDamage();

    // other service methods here
}
