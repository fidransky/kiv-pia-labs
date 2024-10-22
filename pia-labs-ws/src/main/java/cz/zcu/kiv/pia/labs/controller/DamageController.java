package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import cz.zcu.kiv.pia.labs.model.DamageDTO;
import cz.zcu.kiv.pia.labs.service.DamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class DamageController implements DamagesApi {
    // property-based dependency injection
    @Autowired
    private DamageService damageService;
    @Autowired
    private ConversionService conversionService;

    @Override
    public ResponseEntity<Void> createDamage(DamageDTO damageDTO) {
        var impaired = new User(UUID.randomUUID(), damageDTO.getImpaired().getName(), damageDTO.getImpaired().getEmailAddress(), UserRole.IMPAIRED);
        var timestamp = damageDTO.getTimestamp().toInstant();
        var location = new Location(damageDTO.getLocation().getLongitude(), damageDTO.getLocation().getLatitude());
        var description = damageDTO.getDescription();

        damageService.create(impaired, timestamp, location, description);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ResponseEntity<List<DamageDTO>> retrieveDamage() {
        var result = damageService.retrieveReportedDamage().stream()
                .map(damage -> conversionService.convert(damage, DamageDTO.class))
                .toList();

        return ResponseEntity.ok(result);
    }

    // other controller methods here
}
