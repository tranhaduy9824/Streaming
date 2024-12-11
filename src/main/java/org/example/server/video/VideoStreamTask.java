package org.example.server.video;

public class VideoStreamTask implements Runnable {
    private String roomName;

    public VideoStreamTask(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Streaming video for room: " + roomName);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}