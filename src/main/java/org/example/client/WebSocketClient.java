package org.example.client;

import jakarta.websocket.*;
import java.net.URI;

@ClientEndpoint
public class WebSocketClient {
    private Session session;
    private String uri;

    public WebSocketClient(String uri) {
        this.uri = uri;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to signaling server");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received signaling message: " + message);
    }

    public void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, URI.create(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        session.getAsyncRemote().sendText(message);
    }
}
