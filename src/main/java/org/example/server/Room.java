package org.example.server;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomName;
    private String owner;
    private List<Participant> participants;

    public Room(String roomName, String owner) {
        this.roomName = roomName;
        this.owner = owner;
        this.participants = new ArrayList<>();
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public void removeParticipant(String username) {
        participants.removeIf(participant -> participant.getUsername().equals(username));
    }

    public boolean hasParticipant(String username) {
        for (Participant participant : participants) {
            if (participant.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getOwner() {
        return owner;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public int getParticipantCount() {
        return participants.size();
    }
}