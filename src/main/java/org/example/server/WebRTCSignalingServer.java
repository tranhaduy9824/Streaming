package org.example.server;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.example.config.ServerConfig;

public class WebRTCSignalingServer extends Thread {
    private Socket socket;

    @Override
    public void run() {
        try {
            socket = IO.socket("http://" + ServerConfig.BROADCAST_ADDRESS + ":" + ServerConfig.WEBSOCKET_PORT);
            socket.connect();
            // Handle signaling messages for WebRTC
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}