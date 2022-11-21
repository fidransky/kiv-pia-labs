package cz.zcu.kiv.pia.labs.chat.mapper;

import cz.zcu.kiv.pia.labs.chat.domain.Room;
import cz.zcu.kiv.pia.labs.chat.domain.User;
import io.r2dbc.spi.Row;

import java.util.UUID;
import java.util.function.Function;

/**
 * Maps {@link Row} to {@link Room}.
 *
 * @see UserMapper
 */
public class RoomMapper implements Function<Row, Room> {
    private static final UserMapper USER_MAPPER = new UserMapper();

    @Override
    public Room apply(Row row) {
        String id = row.get("room_id", String.class);
        String name = row.get("name", String.class);
        User administrator = USER_MAPPER.apply(row);
        return new Room(UUID.fromString(id), name, administrator);
    }
}
