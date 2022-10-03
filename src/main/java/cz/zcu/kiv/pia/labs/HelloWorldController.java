package cz.zcu.kiv.pia.labs;

import cz.zcu.kiv.pia.labs.number.reactive.ReactiveNumberService;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@ResponseBody
public class HelloWorldController {

    private static final Logger LOG = getLogger(HelloWorldController.class);

    private final ReactiveNumberService numberService;

    public HelloWorldController(ReactiveNumberService randomNumberService) {
        this.numberService = randomNumberService;
    }

    @GetMapping(path = "/hello", produces = MediaType.TEXT_HTML_VALUE + "; charset=utf-8")
    public Mono<String> sayHello(@RequestParam String from) {
        var builder = new StringBuilder("Hello World");
        if (from != null) {
            builder.append(" from ").append(from);
        }
        builder.append("!");

        LOG.info(builder.toString());

        return Mono.just("<h1>" + builder + "</h1>");
    }

    @GetMapping(path = "/greet/{name}", produces = MediaType.TEXT_HTML_VALUE + "; charset=utf-8")
    public Mono<String> greet(@PathVariable String name) {
        return Mono.just("<h1>Hello " + name + "!</h1>");
    }

    @GetMapping("/number")
    public Mono<String> getRandomNumber() {
        return numberService.getNumber().map(Number::toString);
    }
}
