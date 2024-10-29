package org.example.server;

import java.util.ArrayList;
import java.util.List;
import java.net.Socket;

public class Room {
    private String name;
    private List<Socket> participants;

    public Room(String name) {
        this.name = name;
        this.participants = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addParticipant(Socket socket) {
        participants.add(socket);
    }

    public List<Socket> getParticipants() {
        return participants;
    }
}
