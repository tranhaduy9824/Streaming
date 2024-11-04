package org.example.server.manager;

import java.util.HashMap;
import java.util.Map;

import org.example.server.model.Participant;
import org.example.server.model.Room;
import org.example.server.video.VideoStreamManager;
import org.example.server.video.VideoStreamTask;

public class RoomManager {
    private Map<String, Room> rooms;
    private VideoStreamManager videoStreamManager;

    public RoomManager() {
        this.rooms = new HashMap<>();
        this.videoStreamManager = new VideoStreamManager();
    }

    public synchronized void createRoom(String roomName, String owner) {
        if (roomName != null && !roomName.trim().isEmpty() && !rooms.containsKey(roomName)) {
            Room room = new Room(roomName, owner);
            room.addParticipant(new Participant(owner));
            rooms.put(roomName, room);
            videoStreamManager.startStream(roomName, new VideoStreamTask(roomName));
            System.out.println("Room created: " + roomName + " by " + owner);
        } else {
            System.out.println("Failed to create room: " + roomName);
        }
    }

    public synchronized void closeRoom(String roomName) {
        if (rooms.containsKey(roomName)) {
            rooms.remove(roomName);
            videoStreamManager.stopStream(roomName);
            System.out.println("Room closed: " + roomName);
        }
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }
}