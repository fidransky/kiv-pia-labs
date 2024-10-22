package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import cz.zcu.kiv.pia.labs.graphql.DamageDTO;
import cz.zcu.kiv.pia.labs.graphql.ImpairedUserInputDTO;
import cz.zcu.kiv.pia.labs.graphql.LocationInputDTO;
import cz.zcu.kiv.pia.labs.service.DamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class GraphQLController {
    // property-based dependency injection
    @Autowired
    private DamageService damageService;
    @Autowired
    private ConversionService conversionService;

    @QueryMapping
    public String greeting(@Argument String name) {
        return "Hello, " + name + "!";
    }

    @QueryMapping
    public List<DamageDTO> retrieveDamage() {
        return damageService.retrieveReportedDamage().stream()
                .map(damage -> conversionService.convert(damage, DamageDTO.class))
                .toList();
    }

    @MutationMapping
    public DamageDTO createDamage(@Argument("impaired") ImpairedUserInputDTO impairedInput, @Argument("timestamp") OffsetDateTime timestampInput, @Argument("location") LocationInputDTO locationInput, @Argument String description) {
        var impaired = new User(UUID.randomUUID(), impairedInput.getName(), impairedInput.getEmailAddress(), UserRole.IMPAIRED);
        var timestamp = timestampInput.toInstant();
        var location = new Location(locationInput.getLongitude(), locationInput.getLatitude());

        var result = damageService.create(impaired, timestamp, location, description);

        return conversionService.convert(result, DamageDTO.class);
    }
}
