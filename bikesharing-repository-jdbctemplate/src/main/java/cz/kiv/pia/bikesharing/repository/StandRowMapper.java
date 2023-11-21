package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.repository.dto.StandDTO;
import cz.kiv.pia.bikesharing.repository.dto.StandLocationDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * {@link RowMapper} mapping database rows into {@link StandDTO} instances.
 */
@Service
public class StandRowMapper implements RowMapper<StandDTO> {
    @Override
    public StandDTO mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var name = rs.getString("name");
        var longitude = rs.getString("longitude");
        var latitude = rs.getString("latitude");
        return new StandDTO(UUID.fromString(id), name, new StandLocationDTO(longitude, latitude));
    }
}
