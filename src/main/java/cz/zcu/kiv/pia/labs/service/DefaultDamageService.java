package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.repository.DamageRepository;

import java.time.Instant;
import java.util.Collection;

public class DefaultDamageService implements DamageService {
    private final UserService userService;
    private final DamageRepository damageRepository;

    public DefaultDamageService(UserService userService, DamageRepository damageRepository) {
        this.userService = userService;
        this.damageRepository = damageRepository;
    }

    @Override
    public void create(User impaired, Instant timestamp, Location location, String description) {
        var currentUser = userService.getCurrentUser();

        var damage = currentUser.reportDamage(impaired, timestamp, location, description);

        damageRepository.create(damage);
    }

    // TODO: check that user is in role INSURED (i.e. authorization)
    @Override
    public Collection<Damage> retrieveReportedDamage() {
        var currentUser = userService.getCurrentUser();

        return damageRepository.retrieveReportedDamage(currentUser);
    }
}
