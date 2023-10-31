package cz.kiv.pia.bikesharing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {
    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "from", required = false, defaultValue = "me") String from, Model model) {
        model.addAttribute("from", from);

        return "hello";
    }
}
