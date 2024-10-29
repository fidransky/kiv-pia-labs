package cz.zcu.kiv.pia.labs.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {
    // create endpoint at http://localhost:8080/hello
    @GetMapping(path = "/hello", produces = MediaType.TEXT_HTML_VALUE + "; charset=utf-8")
    public String sayHello(@RequestParam(value = "from", required = false) String from, Model model) {
        var builder = new StringBuilder("Hello World");
        if (from != null) {
            builder.append(" from ").append(from);
        }
        builder.append("!");

        model.addAttribute("input", from);
        model.addAttribute("output", builder);

        return "hello";
    }
}