package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.servlets.LikedServlet;
import org.example.servlets.ShowChatServlet;
import org.example.servlets.UsersServlet;

import java.sql.Connection;
import java.sql.DriverManager;

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


        handler.addServlet(new ServletHolder(new UsersServlet("static-content", conn)), "/users/*");
        handler.addServlet(new ServletHolder(new LikedServlet("static-content", conn)), "/liked/*");
        handler.addServlet(new ServletHolder(new ShowChatServlet("static-content", conn)), "/messages/*");

        server.setHandler(handler);
        server.start();
        server.join();
        conn.close();
    }
}
