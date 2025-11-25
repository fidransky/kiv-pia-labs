package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.domain.ProjectState;
import cz.zcu.kiv.pia.labs.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.UUID;

@Component
public class ProjectRowMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        var id = UUID.fromString(rs.getString("id"));
        var customerId = rs.getString("customer_id");
        var translatorId = rs.getString("translator_id");
        var targetLanguage = Locale.forLanguageTag(rs.getString("target_language"));
        var sourceFile = rs.getBytes("source_file");
        var translatedFile = rs.getBytes("translated_file");
        var state = ProjectState.valueOf(rs.getString("state"));
        var createdAt = rs.getTimestamp("created_at").toInstant();

        return new Project(
                id,
                new User(UUID.fromString(customerId)),
                translatorId != null ? new User(UUID.fromString(translatorId)) : null,
                targetLanguage,
                sourceFile,
                translatedFile,
                state,
                createdAt
        );
    }
}
