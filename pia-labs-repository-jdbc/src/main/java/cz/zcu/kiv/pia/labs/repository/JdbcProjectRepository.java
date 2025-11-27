package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Project;
import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class JdbcProjectRepository implements ProjectRepository {

    private static final Logger LOG = getLogger(JdbcProjectRepository.class);

    private final DataSource dataSource;
    private final RowMapper<Project> rowMapper;

    public JdbcProjectRepository(DataSource dataSource, ProjectRowMapper rowMapper) {
        this.dataSource = dataSource;
        this.rowMapper = rowMapper;
    }

    @Override
    public void store(Project project) {
        var sql = """
                INSERT INTO project (id, customer_id, translator_id, target_language, source_file, translated_file, state, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try {
            var connection = DataSourceUtils.getConnection(dataSource);

            var statement = connection.prepareStatement(sql);
            statement.setString(1, project.getId().toString());
            statement.setString(2, project.getCustomer().getId().toString());
            statement.setString(3, Optional.ofNullable(project.getTranslator()).map(user -> user.getId().toString()).orElse(null));
            statement.setString(4, project.getTargetLanguage().toLanguageTag());
            statement.setBytes(5, project.getSourceFile());
            statement.setBytes(6, project.getTranslatedFile());
            statement.setString(7, project.getState().toString());
            statement.setTimestamp(8, Timestamp.from(project.getCreatedAt()));

            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Project> getAll() {
        var sql = """
                SELECT * FROM project
                ORDER BY created_at DESC
                """;

        var result = new ArrayList<Project>();

        try {
            var connection = DataSourceUtils.getConnection(dataSource);

            var statement = connection.prepareStatement(sql);

            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                var project = rowMapper.mapRow(resultSet, resultSet.getRow());
                result.add(project);
            }

            return result;

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return result;
        }
    }

    @Override
    public Project findById(UUID id) {
        var sql = """
                SELECT * FROM project
                WHERE id = ?
                """;

        try {
            var connection = DataSourceUtils.getConnection(dataSource);

            var statement = connection.prepareStatement(sql);
            statement.setString(1, id.toString());

            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return rowMapper.mapRow(resultSet, resultSet.getRow());
            }

            return null;

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}
