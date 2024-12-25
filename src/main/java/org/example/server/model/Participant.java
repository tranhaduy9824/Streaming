package org.example.server.model;

public class Participant {
    private int id;
    private int roomId;
    private int userId;
    private String titleStream;

    public Participant() {
    }

    public Participant(int roomId, int userId) {
        this.roomId = roomId;
        this.userId = userId;
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
    public String getTitleStream(){
        return titleStream;
    }
    public void setTitleStream(String titleStream){
        this.titleStream = titleStream;
    }
}