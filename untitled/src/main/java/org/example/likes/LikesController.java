package org.example.likes;

import org.example.users.User;

import java.sql.SQLException;
import java.util.List;

public class LikesController {
    private final LikesDao dao;

    public LikesController(LikesDao dao) {
        this.dao = dao;
    }

    public List<Integer> getAllLiked(Integer id) throws SQLException {
        return dao.getLikedId(id);
    }

    public void save(Integer idLiker, Integer idLiked) throws SQLException {
        dao.save(idLiker, idLiked);
    }

    public List<User> getLikedUsers(Integer id) throws SQLException {
        return dao.getLikedUsers(id);
    }

}
