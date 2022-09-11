package cz.zcu.kiv.pia.labs;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/error")
public class ErrorServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable exception = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        resp.setContentType("text/html");
        resp.setStatus(400);

        PrintWriter out = resp.getWriter();
        out.println("<h1>Error: " + exception.getMessage() + "</h1>");
    }
}