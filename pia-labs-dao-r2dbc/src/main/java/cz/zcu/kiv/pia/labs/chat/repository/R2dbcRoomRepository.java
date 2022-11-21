package cz.zcu.kiv.pia.labs.chat.repository;

import cz.zcu.kiv.pia.labs.chat.domain.Room;
import cz.zcu.kiv.pia.labs.chat.mapper.RoomMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * R2DBC-based implementation of {@link RoomRepository}.
 */
@Primary
@Repository
public class R2dbcRoomRepository implements RoomRepository {
    private static final RoomMapper ROOM_MAPPER = new RoomMapper();
    private final DatabaseClient databaseClient;

    public R2dbcRoomRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Room> createRoom(Room room) {
        var sql = """
        INSERT INTO room (id, name, administrator_id)
        VALUES (UUID_TO_BIN(:id), :name, UUID_TO_BIN(:administratorId))
        """;

        return databaseClient.sql(sql)
                .bind("id", room.getId().toString())
                .bind("name", room.getName())
                .bind("administratorId", room.getAdministrator().getId().toString())
                .fetch()
                .rowsUpdated()
                .flatMap(rowsUpdated -> rowsUpdated == 1 ? Mono.just(room) : Mono.empty());
    }

    @Override
    public Flux<Room> findAll() {
        var sql = """
        SELECT BIN_TO_UUID(r.id) AS room_id, r.name, BIN_TO_UUID(u.id) AS user_id, u.username
        FROM room r
        JOIN user u ON r.administrator_id = u.id
        """;

        return databaseClient.sql(sql)
                .map(ROOM_MAPPER)
                .all();
    }

    @Override
    public Flux<Room> findByName(String name) {
        var sql = """
        SELECT BIN_TO_UUID(r.id) AS room_id, r.name, BIN_TO_UUID(u.id) AS user_id, u.username
        FROM room r
        JOIN user u ON r.administrator_id = u.id
        WHERE r.name LIKE :name
        """;

        return databaseClient.sql(sql)
                .bind("name", "%" + name + "%")
                .map(ROOM_MAPPER)
                .all();
    }

    @Override
    public Mono<Room> findById(UUID id) {
        var sql = """
        SELECT BIN_TO_UUID(r.id) AS room_id, r.name, BIN_TO_UUID(u.id) AS user_id, u.username
        FROM room r
        JOIN user u ON r.administrator_id = u.id
        WHERE r.id = UUID_TO_BIN(:id)
        """;

        return databaseClient.sql(sql)
                .bind("id", id.toString())
                .map(ROOM_MAPPER)
                .one();
    }
}
