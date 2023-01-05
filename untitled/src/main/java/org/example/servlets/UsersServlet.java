package org.example.servlets;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.example.Profile;
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
import java.util.List;

public class UsersServlet extends HttpServlet {
    private final String osPrefix;
    private int showedUsers;
    private List<Profile> liked;
    private Connection conn;
    private UsersController usersController;
    private LikesController likesController;

    public UsersServlet(String osPrefix, Connection conn) {
        this.osPrefix = osPrefix;
        this.conn = conn;
        usersController = new UsersController(new UsersDao(conn));
        likesController = new LikesController(new LikesDao(conn));
        liked = new ArrayList<>();
        showedUsers = 0;
    }

    public List<Profile> getLiked() {
        return liked;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null)
            pathInfo = "/like-page.ftl";

        try {
            if (showedUsers == usersController.getAllUsers().size()) {
                resp.sendRedirect("/liked");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Configuration conf = new Configuration(Configuration.VERSION_2_3_31);
        conf.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
        conf.setDirectoryForTemplateLoading(new File(osPrefix));

        HashMap<String, String> data = new HashMap<>();
        try {
            data.put("name", usersController.getAllUsers().get(showedUsers).getName());
            data.put("imgURL", usersController.getAllUsers().get(showedUsers).getImgURL());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        if (pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);

        try (PrintWriter w = resp.getWriter()) {
            conf.getTemplate(pathInfo).process(data, w);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = "like-page.ftl";
        Path file = Path.of(osPrefix, pathInfo);

        System.out.println(req.getParameter("isLiked"));
        try {
            if (req.getParameter("isLiked").equals("true"))
                likesController.save(1, usersController.getAllUsers().get(showedUsers).getId());
            showedUsers++;
            if (showedUsers == usersController.getAllUsers().size() + 1) {
                showedUsers = 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        doGet(req, resp);
    }
}
