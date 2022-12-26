package org.example;

import freemarker.template.Template;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.servlets.TestServlet;

public class ServerApp {
    //http://localhost:8080/users
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();

        handler.addServlet(new ServletHolder(new TestServlet("static-content")) , "/users/*");

        server.setHandler(handler);
        server.start();
        server.join();

    }
}
