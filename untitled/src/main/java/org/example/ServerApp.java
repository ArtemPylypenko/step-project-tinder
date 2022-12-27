package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.dao.ProfileDao;
import org.example.servlets.TestServlet;

import java.util.ArrayList;
import java.util.List;

public class ServerApp {
    //http://localhost:8080/users
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();

        List<Profile> liked = new ArrayList<>();
        ProfileDao dao = new ProfileDao();
        dao.save(new Profile("Bob", "http://surl.li/eetve",0));
        dao.save(new Profile("Jim", "http://surl.li/eezms",1));
        dao.save(new Profile("Jord", "http://surl.li/eezmv",2));
        dao.save(new Profile("Mary", "http://surl.li/eezob",3));

        handler.addServlet(new ServletHolder(new TestServlet("static-content", dao)), "/users/*");

        server.setHandler(handler);
        server.start();
        server.join();

    }
}
