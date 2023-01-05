package org.example.messages;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    List<T> getAllBetween(Integer idSend, Integer idRec) throws SQLException;
    void save(T t) throws SQLException;

}
