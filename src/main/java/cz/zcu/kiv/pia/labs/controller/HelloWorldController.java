package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.service.NumberService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class HelloWorldController {
    private final NumberService numberService;

    // constructor-based dependency injection
    // we're asking DI container to provide a bean which implements NumberService interface
    public HelloWorldController(NumberService numberService) {
        this.numberService = numberService;
    }

    // create endpoint at http://localhost:8080/hello
    @GetMapping(path = "/hello", produces = MediaType.TEXT_HTML_VALUE + "; charset=utf-8")
    public String sayHello(@RequestParam(value = "from", required = false) String from) {
        var builder = new StringBuilder("Hello World");
        if (from != null) {
            builder.append(" from ").append(from);
        }
        builder.append("!");

        return "<h1>" + builder + "</h1>";
    }

    @GetMapping("/number")
    public String getRandomNumber() {
        return numberService.getNumber().toString();
    }
}
