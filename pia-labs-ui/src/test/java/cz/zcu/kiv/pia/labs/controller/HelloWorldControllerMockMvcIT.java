package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.service.NumberService;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneOffset;
import java.util.TimeZone;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// This test is identical to {@link HelloWorldControllerMockMvcTest} but it uses @SpringBootTest annotation instead of @WebMvcTest and therefore builds full application context.
@SpringBootTest
@TestPropertySource(properties = {
        // do not use embedded database
        "spring.test.database.replace=none",
        // run tests against real MariaDB database running in Docker using Testcontainers
        "spring.datasource.url=jdbc:tc:mariadb:11:///pia_labs",
})
@AutoConfigureMockMvc
class HelloWorldControllerMockMvcIT {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean("myRandomNumberService")
    private NumberService numberService;

    @BeforeAll
    static void beforeAll() {
        // set timezone to UTC to match MariaDB database configuration
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));

        // needed because of a Testcontainers bug: https://github.com/testcontainers/testcontainers-java/issues/11212
        System.setProperty("api.version", "1.44");
    }

    @Test
    @WithMockUser
    void printNumber() throws Exception {
        var number = 42;

        when(numberService.getNumber()).thenReturn(number);

        mockMvc.perform(get("/number"))
                .andExpect(status().isOk())
                .andExpect(content().string(Integer.toString(number)));
    }

    @Test
    void sayHello() throws Exception {
        var result = mockMvc.perform(get("/hello").queryParam("from", "John Doe"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello world from John Doe!")))
                //.andExpect(xpath("//h1").string("Hello world from John Doe!"))
                .andReturn();

        assertEquals("greeting", result.getModelAndView().getViewName());

        var html = result.getResponse().getContentAsString();
        var doc = Jsoup.parse(html);

        assertEquals("Hello world from John Doe!", doc.select("h1").text());
    }
}