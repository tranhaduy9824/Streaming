package org.example.server.model;

import java.sql.Timestamp;

public class Comment {
    private int id;
    private int roomId;
    private int userId;
    private String comment;
    private Timestamp timestamp;

    public Comment() {
    }

    public Comment(int roomId, int userId, String comment) {
        this.roomId = roomId;
        this.userId = userId;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}