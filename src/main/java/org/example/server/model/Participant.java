package org.example.server.model;

public class Participant {
    private int id;
    private int roomId;
    private int userId;
    private String address;

    public Participant() {
    }

    public Participant(int roomId, int userId, String address) {
        this.roomId = roomId;
        this.userId = userId;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}