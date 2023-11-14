package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Location;
import cz.kiv.pia.bikesharing.domain.Stand;
import cz.kiv.pia.bikesharing.repository.dto.StandDTO;
import org.slf4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Plain JDBC-based implementation of {@link StandRepository}.
 */
//@Primary
//@Repository
public class JdbcStandRepository implements StandRepository {
    private static final Logger LOG = getLogger(JdbcStandRepository.class);

    private final DataSource dataSource;
    private final RowMapper<StandDTO> rowMapper;

    public JdbcStandRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.rowMapper = new StandRowMapper();
    }

    @Override
    public Collection<Stand> getAll() {
        var sql = """
                SELECT BIN_TO_UUID(id) AS id, name, longitude, latitude FROM stand
                """;

        Collection<Stand> result = new ArrayList<>();

        try (var statement = dataSource.getConnection().prepareStatement(sql)) {
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                var standDTO = rowMapper.mapRow(resultSet, resultSet.getRow());

                var id = standDTO.id();
                var name = standDTO.name();
                var location = new Location(standDTO.location().longitude(), standDTO.location().latitude());

                result.add(new Stand(id, name, location));
            }

            return result;

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return result;
        }
    }
}
