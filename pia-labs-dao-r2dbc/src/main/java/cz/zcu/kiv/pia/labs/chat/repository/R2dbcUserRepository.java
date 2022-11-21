package cz.zcu.kiv.pia.labs.chat.repository;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import cz.zcu.kiv.pia.labs.chat.mapper.UserMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * R2DBC-based implementation of {@link UserRepository}.
 */
@Primary
@Repository
public class R2dbcUserRepository implements UserRepository {
    private static final UserMapper USER_MAPPER = new UserMapper();
    private final DatabaseClient databaseClient;

    public R2dbcUserRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<User> registerUser(User user) {
        var sql = "INSERT INTO user (id, username) VALUES (:id, :username)";

        return databaseClient.sql(sql)
                .bind("id", user.getId().toString())
                .bind("username", user.getUsername())
                .fetch()
                .rowsUpdated()
                .flatMap(rowsUpdated -> rowsUpdated == 1 ? Mono.just(user) : Mono.empty());
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        var sql = "SELECT BIN_TO_UUID(id) AS user_id, username FROM user WHERE username = :username";

        return databaseClient.sql(sql)
                .bind("username", username)
                .map(USER_MAPPER)
                .one()
                .map(UserDetails.class::cast);
    }
}
