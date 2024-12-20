package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.event.DamageReportedEvent;
import cz.zcu.kiv.pia.labs.repository.DamageRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;

@Transactional(readOnly = true)
public class DefaultDamageService implements DamageService, ApplicationEventPublisherAware {
    private final UserService userService;
    private final DamageRepository damageRepository;
    private ApplicationEventPublisher applicationEventPublisher;

    public DefaultDamageService(UserService userService, DamageRepository damageRepository) {
        this.userService = userService;
        this.damageRepository = damageRepository;
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    @Transactional
    @Secured("ROLE_INSURED")
    public Damage create(User impaired, Instant timestamp, Location location, String description) {
        var currentUser = userService.getCurrentUser();

        var damage = currentUser.reportDamage(impaired, timestamp, location, description);

        var createdDamage = damageRepository.create(damage);

        if (applicationEventPublisher != null) {
            applicationEventPublisher.publishEvent(new DamageReportedEvent(createdDamage));
        }

        return createdDamage;
    }

    @Override
    @Secured("ROLE_INSURED")
    public Collection<Damage> retrieveReportedDamage() {
        var currentUser = userService.getCurrentUser();

        return damageRepository.retrieveReportedDamage(currentUser);
    }
}
