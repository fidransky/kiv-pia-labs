package cz.zcu.kiv.pia.labs;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/hello")
public class HelloWorldServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");

        var from = req.getParameter("from");
        var builder = new StringBuilder("Hello World");
        if (from == null) {
            throw new RuntimeException("The 'from' parameter is missing.");
        } else {
            builder.append(" from ").append(from);
        }
        builder.append("!");

        // use res.getWriter() instead of writing directly to res.getOutputStream()
        PrintWriter out = res.getWriter();
        out.println(builder);
    }
}
