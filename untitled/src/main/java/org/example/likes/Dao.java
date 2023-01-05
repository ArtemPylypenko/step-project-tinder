package org.example.likes;

import org.example.users.User;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    List<Integer> getLikedId(Integer id) throws SQLException;

    void save(Integer idLiker,Integer idLiked) throws SQLException;
    List<User> getLikedUsers(Integer id) throws SQLException;

}
