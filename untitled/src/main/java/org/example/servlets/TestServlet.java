package org.example.servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestServlet extends HttpServlet {
    private final String osPrefix;

    public TestServlet(String osPrefix) {
        this.osPrefix = osPrefix;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        if (pathInfo == null)
            pathInfo = "/like-page.html";

        if (pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);
        Path file = Path.of(osPrefix, pathInfo);

        System.out.println(pathInfo);
        try (ServletOutputStream os = resp.getOutputStream()) {
            Files.copy(file, os);
        }
    }
}
