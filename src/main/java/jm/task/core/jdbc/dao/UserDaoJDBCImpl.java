package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable =
                "CREATE TABLE Users(id SERIAL, name VARCHAR(255), lastname VARCHAR(255), age INT);";
        try {
            Connection connection = util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(createTable);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Таблица уже существует");
        }
    }

    public void dropUsersTable() {
        String dropUsers = "DROP TABLE Users;";
        try {
            Connection connection = util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(dropUsers);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Таблица не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String save =
                "INSERT INTO Users(name, lastname, age) VALUES (?,?,?);";

        try {
            Connection connection = util.getConnection();
            PreparedStatement statement = connection.prepareStatement(save);
            statement.setString(1 , name);
            statement.setString(2,lastName);
            statement.setInt(3,(int)age);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void removeUserById(long id) {
        String delete = "DELETE FROM Users WHERE id = " + id + ";";
        try {
            Connection connection = util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(delete);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String selectAll = "SELECT * FROM Users;";
        List<User> selectAllList = new ArrayList<>();
        try {
            Connection connection = util.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAll);

            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge((byte) resultSet.getInt("age"));
                selectAllList.add(user);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return selectAllList;
    }

    public void cleanUsersTable() {
        String truncateTable = "TRUNCATE Users;";
        try {
            Connection connection = util.getConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery(truncateTable);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Таблица пуста");
        }
    }
}
