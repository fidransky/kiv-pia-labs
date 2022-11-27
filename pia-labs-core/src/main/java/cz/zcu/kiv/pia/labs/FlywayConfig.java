package cz.zcu.kiv.pia.labs;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {
    @Value("${spring.jdbc.url}")
    private String dataSourceUrl;
    @Value("${spring.jdbc.username}")
    private String dataSourceUsername;
    @Value("${spring.jdbc.password}")
    private String dataSourcePassword;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(dataSourceUrl, dataSourceUsername, dataSourcePassword)
                .load();
    }
}
