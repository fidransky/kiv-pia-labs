package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Implementation of {@link DamageRepository} storing data in memory. Data are cleared when application restarts.
 */
public class InMemoryDamageRepository implements DamageRepository {
    final Map<UUID, Damage> data;

    public InMemoryDamageRepository() {
        this(new HashMap<>());
    }

    public InMemoryDamageRepository(Map<UUID, Damage> data) {
        this.data = data;
    }

    @Override
    public Damage create(Damage damage) {
        data.put(damage.getId(), damage);

        return data.get(damage.getId());
    }

    @Override
    public Collection<Damage> retrieveReportedDamage(User user) {
        return data.values().stream()
                .filter(damage -> damage.getInsured().equals(user))
                .toList();
    }
}
