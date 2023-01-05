package org.example.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDao implements Dao<User> {
    Connection conn;

    public UsersDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User get(Integer id) throws SQLException {
        Optional<User> userOptional = Optional.empty();

        PreparedStatement statement = conn.prepareStatement("select * from users where id = ?");
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();

        String name = "";
        String login = "";
        String password = "";
        String imgURL = "";
        while (res.next()) {
            name = res.getString("name");
            login = res.getString("login");
            password = res.getString("password");
            imgURL = res.getString("img_url");
        }
        User newUser = new User(id, name, login, password, imgURL);
        System.out.println(newUser.toString());
        return new User(id, name, login, password, imgURL);

    }

    public List<User> getAll() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("select * from users");
        ResultSet res = statement.executeQuery();
        List<User> userList = new ArrayList<>();
        User tmp = null;
        while (res.next()) {
            Integer id = res.getInt("id");
            String name = res.getString("name");
            String login = res.getString("login");
            String password = res.getString("password");
            String imgURL = res.getString("img_url");
            tmp = new User(id, name, login, password, imgURL);
            userList.add(tmp);
        }
        return userList;
    }

    public void save(User t) throws SQLException {
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "insert into users (login,password,name,img_url) values (?,?,?,?)");
            statement.setString(1, t.getLogin());
            statement.setString(2, t.getPassword());
            statement.setString(3, t.getName());
            statement.setString(4, t.getImgURL());

            statement.execute();
        } catch (SQLException e) {
            System.out.println("can t add this users!");
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "delete from users where id = ?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
