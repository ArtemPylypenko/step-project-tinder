package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.filters.CheckCookieFilter;
import org.example.likes.LikesController;
import org.example.likes.LikesDao;
import org.example.servlets.*;

import javax.servlet.DispatcherType;
import java.sql.Connection;
import java.sql.DriverManager;
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

        LikesController controller = new LikesController(new LikesDao(conn));
        System.out.println(controller.checkPair("1e70ba57-1bf2-40f1-a0cc-0d8c9d333582","1e70ba57-1bf2-40f1-a0cc-0d8c9d333582"));
        handler.addServlet(new ServletHolder(new UsersServlet("static-content")), "/users/*");
        handler.addServlet(new ServletHolder(new LikedServlet("static-content")), "/liked/*");
        handler.addServlet(new ServletHolder(new ShowChatServlet("static-content")), "/messages/*");
        handler.addServlet(new ServletHolder(new AddMessageServlet()), "/add-message/*");
        handler.addServlet(new ServletHolder(new LoginServlet("static-content")), "/login/*");
        handler.addServlet(new ServletHolder(new LogoutServlet()), "/logout/*");

        handler.addFilter(new FilterHolder(new CheckCookieFilter()),"/liked", ft);
        handler.addFilter(new FilterHolder(new CheckCookieFilter()),"/users", ft);
        handler.addFilter(new FilterHolder(new CheckCookieFilter()),"/messages", ft);

        server.setHandler(handler);
        server.start();
        server.join();
        conn.close();
    }
}
