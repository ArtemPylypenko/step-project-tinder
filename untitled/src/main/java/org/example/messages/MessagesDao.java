package org.example.messages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessagesDao implements Dao<Message> {
    Connection conn;

    public MessagesDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Message> getAllBetween(Integer idSend, Integer idRec) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("select * from messages " +
                "where id_from = ? and id_to = ? or  id_from = ? and id_to = ? " +
                "order by send_time asc");
        statement.setInt(1,idSend);
        statement.setInt(2,idRec);
        statement.setInt(3,idRec);
        statement.setInt(4,idSend);
        ResultSet resultSet = statement.executeQuery();
        List<Message> messageList = new ArrayList<>();
        Message tmp = null;
        while (resultSet.next()){
            Integer idFrom = resultSet.getInt("id_from");
            Integer idTo = resultSet.getInt("id_to");
            String text = resultSet.getString("text");
            Timestamp sendTime = resultSet.getTimestamp("send_time");
            tmp = new Message(idFrom,idTo,text,sendTime);
            messageList.add(tmp);
        }
        return messageList;
    }

    @Override
    public void save(Message message) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("insert into messages (id_from,id_to,text) " +
                "values (?,?,?)");
        statement.setLong(1,message.getIdFrom());
        statement.setLong(2,message.getIdTo());
        statement.setString(3,message.getText());
        statement.execute();
    }
}
