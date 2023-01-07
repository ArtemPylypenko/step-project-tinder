package org.example.likes;

import org.example.users.User;

import java.sql.SQLException;
import java.util.List;

public class LikesController {
    private final LikesDao dao;

    public LikesController(LikesDao dao) {
        this.dao = dao;
    }

    public List<String> getAllLiked(String id) throws SQLException {
        return dao.getLikedId(id);
    }

    public void save(String idLiker, String idLiked) throws SQLException {
        dao.save(idLiker, idLiked);
    }

    public List<User> getLikedUsers(String id) throws SQLException {
        return dao.getLikedUsers(id);
    }

    public boolean checkPair(String idLiker, String idLiked) throws SQLException {
        return dao.checkPair(idLiker, idLiked);
    }

}
