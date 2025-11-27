package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.domain.ProjectState;
import cz.zcu.kiv.pia.labs.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectRowMapperTest {
    final ProjectRowMapper rowMapper = new ProjectRowMapper();

    @Test
    void mapRow() throws SQLException {
        // test data
        var project = new Project(UUID.randomUUID(), new User(UUID.randomUUID()), new User(UUID.randomUUID()), Locale.forLanguageTag("CS"), new byte[]{0}, null, ProjectState.CREATED, Instant.parse("2025-11-25T08:27:10.123456Z"));

        // mocks
        var resultSet = mock(ResultSet.class);
        when(resultSet.getString("id")).thenReturn(project.getId().toString());
        when(resultSet.getString("customer_id")).thenReturn(project.getCustomer().getId().toString());
        when(resultSet.getString("translator_id")).thenReturn(project.getTranslator().getId().toString());
        when(resultSet.getString("target_language")).thenReturn(project.getTargetLanguage().toLanguageTag());
        when(resultSet.getBytes("source_file")).thenReturn(project.getSourceFile());
        when(resultSet.getBytes("translated_file")).thenReturn(project.getTranslatedFile());
        when(resultSet.getString("state")).thenReturn(project.getState().toString());
        when(resultSet.getTimestamp("created_at")).thenReturn(Timestamp.from(project.getCreatedAt()));

        // tested method
        var result = rowMapper.mapRow(resultSet, 42);

        // verifications
        assertEquals(project, result);
    }
}