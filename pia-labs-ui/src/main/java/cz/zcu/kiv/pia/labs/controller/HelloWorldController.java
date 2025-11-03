package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Controller
public class HelloWorldController {
    // inject NumberService bean named "myRandomNumberService" via property-based dependency injection
    @Autowired
    @Qualifier("myRandomNumberService")
    private NumberService numberService;
    @Autowired
    private MessageSource messageSource;

    // create endpoint at http://localhost:8080/hello
    @GetMapping(path = "/hello", produces = MediaType.TEXT_HTML_VALUE + "; charset=utf-8")
    public String sayHello(
            @RequestParam(value = "from", required = false) String from,
            Model model,
            Locale locale
    ) {
        // build resulting message
        if (from == null) {
            throw new RuntimeException("The 'from' parameter is missing.");
        }

        var message = messageSource.getMessage("greeting.message", new Object[]{ from }, locale);

        // add message to model
        model.addAttribute("message", message);

        // add other model attributes
        model.addAttribute("from", from);

        return "greeting";
    }

    // create endpoint at http://localhost:8080/number
    @GetMapping(path = "/number")
    @ResponseBody
    public Number printNumber() {
        return numberService.getNumber();
    }
}
