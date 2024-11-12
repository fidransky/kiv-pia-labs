package cz.zcu.kiv.pia.labs.repository.dto;

/**
 * Damage occurrence location.
 *
 * @param longitude Longitude in defined coordinates system.
 * @param latitude  Latitude in defined coordinates system.
 */
public record DamageLocationDTO(
        String longitude,
        String latitude
) {
}
