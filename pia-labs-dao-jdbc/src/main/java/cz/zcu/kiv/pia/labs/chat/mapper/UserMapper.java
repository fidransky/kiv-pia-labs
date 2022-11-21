package cz.zcu.kiv.pia.labs.chat.mapper;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Maps {@link ResultSet} to {@link User}.
 */
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getObject("user_id", String.class);
        String username = rs.getObject("username", String.class);
        return new User(UUID.fromString(id), username);
    }
}
