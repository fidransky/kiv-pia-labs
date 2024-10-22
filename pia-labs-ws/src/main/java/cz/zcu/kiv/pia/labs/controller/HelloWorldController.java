package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.service.NumberService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class HelloWorldController implements HelloApi {
    private final NumberService numberService;

    // constructor-based dependency injection
    // we're asking DI container to provide a bean which implements NumberService interface
    public HelloWorldController(NumberService numberService) {
        this.numberService = numberService;
    }

    @Override
    public ResponseEntity<String> sayHello(String from) {
        var builder = new StringBuilder("Hello World");
        if (from != null) {
            builder.append(" from ").append(from);
        }
        builder.append("!");

        return ResponseEntity.ok("<h1>" + builder + "</h1>");
    }

    @GetMapping("/number")
    public String getRandomNumber() {
        return numberService.getNumber().toString();
    }
}
