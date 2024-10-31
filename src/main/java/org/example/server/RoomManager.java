package org.example.server;

import java.util.HashMap;
import java.util.Map;

public class RoomManager {
    private Map<String, Room> rooms;

    public RoomManager(UDPBroadcastServer udpBroadcastServer) {
        this.rooms = new HashMap<>();
    }

    public synchronized void createRoom(String roomName, String owner) {
        if (roomName != null && !roomName.trim().isEmpty() && !rooms.containsKey(roomName)) {
            Room room = new Room(roomName, owner);
            room.addParticipant(new Participant(owner));
            rooms.put(roomName, room);
            System.out.println("Room created: " + roomName + " by " + owner);
        } else {
            System.out.println("Failed to create room: " + roomName);
        }
    }

    public synchronized void closeRoom(String roomName) {
        if (rooms.containsKey(roomName)) {
            rooms.remove(roomName);
            System.out.println("Room closed: " + roomName);
        }
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }
}