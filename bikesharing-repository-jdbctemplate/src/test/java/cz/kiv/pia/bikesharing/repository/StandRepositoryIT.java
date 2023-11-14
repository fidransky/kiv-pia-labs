package cz.kiv.pia.bikesharing.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;

// autoconfigure DataSource and JdbcTemplate beans
@JdbcTest
@TestPropertySource(properties = {
        // do not use embedded database
        "spring.test.database.replace=none",
        // run tests against real MySQL database running in Docker using Testcontainers
        "spring.datasource.url=jdbc:tc:mysql:8:///bikesharing",
})
// populate database with data from StandRepositoryIT.sql script in test resources
@Sql
class StandRepositoryIT {
    @Autowired
    private StandRepository standRepository;

    @Test
    void getAll() {
        var result = standRepository.getAll();

        assertEquals(2, result.size());
    }

    // nested @Configuration is loaded into Spring application context by default
    @TestConfiguration
    // scan for @Repository beans within given base package
    @ComponentScan(basePackages = "cz.kiv.pia.bikesharing.repository", includeFilters = @ComponentScan.Filter(classes = Repository.class))
    static class ITConfiguration {
        // serviceInterval bean is needed for InMemoryBikeRepository, gotta mock it
        @Bean
        public Period serviceInterval() {
            return Period.ofMonths(2);
        }
    }
}
