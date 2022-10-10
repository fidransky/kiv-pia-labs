package cz.zcu.kiv.pia.labs;

import org.slf4j.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@WebFilter(urlPatterns = "/*", dispatcherTypes = DispatcherType.ERROR)
public class ErrorFilter extends HttpFilter {
    private static final Logger LOG = getLogger(ErrorFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Throwable exception = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        LOG.error(exception.getMessage(), exception);

        super.doFilter(req, res, chain);
    }
}