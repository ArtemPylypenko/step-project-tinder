package org.example.servlets;

import org.example.GlobalSQLConnection;
import org.example.messages.Message;
import org.example.messages.MessagesController;
import org.example.messages.MessagesDao;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class AddMessageServlet extends HttpServlet {
    private final Connection conn;
    private final MessagesController controller;

    public AddMessageServlet() throws SQLException {
        conn = GlobalSQLConnection.get();
        controller = new MessagesController(new MessagesDao(conn));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie c = Optional.ofNullable(req.getCookies())
                .flatMap(cc -> Arrays.stream(cc).filter(c1 -> c1.getName().equals("id")).findFirst()).get();


        try {
            controller.save(new Message(c.getValue(),req.getParameter("userTo"),req.getParameter("message")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.setHeader("id", req.getParameter("userTo"));
        resp.sendRedirect("/messages/chat.ftl");
    }

}
