package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Project;
import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SimplePropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.security.spec.NamedParameterSpec;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class JdbcClientProjectRepository implements ProjectRepository {

    private static final Logger LOG = getLogger(JdbcClientProjectRepository.class);

    private final JdbcClient jdbcClient;
    private final RowMapper<Project> rowMapper;

    public JdbcClientProjectRepository(JdbcClient jdbcClient, RowMapper<Project> rowMapper) {
        this.jdbcClient = jdbcClient;
        this.rowMapper = rowMapper;
    }

    @Override
    public void store(Project project) {
        var sql = """
                INSERT INTO project (id, timestamp, longitude, latitude, description)
                VALUES (:id)
                """;

        var paramSource = new MapSqlParameterSource()
                .addValue("id", project.getId());

        jdbcClient.sql(sql)
                .paramSource(paramSource)
                .update();
    }

    @Override
    public List<Project> getAll() {
        var sql = """
                SELECT * FROM project
                ORDER BY created_at DESC
                """;

        return jdbcClient.sql(sql)
                .query(rowMapper)
                .list();
    }

    @Override
    public Project findById(UUID id) {
        var sql = """
                SELECT * FROM project
                WHERE id = UUID_TO_BIN(:id)
                """;

        return jdbcClient.sql(sql)
                .param("id", id)
                .query(rowMapper)
                .single();
    }
}
