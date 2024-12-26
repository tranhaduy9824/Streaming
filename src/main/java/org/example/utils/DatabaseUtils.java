package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {
    //    private static final String URL = "jdbc:mysql://localhost:3306/streaming";
    private static final String URL = "jdbc:mysql://192.168.45.84:3306/streaming";
    private static final String USER = "TestUser2";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}