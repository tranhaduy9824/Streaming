package org.example.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VideoStreamManager {
    private ConcurrentHashMap<String, ExecutorService> roomStreams;

    public VideoStreamManager() {
        roomStreams = new ConcurrentHashMap<>();
    }

    public void startStream(String roomName, Runnable streamTask) {
        if (!roomStreams.containsKey(roomName)) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            roomStreams.put(roomName, executor);
            executor.submit(streamTask);
            System.out.println("Started video stream for room: " + roomName);
        }
    }

    public void stopStream(String roomName) {
        ExecutorService executor = roomStreams.remove(roomName);
        if (executor != null) {
            executor.shutdownNow();
            System.out.println("Stopped video stream for room: " + roomName);
        }
    }
}