package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.repository.dto.DamageDTO;
import cz.zcu.kiv.pia.labs.repository.dto.DamageLocationDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class DamageRowMapper implements RowMapper<DamageDTO> {
    @Override
    public DamageDTO mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var timestamp = rs.getTimestamp("timestamp");
        var longitude = rs.getString("longitude");
        var latitude = rs.getString("latitude");
        var description = rs.getString("description");

        return new DamageDTO(UUID.fromString(id), timestamp.toInstant(), new DamageLocationDTO(longitude, latitude), description);
    }
}
