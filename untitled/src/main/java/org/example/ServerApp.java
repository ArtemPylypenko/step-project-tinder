package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.filters.CheckCookieFilter;

import org.example.servlets.*;

import javax.servlet.DispatcherType;

import java.util.EnumSet;

public class ServerApp {
    private static final EnumSet<DispatcherType> ft = EnumSet.of(DispatcherType.REQUEST);

    //http://localhost:8080/users
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();





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

        GlobalSQLConnection.close();
    }
}
