package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Project;
import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
                INSERT INTO project (id, created_at, source_language, target_language, source_file, translated_file, translator_id, status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (var statement = dataSource.getConnection().prepareStatement(sql)) {
            statement.setString(1, project.getId().toString());
            statement.setTimestamp(2, Timestamp.from(project.getCreatedAt()));
            statement.setString(3, project.getTargetLanguage().toString());
            statement.setBytes(4, project.getSourceFile());
            statement.setBytes(5, project.getTranslatedFile());
            statement.setString(6, project.getTranslator().getId().toString());
            statement.setString(7, project.getState().toString());

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

        try (var statement = dataSource.getConnection().createStatement()) {
            var resultSet = statement.executeQuery(sql);

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
                WHERE id = :id
                """;

        try (var statement = dataSource.getConnection().createStatement()) {
            var resultSet = statement.executeQuery(sql);

            return rowMapper.mapRow(resultSet, resultSet.getRow());

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}
