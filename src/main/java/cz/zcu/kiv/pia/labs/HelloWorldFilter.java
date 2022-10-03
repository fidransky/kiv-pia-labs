package cz.zcu.kiv.pia.labs;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class HelloWorldFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        //res.setCharacterEncoding(StandardCharsets.UTF_8.name());

        super.doFilter(req, res, chain);
    }
}