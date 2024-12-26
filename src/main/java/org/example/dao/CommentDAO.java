package org.example.dao;

import org.example.server.model.Comment;
import org.example.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    public void addComment(Comment comment) throws SQLException {
        String query = "INSERT INTO comments (room_id, user_id, comment) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, comment.getRoomId());
            statement.setInt(2, comment.getUserId());
            statement.setString(3, comment.getComment());
            statement.executeUpdate();
        }
    }

    public List<Comment> getCommentsByRoomId(int roomId) throws SQLException {
        String query = "SELECT * FROM comments WHERE room_id = ?";
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setRoomId(resultSet.getInt("room_id"));
                comment.setUserId(resultSet.getInt("user_id"));
                comment.setComment(resultSet.getString("comment"));
                comment.setTimestamp(resultSet.getTimestamp("timestamp"));
                comments.add(comment);
            }
        }
        return comments;
    }
}