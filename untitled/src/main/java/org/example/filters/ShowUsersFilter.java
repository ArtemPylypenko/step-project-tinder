package org.example.filters;

import org.example.likes.LikesController;
import org.example.likes.LikesDao;
import org.example.users.UsersController;
import org.example.users.UsersDao;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public class ShowUsersFilter implements HttpFilter {
    private Connection conn;
    private UsersController usersController;
    private LikesController likesController;

    ShowUsersFilter(Connection conn) {
        this.conn = conn;
        usersController = new UsersController(new UsersDao(conn));
        likesController = new LikesController(new LikesDao(conn));
    }

    @Override
    public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

    }
}
