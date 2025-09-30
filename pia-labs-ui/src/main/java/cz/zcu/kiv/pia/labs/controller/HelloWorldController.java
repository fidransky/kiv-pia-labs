package cz.zcu.kiv.pia.labs.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {
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
}
