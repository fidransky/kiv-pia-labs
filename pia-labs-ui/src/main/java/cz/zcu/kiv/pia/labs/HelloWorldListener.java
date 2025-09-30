package cz.zcu.kiv.pia.labs;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@WebListener
public class HelloWorldListener implements ServletContextListener, ServletRequestListener {
    private static final String REQUEST_START_ATTRIBUTE_NAME = "requestStartedDateTime";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // do nothing
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        sre.getServletRequest().setAttribute(REQUEST_START_ATTRIBUTE_NAME, LocalDateTime.now());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        final var request = sre.getServletRequest();
        final var started = (LocalDateTime) request.getAttribute(REQUEST_START_ATTRIBUTE_NAME);
        final var now = LocalDateTime.now();

        final var millis = ChronoUnit.MILLIS.between(started, now);
        System.out.println("Request took " + millis + " ms.");
    }
}
