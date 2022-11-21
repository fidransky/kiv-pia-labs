package cz.zcu.kiv.pia.labs.chat.repository;

import cz.zcu.kiv.pia.labs.chat.domain.User;
import cz.zcu.kiv.pia.labs.chat.mapper.UserMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Plain JDBC-based implementation of {@link UserRepository}.
 */
//@Primary
//@Repository
public class PlainJdbcUserRepository implements UserRepository {
    private static final UserMapper USER_MAPPER = new UserMapper();
    private final DataSource dataSource;

    public PlainJdbcUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Mono<User> registerUser(User user) {
        var sql = "INSERT INTO user (id, username) VALUES (UUID_TO_BIN(?), ?)";

        try (var statement = dataSource.getConnection().prepareStatement(sql)) {
            statement.setString(1, user.getId().toString());
            statement.setString(2, user.getUsername());
            var rowsUpdated = statement.executeUpdate();

            return rowsUpdated == 1 ? Mono.just(user) : Mono.empty();

        } catch (SQLException e) {
            return Mono.empty();
        }
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        var sql = "SELECT BIN_TO_UUID(id) AS user_id, username FROM user WHERE username = ?";

        try (var statement = dataSource.getConnection().prepareStatement(sql)) {
            statement.setString(1, username);
            var resultSet = statement.executeQuery();

            var user = USER_MAPPER.mapRow(resultSet, resultSet.getRow());

            return Mono.justOrEmpty(user);

        } catch (SQLException e) {
            return Mono.empty();
        }
    }
}
