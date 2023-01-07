package org.example.likes;

import org.example.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LikesDao implements Dao<Like> {
    private Connection connection;

    public LikesDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<String> getLikedId(String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from likes where " +
                "id_liker = ?");
        preparedStatement.setString(1, id);
        List<String> likedList = new ArrayList<>();
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            likedList.add(result.getString("id_liked"));
        }
        return likedList;
    }

    @Override
    public void save(String idLiker, String idLiked) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into likes (id_liker,id_liked) values (?,?)");
        preparedStatement.setString(1, idLiker);
        preparedStatement.setString(2, idLiked);
        preparedStatement.execute();
    }

    @Override
    public List<User> getLikedUsers(String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users\n" +
                "where id in (select id_liked from likes where id_liker = ?)");
        preparedStatement.setString(1, id);
        ResultSet res = preparedStatement.executeQuery();
        List<User> userList = new ArrayList<>();
        User tmp;
        while (res.next()) {
            String userId = res.getString("id");
            String name = res.getString("name");
            String login = res.getString("login");
            String password = res.getString("password");
            String imgURL = res.getString("img_url");
            tmp = new User(userId, name, login, password, imgURL);
            userList.add(tmp);
        }


        return userList;
    }

    public boolean checkPair(String idLiker, String idLiked) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from likes where " +
                "(id_liker = ? and id_liked = ?) ");
        preparedStatement.setString(1, idLiker);
        preparedStatement.setString(2, idLiked);
//        preparedStatement.setString(3, idLiked);
//        preparedStatement.setString(4, idLiker);
        ResultSet result = preparedStatement.executeQuery();
        return result.next();
    }
}
