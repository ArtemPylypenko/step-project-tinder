package org.example.messages;

import java.sql.SQLException;
import java.util.List;

public class MessagesController {
    private MessagesDao dao;

    public MessagesController(MessagesDao dao) {
        this.dao = dao;
    }

    public List<Message> getAllBetween(Integer idSend, Integer idRec) throws SQLException {
        return dao.getAllBetween(idSend,idRec);
    }
    public void save(Message message) throws SQLException {
        dao.save(message);
    }


}
