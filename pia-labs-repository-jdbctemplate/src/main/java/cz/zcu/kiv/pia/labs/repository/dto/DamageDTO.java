package cz.zcu.kiv.pia.labs.repository.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * TODO: javaDoc
 *
 * @param id
 * @param timestamp
 * @param location
 * @param description
 */
public record DamageDTO(
        UUID id,
        Instant timestamp,
        DamageLocationDTO location,
        String description
) {
}
