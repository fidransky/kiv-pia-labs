package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.User;

import java.util.Collection;

/**
 * Common interface for {@link Damage} storage.
 */
public interface DamageRepository {
    /**
     * Stores a new damage.
     *
     * @param damage damage to be stored
     * @return stored damage
     */
    Damage create(Damage damage);

    /**
     * Retrieves damage reported by given insured user.
     *
     * @param user insured user
     * @return all damage reported by given user or empty collection if no matching damage is found
     */
    Collection<Damage> retrieveReportedDamage(User user);

    // other repository methods here
}
