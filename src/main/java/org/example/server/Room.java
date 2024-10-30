package org.example.server;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomName;
    private List<Participant> participants;

    public Room(String roomName) {
        this.roomName = roomName;
        this.participants = new ArrayList<>();
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public String getRoomName() {
        return roomName;
    }

    public List<Participant> getParticipants() {
        return participants;
    }
}

