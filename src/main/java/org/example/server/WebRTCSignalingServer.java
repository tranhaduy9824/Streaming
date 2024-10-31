package org.example.server;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.example.config.ServerConfig;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WebRTCSignalingServer extends WebSocketServer {
    private Map<WebSocket, String> clients = Collections.synchronizedMap(new HashMap<>());

    public WebRTCSignalingServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New client connected: " + conn.getRemoteSocketAddress());
        clients.put(conn, conn.getRemoteSocketAddress().toString());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Client disconnected: " + conn.getRemoteSocketAddress());
        clients.remove(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        for (WebSocket client : clients.keySet()) {
            if (client != conn) {
                client.send(message);
            }
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("WebRTC Signaling Server is running on port " + getPort());
    }

    public static void main(String[] args) {
        int port = ServerConfig.SIGNALING_PORT;
        WebRTCSignalingServer server = new WebRTCSignalingServer(port);
        server.start();
    }
}