package cz.zcu.kiv.pia.labs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootTest
@TestPropertySource(properties = {
        // do not use embedded database
        "spring.test.database.replace=none",
        // run tests against real MariaDB database running in Docker using Testcontainers
        "spring.datasource.url=jdbc:tc:mariadb:11:///pia_labs",
})
class ApplicationTests {

    @BeforeAll
    static void beforeAll() {
        // set timezone to UTC to match MariaDB database configuration
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));

        // needed because of a Testcontainers bug: https://github.com/testcontainers/testcontainers-java/issues/11212
        System.setProperty("api.version", "1.44");
    }

    @Test
    void contextLoads() {
    }

}
