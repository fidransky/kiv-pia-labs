package cz.kiv.pia.bikesharing.repository.dto;

/**
 * Bike stand location.
 *
 * @param longitude Longitude in defined coordinates system.
 * @param latitude Latitude in defined coordinates system.
 */
public record StandLocationDTO(
        String longitude,
        String latitude
) {}
