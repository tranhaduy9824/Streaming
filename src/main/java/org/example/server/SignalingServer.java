package org.example.server;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@ServerEndpoint("/signal")
public class SignalingServer extends Thread {
    private static final Logger logger = Logger.getLogger(SignalingServer.class.getName());
    private static Map<String, Set<Session>> rooms = new HashMap<>();

    @Override
    public void run() {
        logger.info("Signaling Server started.");
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            logger.severe("Signaling Server interrupted: " + e.getMessage());
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Client connected: " + session.getId());
        broadcastRoomList();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("Received message: " + message);
        if (message.startsWith("CREATE:")) {
            String roomName = message.substring(7);
            rooms.putIfAbsent(roomName, new HashSet<>());
            rooms.get(roomName).add(session);
            sendMessageToClient(session, "Room created: " + roomName);
            logger.info("Room created: " + roomName);
            broadcastRoomList();
        } else if (message.startsWith("JOIN:")) {
            String roomName = message.substring(5);
            if (rooms.containsKey(roomName)) {
                rooms.get(roomName).add(session);
                sendMessageToClient(session, "Joined room: " + roomName);
                logger.info("Joined room: " + roomName);
            } else {
                sendMessageToClient(session, "Room not found: " + roomName);
                logger.warning("Room not found: " + roomName);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        rooms.values().forEach(sessions -> sessions.remove(session));
        logger.info("Client disconnected: " + session.getId());
        broadcastRoomList();
    }

    private void sendMessageToClient(Session client, String message) {
        client.getAsyncRemote().sendText(message);
    }

    private void broadcastRoomList() {
        String roomList = "ROOMS:" + String.join(",", rooms.keySet());
        for (Set<Session> sessions : rooms.values()) {
            for (Session session : sessions) {
                sendMessageToClient(session, roomList);
            }
        }
    }
}