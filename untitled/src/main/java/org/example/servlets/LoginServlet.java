package org.example.servlets;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.example.GlobalSQLConnection;
import org.example.users.User;
import org.example.users.UsersController;
import org.example.users.UsersDao;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private final String osPrefix;

    private Connection conn;
    private UsersController controller;

    public LoginServlet(String osPrefix) throws SQLException {
        this.osPrefix = osPrefix;
        this.conn = GlobalSQLConnection.get();
        controller = new UsersController(new UsersDao(conn));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/login.html";
        }
        Configuration conf = new Configuration(Configuration.VERSION_2_3_31);
        conf.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
        conf.setDirectoryForTemplateLoading(new File(osPrefix));

        HashMap<String, Object> data = new HashMap<>();

        if (pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);
        Path file = Path.of(osPrefix, pathInfo);

        try (PrintWriter w = resp.getWriter()) {
            conf.getTemplate(pathInfo).process(data, w);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            if(controller.checkUserLogin(req.getParameter("login"))){
                String id = UUID.randomUUID().toString();
                resp.addCookie(new Cookie("id", id));

                controller.addUser(new User(id, req.getParameter("login"),
                        req.getParameter("password"),"Some name","someImg"));

                resp.sendRedirect("/users");
            }else {
                resp.sendRedirect("/login");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
