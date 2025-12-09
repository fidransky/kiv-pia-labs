package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.configuration.SecurityConfiguration;
import cz.zcu.kiv.pia.labs.service.NumberService;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloWorldController.class)
@Import(SecurityConfiguration.class)
class HelloWorldControllerMockMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean("myRandomNumberService")
    private NumberService numberService;

    @Test
    @WithMockUser
    void printNumber() throws Exception {
        // test data
        var number = 42;

        // mocks
        when(numberService.getNumber()).thenReturn(number);

        // verifications
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