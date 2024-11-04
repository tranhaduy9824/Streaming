package org.example.controller;

import org.example.dao.UserDAO;
import org.example.server.model.User;

import java.sql.SQLException;

public class UserController {
    private UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void register(String username, String password) throws SQLException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userDAO.createUser(user);
    }
}