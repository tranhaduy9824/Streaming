package org.example.client;

import jakarta.websocket.*;
import java.net.URI;
import java.util.logging.Logger;

@ClientEndpoint
public class WebSocketClient {
    private static final Logger logger = Logger.getLogger(WebSocketClient.class.getName());
    private Session session;
    private String uri;
    private boolean connected = false;

    public WebSocketClient(String uri) {
        this.uri = uri;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        this.connected = true;
        logger.info("Connected to signaling server");
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("Received signaling message: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        this.connected = false;
        logger.info("Disconnected from signaling server: " + closeReason);
    }

    public void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, URI.create(uri));
            logger.info("Attempting to connect to WebSocket server at " + uri);
        } catch (Exception e) {
            logger.severe("Error connecting to WebSocket server: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        if (connected) {
            session.getAsyncRemote().sendText(message);
        } else {
            logger.severe("Cannot send message, not connected.");
        }
    }

    public boolean isConnected() {
        return connected;
    }
}