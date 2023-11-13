package cz.kiv.pia.bikesharing.repository.dto;

import cz.kiv.pia.bikesharing.repository.JdbcTemplateStandRepository;

import java.util.UUID;

/**
 * Bike stand stored in database.
 *
 * @param id Unique bike stand identifier.
 * @param name Bike stand name.
 * @param location Bike stand location.
 */
public record StandDTO(
        UUID id,
        String name,
        StandLocationDTO location
) {}
