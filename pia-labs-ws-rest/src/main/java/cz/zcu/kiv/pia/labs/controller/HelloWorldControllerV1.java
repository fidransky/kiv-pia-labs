package cz.zcu.kiv.pia.labs.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class HelloWorldControllerV1 implements HelloApi {
    @Override
    public ResponseEntity<String> sayHello(String from) {
        var builder = new StringBuilder("Hello World");
        if (from == null) {
            throw new RuntimeException("The 'from' parameter is missing.");
        } else {
            builder.append(" from ").append(from);
        }
        builder.append("!");

        return new ResponseEntity<>("<h1>" + builder + "</h1>", new HttpHeaders(), HttpStatus.OK);
    }
}
