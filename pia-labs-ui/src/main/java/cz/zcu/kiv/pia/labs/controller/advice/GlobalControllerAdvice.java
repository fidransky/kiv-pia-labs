package cz.zcu.kiv.pia.labs.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("request")
    public HttpServletRequest addRequestToModel(HttpServletRequest request) {
        return request;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex, HttpServletRequest request) throws Exception {
        if (request.getRequestURI().startsWith("/swagger-ui")) {
            throw ex; // let Swagger handle it
        }

        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("message", ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred");
        modelAndView.addObject("request", request);

        return modelAndView;
    }
}
