package cz.zcu.kiv.pia.labs.chat.mapper;

import cz.zcu.kiv.pia.labs.chat.domain.Room;
import cz.zcu.kiv.pia.labs.chat.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Maps {@link ResultSet} to {@link Room}.
 *
 * @see UserMapper
 */
public class RoomMapper implements RowMapper<Room> {
    private static final UserMapper USER_MAPPER = new UserMapper();

    @Override
    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getObject("room_id", String.class);
        String name = rs.getObject("name", String.class);
        User administrator = USER_MAPPER.mapRow(rs, rowNum);
        return new Room(UUID.fromString(id), name, administrator);
    }
}
