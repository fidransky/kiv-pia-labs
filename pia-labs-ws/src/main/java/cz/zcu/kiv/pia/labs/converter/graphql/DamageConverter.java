package cz.zcu.kiv.pia.labs.converter.graphql;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.DamageState;
import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.graphql.DamageDTO;
import cz.zcu.kiv.pia.labs.graphql.DamageStateEnum;
import cz.zcu.kiv.pia.labs.graphql.LocationDTO;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DamageConverter implements Converter<Damage, DamageDTO> {
    @Override
    public DamageDTO convert(Damage source) {
        return DamageDTO.builder()
                .withId(source.getId())
                .withTimestamp(toDateTime(source.getTimestamp()))
                .withLocation(toLocationDTO(source.getLocation()))
                .withDescription(source.getDescription())
                .withState(toState(source.getState()))
                // other mappings here
                .build();
    }

    private OffsetDateTime toDateTime(Instant source) {
        return source.atOffset(ZoneOffset.UTC);
    }

    private LocationDTO toLocationDTO(Location source) {
        return LocationDTO.builder()
                .withLongitude(source.getLongitude().doubleValue())
                .withLatitude(source.getLatitude().doubleValue())
                .build();
    }

    private DamageStateEnum toState(DamageState source) {
        return switch (source) {
            case STARTED -> DamageStateEnum.STARTED;
            case PROCESSING -> DamageStateEnum.PROCESSING;
            case APPROVED -> DamageStateEnum.APPROVED;
            case REJECTED -> DamageStateEnum.REJECTED;
            case CLOSED -> DamageStateEnum.CLOSED;
        };
    }
}
