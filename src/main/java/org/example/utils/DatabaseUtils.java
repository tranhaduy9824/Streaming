package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {
//    private static final String URL = "jdbc:mysql://localhost:3306/streaming";
     private static final String URL = "jdbc:mysql://192.168.1.19:3306/streaming";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
//GRANT ALL PRIVILEGES ON *.* TO 'TestUser'@'%' IDENTIFIED BY '123456';
//FLUSH PRIVILEGES;
//Room created: 4 by example04
//Room created: 4 title: Xin chao mn by example04
//Streaming video for room: 4
//Sent room list to broadcast address: ROOM_LIST:4|example04|1|1|225.32.242.172|5452|Xin chao mn,
//Streaming video for room: 4
