package cz.zcu.kiv.pia.labs.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("request")
    public HttpServletRequest addRequestToModel(HttpServletRequest request) {
        return request;
    }
}
