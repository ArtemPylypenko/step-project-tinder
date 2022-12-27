package org.example.servlets;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.example.Profile;
import org.example.dao.ProfileDao;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestServlet extends HttpServlet {
    private final String osPrefix;
    private final ProfileDao users;
    private int showedUsers;
    private List<Profile> liked;

    public TestServlet(String osPrefix, ProfileDao users) {
        this.osPrefix = osPrefix;
        this.users = users;
        liked = new ArrayList<>();
        showedUsers = 0;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GET!!");
        String pathInfo = req.getPathInfo();

        if (pathInfo == null) {
            pathInfo = "/like-page.ftl";
        }

        Configuration conf = new Configuration(Configuration.VERSION_2_3_31);
        conf.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
        conf.setDirectoryForTemplateLoading(new File(osPrefix));

        HashMap<String, String> data = new HashMap<>();
        data.put("name", users.getAll().get(showedUsers).getName());
        data.put("imgURL", users.getAll().get(showedUsers).getImgURL());

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
        System.out.println("POST!!");
        String pathInfo = "like-page.ftl";
        Path file = Path.of(osPrefix, pathInfo);

        System.out.println(req.getParameter("isLiked"));
        if(req.getParameter("isLiked").equals("true"))
            liked.add(users.getAll().get(showedUsers));
        showedUsers++;
        if(showedUsers == users.getAll().size()){
            showedUsers = 0;

        }


        System.out.println(pathInfo);
        doGet(req,resp);
    }
}
