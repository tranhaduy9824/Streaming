package org.example.dao;

import org.example.server.model.User;
import org.example.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DatabaseUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }
        return null;
    }

    public User createUser(User user) throws SQLException {
        if (isUsernameTaken(user.getUsername())) {
            throw new SQLException("Username is already taken.");
        }

        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {  // Thêm Statement.RETURN_GENERATED_KEYS
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
    
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);
                user.setId(userId);
                return user;
            }
        }
        return null;
    }
    
    private boolean isUsernameTaken(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; 
            }
        }
        return false;
    }
}