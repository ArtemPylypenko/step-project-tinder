package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GlobalSQLConnection {

    private static Connection conn = null;

    public static Connection get() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "20032612"
            );
        }
        return conn;
    }

}
