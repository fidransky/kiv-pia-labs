package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Location;
import cz.kiv.pia.bikesharing.domain.Stand;
import cz.kiv.pia.bikesharing.repository.dto.StandDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * {@link JdbcTemplate}-based implementation of {@link StandRepository}.
 */
@Primary
@Repository
public class JdbcTemplateStandRepository implements StandRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<StandDTO> rowMapper;

    public JdbcTemplateStandRepository(JdbcTemplate jdbcTemplate, StandRowMapper standRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = standRowMapper;
    }

    @Override
    public Collection<Stand> getAll() {
        var sql = """
                SELECT BIN_TO_UUID(id) AS id, name, longitude, latitude FROM stand
                """;

        var result = jdbcTemplate.query(sql, rowMapper);

        return result.stream()
                .map(standDTO -> {
                    var id = standDTO.id();
                    var name = standDTO.name();
                    var location = new Location(standDTO.location().longitude(), standDTO.location().latitude());
                    return new Stand(id, name, location);
                })
                .toList();
    }
}
