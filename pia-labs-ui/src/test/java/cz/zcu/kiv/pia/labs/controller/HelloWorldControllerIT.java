package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.service.NumberService;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.ZoneOffset;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        // do not use embedded database
        "spring.test.database.replace=none",
        // run tests against real MariaDB database running in Docker using Testcontainers
        "spring.datasource.url=jdbc:tc:mariadb:11:///pia_labs",
})
class HelloWorldControllerIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean("myRandomNumberService")
    private NumberService numberService;

    @BeforeAll
    static void beforeAll() {
        // set timezone to UTC to match MariaDB database configuration
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));

        // needed because of a Testcontainers bug: https://github.com/testcontainers/testcontainers-java/issues/11212
        System.setProperty("api.version", "1.44");
    }

    @Disabled("Requires authentication")
    @Test
    void printNumber() {
        var number = 42;

        when(numberService.getNumber()).thenReturn(number);

        var result = restTemplate.getForEntity("http://localhost:" + port + "/number", String.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(Integer.toString(number), result.getBody());
    }

    @Test
    void sayHello() {
        var result = restTemplate.getForEntity("http://localhost:" + port + "/hello?from={0}", String.class, "John Doe");

        var html = result.getBody();
        var doc = Jsoup.parse(html);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Hello world from John Doe!", doc.select("h1").text());
    }
}