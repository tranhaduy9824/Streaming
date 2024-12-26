package org.example.client.UI.components.Helper;

import java.sql.*;



public class DatabaseHelper {
    
    public static Connection openConnection()throws Exception {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLy_Laptop";
            String username = "sa";
            String password = "12345";
            Connection  con = DriverManager.getConnection(url,username,password);
           return con;
    }
    
    
}  
