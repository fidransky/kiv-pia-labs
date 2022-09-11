package cz.zcu.kiv.pia.labs;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", dispatcherTypes = DispatcherType.ERROR)
public class ErrorFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Throwable exception = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        exception.printStackTrace(System.out);

        super.doFilter(req, res, chain);
    }
}