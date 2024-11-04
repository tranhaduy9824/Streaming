package org.example.server.manager;

import java.util.HashMap;
import java.util.Map;

import org.example.server.model.User;

public class UserManager {
    private Map<String, User> users;

    public UserManager() {
        users = new HashMap<>();
    }

    public synchronized boolean registerUser(String username) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username));
        return true;
    }

    public synchronized boolean loginUser(String username) {
        return users.containsKey(username);
    }

    public synchronized int getUserCount() {
        return users.size();
    }

    public synchronized User getUser(String username) {
        return users.get(username);
    }
}