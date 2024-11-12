package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.repository.dto.DamageDTO;
import org.slf4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Primary
@Repository
public class JdbcTemplateDamageRepository implements DamageRepository {
    private static final Logger LOG = getLogger(JdbcTemplateDamageRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<DamageDTO> rowMapper;

    public JdbcTemplateDamageRepository(JdbcTemplate jdbcTemplate, DamageRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public Damage create(Damage damage) {
        var sql = """
                INSERT INTO damage (id, timestamp, longitude, latitude, description)
                VALUES (?, ?, ?, ?, ?)
                """;

        try {
            List<Object> args = new ArrayList<>();
            args.add(damage.getId().toString());
            args.add(Timestamp.from(damage.getTimestamp()));
            args.add(damage.getLocation().getLongitude());
            args.add(damage.getLocation().getLatitude());
            args.add(damage.getDescription());

            var rowsUpdated = jdbcTemplate.update(sql, args.toArray());

            return rowsUpdated == 1 ? damage : null;

        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Collection<Damage> retrieveReportedDamage(User user) {
        var sql = """
                SELECT id, timestamp, longitude, latitude, description
                FROM damage
                """;

        Collection<Damage> result = new ArrayList<>();

        try {
            var resultSet = jdbcTemplate.query(sql, rowMapper);

            for (DamageDTO damageDTO : resultSet) {
                var id = damageDTO.id();
                var timestamp = damageDTO.timestamp();
                var location = new Location(damageDTO.location().longitude(), damageDTO.location().latitude());
                var description = damageDTO.description();

                result.add(new Damage(id, null, null, timestamp, location, description, null));
            }

            return result;

        } catch (DataAccessException e) {
            LOG.error(e.getMessage(), e);
            return result;
        }
    }
}
