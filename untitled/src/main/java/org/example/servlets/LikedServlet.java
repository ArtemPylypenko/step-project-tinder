package org.example.servlets;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.example.Profile;
import org.example.dao.ProfileDao;
import org.example.likes.LikesController;
import org.example.likes.LikesDao;
import org.example.users.UsersController;
import org.example.users.UsersDao;

import javax.servlet.ServletException;
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
import java.util.ArrayList;
import java.util.HashMap;

public class LikedServlet extends HttpServlet {
    private final String osPrefix;
    private int showedUsers;
    private Connection conn;
    LikesController likesController;
    UsersController usersController;

    public LikedServlet(String osPrefix,Connection conn) {
        this.osPrefix = osPrefix;
        usersController = new UsersController(new UsersDao(conn));
        likesController = new LikesController(new LikesDao(conn));
        this.conn = conn;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/people-list.ftl";
        }
        Configuration conf = new Configuration(Configuration.VERSION_2_3_31);
        conf.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
        conf.setDirectoryForTemplateLoading(new File(osPrefix));

        HashMap<String, Object> data = new HashMap<>();

        ArrayList<Profile> dataList = new ArrayList<>();


        try {
            data.put("user", likesController.getLikedUsers(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

    }
}
