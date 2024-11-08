package org.example.server.manager;

import org.example.dao.UserDAO;
import org.example.server.model.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users;
    private UserDAO userDAO;

    public UserManager() {
        users = new HashMap<>();
        userDAO = new UserDAO();
    }

    public synchronized boolean registerUser(String username, String password) {
        try {
            if (userDAO.getUserByUsername(username) != null) {
                return false;
            }
            User user = new User(username, password);
            userDAO.createUser(user);
            users.put(username, user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized boolean loginUser(String username, String password) {
        try {
            System.out.println("User logged in: " + username + password);
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                users.put(username, user);
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized int getUserCount() {
        return users.size();
    }

    public synchronized User getUser(String username) {
        return users.get(username);
    }

    public synchronized User getUserById(int id) {
        for (User user : users.values()) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}