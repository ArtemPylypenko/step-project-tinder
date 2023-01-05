package org.example.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersController {
    private UsersDao dao;

    public UsersController(UsersDao dao) {
        this.dao = dao;
    }

    public User getUser(Integer id) throws SQLException {
        return dao.get(id);
    }

    public List<User> getAllUsers() throws SQLException {
        return dao.getAll();
    }

    public void addUser(User u) throws SQLException {
        dao.save(u);
    }

    public void deleteUser(Integer id) throws SQLException {
        dao.delete(id);
    }
}
