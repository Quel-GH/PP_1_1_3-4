package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    Connection connection = null;

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                String url = "jdbc:postgresql://localhost:5432/postgres";
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, "postgres", "postgres");
                return connection;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            return connection;
        }


    }
}
