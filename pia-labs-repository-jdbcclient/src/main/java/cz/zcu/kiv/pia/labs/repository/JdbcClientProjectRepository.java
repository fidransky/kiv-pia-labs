package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Project;
import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

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
                INSERT INTO project (id, customer_id, translator_id, target_language, source_file, translated_file, state, created_at)
                VALUES (:id, :customer_id, :translator_id, :target_language, :source_file, :translated_file, :state, :created_at)
                """;

        var paramSource = new MapSqlParameterSource()
                .addValue("id", project.getId(), SqlParameterSource.TYPE_UNKNOWN)
                .addValue("customer_id", project.getCustomer().getId())
                .addValue("translator_id", project.getTranslator() != null ? project.getTranslator().getId() : null)
                .addValue("target_language", project.getTargetLanguage().toLanguageTag())
                .addValue("source_file", project.getSourceFile())
                .addValue("translated_file", project.getTranslatedFile())
                .addValue("state", project.getState().toString())
                .addValue("created_at", project.getCreatedAt());

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
                WHERE id = :id
                """;

        return jdbcClient.sql(sql)
                .param("id", id)
                .query(rowMapper)
                .single();
    }
}
