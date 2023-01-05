package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.dao.ProfileDao;
import org.example.servlets.LikedServlet;
import org.example.servlets.ShowChatServlet;
import org.example.servlets.UsersServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {
    //http://localhost:8080/users
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();

        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "20032612"
        );


        List<Profile> liked = new ArrayList<>();
        ProfileDao dao = new ProfileDao();
        dao.save(new Profile("Bob", "http://surl.li/eetve", 0));
        dao.save(new Profile("Jim", "http://surl.li/eezms", 1));
        dao.save(new Profile("Jord", "http://surl.li/eezmv", 2));
        dao.save(new Profile("Mary", "http://surl.li/eezob", 3));


        handler.addServlet(new ServletHolder(new UsersServlet("static-content",conn)), "/users/*");
        handler.addServlet(new ServletHolder(new LikedServlet("static-content",conn)), "/liked/*");
        handler.addServlet(new ServletHolder(new ShowChatServlet("static-content",conn)), "/messages/*");

        server.setHandler(handler);
        server.start();
        server.join();
        conn.close();
    }
}
