package cz.zcu.kiv.pia.labs;

import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@ResponseBody
public class HelloWorldController {

    private static final Logger LOG = getLogger(HelloWorldController.class);

    @GetMapping(path = "/hello", produces = MediaType.TEXT_HTML_VALUE + "; charset=utf-8")
    public String sayHello(@RequestParam String from) {
        var builder = new StringBuilder("Hello World");
        if (from != null) {
            builder.append(" from ").append(from);
        }
        builder.append("!");

        LOG.info(builder.toString());

        return "<h1>" + builder + "</h1>";
    }

    @GetMapping(path = "/greet/{name}", produces = MediaType.TEXT_HTML_VALUE + "; charset=utf-8")
    public String greet(@PathVariable String name) {
        return "<h1>Hello " + name + "!</h1>";
    }
}
