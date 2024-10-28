package org.example.client;

import java.util.Scanner;
import java.util.logging.Logger;

public class ClientMain {
    private static final Logger logger = Logger.getLogger(ClientMain.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt for action
        System.out.print("Enter action (create/join): ");
        String action = scanner.nextLine().trim().toLowerCase();

        // Prompt for room name
        System.out.print("Enter room name: ");
        String roomName = scanner.nextLine().trim();

        // Create and connect WebSocketClient
        WebSocketClient signalingClient = new WebSocketClient("ws://localhost:8080/signal");
        signalingClient.connect();

        // Check WebSocket connection
        if (signalingClient.isConnected()) {
            logger.info("Successfully connected to signaling server.");

            // Send CREATE or JOIN message to server
            if ("create".equals(action)) {
                signalingClient.sendMessage("CREATE:" + roomName);
                logger.info("Sent CREATE message for room: " + roomName);
            } else if ("join".equals(action)) {
                signalingClient.sendMessage("JOIN:" + roomName);
                logger.info("Sent JOIN message for room: " + roomName);
            }

            // Start UDP streaming after sending JOIN message
            UDPClient udpClient = new UDPClient();
            udpClient.startStreaming();
        } else {
            logger.severe("Failed to connect to signaling server.");
        }

        // Close Scanner
        scanner.close();
    }
}