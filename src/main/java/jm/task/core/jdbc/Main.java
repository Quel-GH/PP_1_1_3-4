package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl dao = new UserServiceImpl();
        dao.createUsersTable();
        dao.saveUser("Данил","Авдеев", (byte) 29);
        dao.saveUser("Александр","Попов", (byte) 44);
        dao.saveUser("Миша","Сорока", (byte) 11);
        dao.saveUser("Руслан","Черняков", (byte) 44);
        System.out.println(dao.getAllUsers());
        dao.cleanUsersTable();
        dao.dropUsersTable();
    }
}
