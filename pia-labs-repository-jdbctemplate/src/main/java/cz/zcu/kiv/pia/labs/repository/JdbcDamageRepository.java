package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.repository.dto.DamageDTO;
import org.slf4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class JdbcDamageRepository implements DamageRepository {
    private static final Logger LOG = getLogger(JdbcDamageRepository.class);

    private final DataSource dataSource;
    private final RowMapper<DamageDTO> rowMapper;

    public JdbcDamageRepository(DataSource dataSource, DamageRowMapper rowMapper) {
        this.dataSource = dataSource;
        this.rowMapper = rowMapper;
    }

    @Override
    public Damage create(Damage damage) {
        var sql = """
                INSERT INTO damage (id, timestamp, longitude, latitude, description)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (var statement = dataSource.getConnection().prepareStatement(sql)) {
            statement.setString(1, damage.getId().toString());
            statement.setTimestamp(2, Timestamp.from(damage.getTimestamp()));
            statement.setBigDecimal(3, damage.getLocation().getLongitude());
            statement.setBigDecimal(4, damage.getLocation().getLatitude());
            statement.setString(5, damage.getDescription());

            var rowsUpdated = statement.executeUpdate();

            return rowsUpdated == 1 ? damage : null;

        } catch (SQLException e) {
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
            var statement = dataSource.getConnection().createStatement();
            var resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                var damageDTO = rowMapper.mapRow(resultSet, resultSet.getRow());

                var id = damageDTO.id();
                var timestamp = damageDTO.timestamp();
                var location = new Location(damageDTO.location().longitude(), damageDTO.location().latitude());
                var description = damageDTO.description();

                result.add(new Damage(id, null, null, timestamp, location, description, null));
            }

            return result;

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return result;
        }
    }
}
