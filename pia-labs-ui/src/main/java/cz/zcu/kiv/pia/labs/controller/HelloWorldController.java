package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {
    // inject NumberService bean named "myRandomNumberService" via property-based dependency injection
    @Autowired
    @Qualifier("myRandomNumberService")
    private NumberService numberService;

    // create endpoint at http://localhost:8080/hello
    @GetMapping(path = "/hello", produces = MediaType.TEXT_HTML_VALUE + "; charset=utf-8")
    @ResponseBody
    public String sayHello(@RequestParam(value = "from", required = false) String from) {
        var builder = new StringBuilder("Hello World");
        if (from == null) {
            throw new RuntimeException("The 'from' parameter is missing.");
        } else {
            builder.append(" from ").append(from);
        }
        builder.append("!");

        return "<h1>" + builder + "</h1>";
    }

    // create endpoint at http://localhost:8080/number
    @GetMapping(path = "/number")
    @ResponseBody
    public Number printNumber() {
        return numberService.getNumber();
    }
}
