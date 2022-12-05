package cz.zcu.kiv.pia.labs.chat.repository;

import cz.zcu.kiv.pia.labs.FlywayConfig;
import cz.zcu.kiv.pia.labs.JdbcConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        JdbcRoomRepositoryIT.ITConfiguration.class,
        JdbcConfig.class,
        FlywayConfig.class,
})
@Testcontainers
@Transactional
@Sql
class JdbcRoomRepositoryIT {

    // will be shared between test methods
    @Container
    private static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:8")
            .withDatabaseName("pia_labs");

    @Autowired
    private RoomRepository roomRepository;

    @DynamicPropertySource
    static void registerJdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.jdbc.driver-class-name", mySQLContainer::getDriverClassName);
        registry.add("spring.jdbc.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.jdbc.username", mySQLContainer::getUsername);
        registry.add("spring.jdbc.password", mySQLContainer::getPassword);
    }

    @Test
    void findAll() {
        var result = roomRepository.findAll().collectList().block();

        assertFalse(result.isEmpty());
    }

    @Test
    void findByName() {
        var result = roomRepository.findByName("running").collectList().block();

        assertFalse(result.isEmpty());
    }

    @Test
    void findById() {
        var roomId = UUID.fromString("75ae2ef7-8cf5-48d3-b03f-d137a5d43b1f");
        var result = roomRepository.findById(roomId).block();

        assertEquals(roomId, result.getId());
    }

    @Configuration
    @ComponentScan(basePackages = "cz.zcu.kiv.pia.labs.chat.repository", includeFilters = @ComponentScan.Filter(classes = Repository.class))
    static class ITConfiguration {
    }
}
