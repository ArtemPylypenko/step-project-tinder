package org.example.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    T get(Integer id) throws SQLException;
    List<T> getAll() throws SQLException;
    void save(T t) throws SQLException;
    void delete(Integer id) throws SQLException;
}
