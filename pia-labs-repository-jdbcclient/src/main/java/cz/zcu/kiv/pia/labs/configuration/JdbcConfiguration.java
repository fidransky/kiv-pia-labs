package cz.zcu.kiv.pia.labs.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class JdbcConfiguration {

    @Bean
    @DependsOn("flyway")
    public DataSourceInitializer dataSourceInitializer(
            @Value("classpath:init.sql") Resource initializerScript,
            DataSource dataSource
    ) {
        var databaseInitializer = new ResourceDatabasePopulator(initializerScript);
        databaseInitializer.setContinueOnError(true);

        var initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databaseInitializer);
        return initializer;
    }
}
