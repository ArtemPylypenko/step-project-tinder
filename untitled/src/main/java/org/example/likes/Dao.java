package org.example.likes;

import org.example.users.User;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    List<String> getLikedId(String id) throws SQLException;

    void save(String idLiker,String idLiked) throws SQLException;
    List<User> getLikedUsers(String id) throws SQLException;

}
