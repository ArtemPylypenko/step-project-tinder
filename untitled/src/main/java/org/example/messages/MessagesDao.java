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
    public List<Message> getAllBetween(String idSend, String idRec) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("select * from messages " +
                "where id_from = ? and id_to = ? or  id_from = ? and id_to = ? " +
                "order by send_time asc");
        statement.setString(1,idSend);
        statement.setString(2,idRec);
        statement.setString(3,idRec);
        statement.setString(4,idSend);
        ResultSet resultSet = statement.executeQuery();
        List<Message> messageList = new ArrayList<>();
        Message tmp = null;
        while (resultSet.next()){
            String idFrom = resultSet.getString("id_from");
            String idTo = resultSet.getString("id_to");
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
        statement.setString(1,message.getIdFrom());
        statement.setString(2,message.getIdTo());
        statement.setString(3,message.getText());
        statement.execute();
    }
}
