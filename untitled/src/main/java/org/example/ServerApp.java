package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.servlets.LikedServlet;
import org.example.servlets.LoginServlet;
import org.example.servlets.ShowChatServlet;
import org.example.servlets.UsersServlet;

import javax.servlet.DispatcherType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.EnumSet;

public class ServerApp {
    private static final EnumSet<DispatcherType> ft = EnumSet.of(DispatcherType.REQUEST);

    //http://localhost:8080/users
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();

        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "20032612"
        );


        handler.addServlet(new ServletHolder(new UsersServlet("static-content")), "/users/*");
        handler.addServlet(new ServletHolder(new LikedServlet("static-content")), "/liked/*");
        handler.addServlet(new ServletHolder(new ShowChatServlet("static-content")), "/messages/*");
        handler.addServlet(new ServletHolder(new LoginServlet("static-content")), "/login/*");

        handler.addFilter(new FilterHolder(new CheckCookieFilter()),"/liked", ft);
        handler.addFilter(new FilterHolder(new CheckCookieFilter()),"/users", ft);
        handler.addFilter(new FilterHolder(new CheckCookieFilter()),"/messages", ft);

        server.setHandler(handler);
        server.start();
        server.join();
        conn.close();
    }
}
