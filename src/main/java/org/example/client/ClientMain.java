package org.example.client;

public class ClientMain {
    public static void main(String[] args) {
        try {
            WebSocketClient signalingClient = new WebSocketClient("ws://localhost:8080/signal");
            signalingClient.connect();

            UDPClient udpClient = new UDPClient();
            udpClient.startStreaming();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

