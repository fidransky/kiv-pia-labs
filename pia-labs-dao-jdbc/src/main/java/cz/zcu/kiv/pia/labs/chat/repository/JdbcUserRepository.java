package cz.zcu.kiv.pia.labs.chat.repository;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import cz.zcu.kiv.pia.labs.chat.mapper.UserMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * JDBC-based implementation of {@link UserRepository}.
 */
@Primary
@Repository
public class JdbcUserRepository implements UserRepository {
    private static final UserMapper USER_MAPPER = new UserMapper();
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mono<User> registerUser(User user) {
        var sql = "INSERT INTO user (id, username) VALUES (UUID_TO_BIN(?), ?)";

        var params = new MapSqlParameterSource();
        params.addValue("id", user.getId().toString());
        params.addValue("username", user.getUsername());

        var rowsUpdated = jdbcTemplate.update(sql, params);

        return rowsUpdated == 1 ? Mono.just(user) : Mono.empty();
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        var sql = "SELECT BIN_TO_UUID(id) AS user_id, username FROM user WHERE username = ?";

        try {
            var user = jdbcTemplate.queryForObject(sql, USER_MAPPER, username);

            return Mono.justOrEmpty(user);

        } catch (DataAccessException e) {
            return Mono.empty();
        }
    }
}
