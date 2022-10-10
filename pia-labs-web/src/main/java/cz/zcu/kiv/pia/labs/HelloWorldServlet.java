package cz.zcu.kiv.pia.labs;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet(urlPatterns = "/hello")
public class HelloWorldServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        //res.setCharacterEncoding(StandardCharsets.UTF_8.name());

        var from = req.getParameter("from");
        var builder = new StringBuilder("Hello World");
        if (from != null) {
            builder.append(" from ").append(from);
        } else {
            throw new RuntimeException("The 'from' parameter is missing.");
        }
        builder.append("!");

        // use res.getWriter() instead of writing directly to res.getOutputStream()
        PrintWriter out = res.getWriter();
        out.println("<h1>" + builder + "</h1>");
    }
}