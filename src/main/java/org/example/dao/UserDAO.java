package org.example.dao;

import org.example.server.model.User;
import org.example.utils.DatabaseUtils;

import java.sql.*;
import java.util.Base64;
import javax.sql.rowset.serial.SerialBlob;

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
    public User FindUserByUserName (String username ) throws Exception{
        String sql = "select * from users where username = ?";
        try(
                Connection con = DatabaseUtils.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);

        ){
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery();){
                if(rs.next()){
                    User us =  createUsers(rs);
                    return us;

                }
            }

            return null;
        }
    }
        
    public User FindIdByUsername(String username) throws SQLException {
        String sql = "SELECT id FROM users WHERE username = ?";

        try (
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    return user;
                }
            }
        }

        return null;
    }
    
    
    private User createUsers (final ResultSet rs) throws SQLException {
        User us = new User();

        us.setUsername(rs.getString("username"));
        us.setFullname(rs.getString("fullname"));
        us.setPhonenumber(rs.getInt("phonenumber"));
        us.setEmail(rs.getString("email"));
        us.setCic(rs.getInt("cic"));
        us.setBirthday(rs.getString("birthday"));
        us.setSex(rs.getBoolean("sex"));

        us.setPassword(rs.getString("password"));
        us.setAddress(rs.getString("address"));
        
        Blob blob = rs.getBlob("image");
        if(blob != null)
            us.setImage(blob.getBytes(1, (int) blob.length()));

        return us;

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
    
     public boolean changePassword(User user) throws SQLException{
            String sql = "update users set password=?"
                       +" where username = ?";
           try (Connection connection = DatabaseUtils.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {                
                  pstmt.setString(2,user.getUsername());
                  pstmt.setString(1,user.getPassword());
                  return pstmt.executeUpdate() >0;
                 }
        }
     
    public boolean deleteUserByUsername(String username)  throws Exception {
          
         String sql = "DELETE FROM users WHERE username = ?";
         try(Connection connection = DatabaseUtils.getConnection();
          PreparedStatement pstmt = connection.prepareStatement(sql);
              ){
               pstmt.setString(1, username);
               return pstmt.executeUpdate() >0;   
         }
                 
     }
    
     public boolean updateUserByUsername(User user) throws Exception {

        String sql = "UPDATE users SET fullname = ?, phonenumber = ?, email = ?, cic = ?, birthday = ?, sex = ?, address = ?, image = ? WHERE username = ?";
        try (Connection connection = DatabaseUtils.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getFullname());
            pstmt.setInt(2, user.getPhonenumber());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getCic());
            pstmt.setString(5, user.getBirthday());
            pstmt.setBoolean(6, user.getSex());
            pstmt.setString(7, user.getAddress());


            if (user.getImage()!= null) {
                byte[] decodedImage = Base64.getDecoder().decode(user.getImage());
                Blob hinh = new SerialBlob(decodedImage);
                pstmt.setBlob(8, hinh);
            } else {
                pstmt.setNull(8, Types.BLOB);
            }

            return pstmt.executeUpdate() > 0;
        }
    }
     
         
     public void updateToOffline(String username) {
        try {Connection connection = DatabaseUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET IsOnline = 0 WHERE username = ?");
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     
         
     public void updateToOnline(String username) {
        try {Connection connection = DatabaseUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET IsOnline = 1 WHERE username = ?");
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     
         
     public void updateBannedStatus(User user, boolean ban) {
        try {Connection connection = DatabaseUtils.getConnection();
            if (ban) {//INSERT INTO khachhang (username, password, HoTen, Anh) VALUES (?, ?, ?, ?)
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO banned_users (username) VALUES (?)");
                preparedStatement1.setString(1, user.getUsername());
                preparedStatement1.executeUpdate();
            } else {
                PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM banned_users WHERE username = ?");
                preparedStatement2.setString(1, user.getUsername());
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public User verifyUser(User user) {
        String sql = "SELECT *\n"
                + "FROM users\n"
                + "WHERE username = ?\n"
                + "AND password = ?";
        try {
            Connection con = DatabaseUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                byte[] anh = rs.getBytes("image");
                return new User(
                        rs.getString("username"),
                        rs.getString("fullname"),
                        rs.getInt("phonenumber"),
                        rs.getString("email"),
                        rs.getInt("cic"),
                        rs.getString("birthday"),
                        rs.getBoolean("sex"),
                        rs.getString("password"),
                        rs.getString("address"),
                        anh
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User getUserByUsername2(String username) {
        try { Connection con = DatabaseUtils.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                byte[] anh = rs.getBytes("image");
                return new User(
                        rs.getString("username"),
                        rs.getString("fullname"),
                        rs.getInt("phonenumber"),
                        rs.getString("email"),
                        rs.getInt("cic"),
                        rs.getString("birthday"),
                        rs.getBoolean("sex"),
                        rs.getString("password"),
                        rs.getString("address"),
                        anh
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


        
}