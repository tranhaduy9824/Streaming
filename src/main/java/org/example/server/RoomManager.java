package org.example.server;

import java.util.HashMap;
import java.util.Map;

public class RoomManager {
    private Map<String, Room> rooms;

    public RoomManager() {
        rooms = new HashMap<>();
    }

    public synchronized void createRoom(String roomName) {
        if (!rooms.containsKey(roomName)) {
            rooms.put(roomName, new Room(roomName));
            System.out.println("Room created: " + roomName);
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

    public void start() {
        // Logic to manage rooms (create, delete, etc.)
    }
}