package cz.zcu.kiv.pia.labs.chat.repository;

import cz.zcu.kiv.pia.labs.chat.domain.Room;
import cz.zcu.kiv.pia.labs.chat.mapper.RoomMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * JDBC-based implementation of {@link RoomRepository}.
 */
@Primary
@Repository
public class JdbcRoomRepository implements RoomRepository {
    private static final RoomMapper ROOM_MAPPER = new RoomMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcRoomRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mono<Room> createRoom(Room room) {
        var sql = """
        INSERT INTO room (id, name, administrator_id)
        VALUES (UUID_TO_BIN(:id), :name, UUID_TO_BIN(:administratorId))
        """;

        var params = new MapSqlParameterSource();
        params.addValue("id", room.getId().toString());
        params.addValue("name", room.getName());
        params.addValue("administratorId", room.getAdministrator().getId().toString());

        var rowsUpdated = jdbcTemplate.update(sql, params);

        return rowsUpdated == 1 ? Mono.just(room) : Mono.empty();
    }

    @Override
    public Flux<Room> findAll() {
        var sql = """
        SELECT BIN_TO_UUID(r.id) AS room_id, r.name, BIN_TO_UUID(u.id) AS user_id, u.username
        FROM room r
        JOIN user u ON r.administrator_id = u.id
        """;

        var result = jdbcTemplate.query(sql, ROOM_MAPPER);

        return Flux.fromIterable(result);
    }

    @Override
    public Flux<Room> findByName(String name) {
        var sql = """
        SELECT BIN_TO_UUID(r.id) AS room_id, r.name, BIN_TO_UUID(u.id) AS user_id, u.username
        FROM room r
        JOIN user u ON r.administrator_id = u.id
        WHERE r.name LIKE :name
        """;

        var params = new MapSqlParameterSource();
        params.addValue("name", "%" + name + "%");

        var result = jdbcTemplate.query(sql, params, ROOM_MAPPER);

        return Flux.fromIterable(result);
    }

    @Override
    public Mono<Room> findById(UUID id) {
        var sql = """
        SELECT BIN_TO_UUID(r.id) AS room_id, r.name, BIN_TO_UUID(u.id) AS user_id, u.username
        FROM room r
        JOIN user u ON r.administrator_id = u.id
        WHERE r.id = UUID_TO_BIN(:id)
        """;

        var params = new MapSqlParameterSource();
        params.addValue("id", id.toString());

        try {
            var result = jdbcTemplate.queryForObject(sql, params, ROOM_MAPPER);

            return Mono.justOrEmpty(result);

        } catch (DataAccessException e) {
            return Mono.empty();
        }
    }
}
