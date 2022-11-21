package cz.zcu.kiv.pia.labs.chat.mapper;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import io.r2dbc.spi.Row;

import java.util.UUID;
import java.util.function.Function;

/**
 * Maps {@link Row} to {@link User}.
 */
public class UserMapper implements Function<Row, User> {
    @Override
    public User apply(Row row) {
        String id = row.get("user_id", String.class);
        String username = row.get("username", String.class);
        return new User(UUID.fromString(id), username);
    }
}
