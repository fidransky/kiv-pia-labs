package cz.zcu.kiv.pia.labs;

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.ZoneId;

@Configuration
@EnableTransactionManagement
public class R2dbcConfig {
    @Value("${spring.r2dbc.host}")
    private String dataSourceHost;
    @Value("${spring.r2dbc.port}")
    private int dataSourcePort;
    @Value("${spring.r2dbc.schema}")
    private String dataSourceSchema;
    @Value("${spring.r2dbc.username}")
    private String dataSourceUsername;
    @Value("${spring.r2dbc.password}")
    private String dataSourcePassword;
    @Value("classpath:init.sql")
    private Resource initializerScript;

    @Bean
    public ConnectionFactory connectionFactory() {
        var configuration = MySqlConnectionConfiguration.builder()
                .host(dataSourceHost)
                .port(dataSourcePort)  // optional, default 3306
                .username(dataSourceUsername)
                .password(dataSourcePassword) // optional, default null, null means has no password
                .database(dataSourceSchema) // optional, default null, null means not specifying the database
                .serverZoneId(ZoneId.of("Europe/Prague"))
                .build();

        return MySqlConnectionFactory.from(configuration);
    }

    @Bean
    public R2dbcTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    public DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                .build();
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(initializerScript));
        return initializer;
    }
}
