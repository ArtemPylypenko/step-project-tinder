package org.example.servlets;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.example.messages.MessagesController;
import org.example.messages.MessagesDao;
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
import java.util.HashMap;

public class ShowChatServlet extends HttpServlet {
    private final String osPrefix;
    private Connection conn;
    private MessagesController messagesController;
    private UsersController usersController;
    private Integer idRec;

    public ShowChatServlet(String osPrefix, Connection conn) {
        this.osPrefix = osPrefix;
        this.conn = conn;
        messagesController = new MessagesController(new MessagesDao(conn));
        usersController = new UsersController(new UsersDao(conn));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();


        Configuration conf = new Configuration(Configuration.VERSION_2_3_31);
        conf.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
        conf.setDirectoryForTemplateLoading(new File(osPrefix));

        HashMap<String, Object> data = new HashMap<>();

        try {
            data.put("messages", messagesController.getAllBetween(1, idRec));
            data.put("userTo", usersController.getUser(idRec));
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
        System.out.println("Messages post");
        String pathInfo = "/chat.ftl";
        System.out.println("id= " + req.getParameter("id"));
        idRec = Integer.valueOf(req.getParameter("id"));
        System.out.println("ID_REC= " + idRec);
        resp.sendRedirect("/messages/chat.ftl");

//        System.out.println(req.getPathInfo());
//
//        Configuration conf = new Configuration(Configuration.VERSION_2_3_31);
//        conf.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
//        conf.setDirectoryForTemplateLoading(new File(osPrefix));
//
//        HashMap<String, Object> data = new HashMap<>();
//
//        ArrayList<Profile> dataList = new ArrayList<>();
//
//
//        if (pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);
//        Path file = Path.of(osPrefix, pathInfo);
//
//        try (PrintWriter w = resp.getWriter()) {
//            conf.getTemplate(pathInfo).process(data, w);
//        } catch (TemplateException e) {
//            throw new RuntimeException(e);
//        }
    }
}
