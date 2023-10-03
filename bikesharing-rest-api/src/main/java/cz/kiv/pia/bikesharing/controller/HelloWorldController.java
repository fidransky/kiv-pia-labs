package cz.kiv.pia.bikesharing.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Period;

@Controller
@ResponseBody
public class HelloWorldController {
    private final Period bikeServiceInterval;

    // autowire serviceInterval bean via constructor based dependency injection
    public HelloWorldController(Period serviceInterval) {
        this.bikeServiceInterval = serviceInterval;
    }

    // create API endpoint at http://localhost:8080/hello
    @GetMapping(path = "/hello", produces = MediaType.TEXT_HTML_VALUE + "; charset=utf-8")
    public String sayHello(@RequestParam(value = "from", required = false) String from) {
        var builder = new StringBuilder("Hello World");
        if (from != null) {
            builder.append(" from ").append(from);
        }
        builder.append("!");

        return builder.toString();
    }

    @GetMapping("/service-interval")
    public String serviceInterval() {
        return "Bike service interval is set to %d months.".formatted(bikeServiceInterval.getMonths());
    }
}
