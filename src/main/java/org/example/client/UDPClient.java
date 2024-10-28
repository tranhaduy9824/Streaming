package org.example.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;

public class UDPClient {
    private static final Logger logger = Logger.getLogger(UDPClient.class.getName());
    private DatagramSocket socket;
    private final int port = 9877; // Changed port number

    public void startStreaming() {
        try {
            socket = new DatagramSocket();
            // Send video data to server
            String message = "Hello from UDP Client!";
            byte[] buffer = message.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
                    InetAddress.getByName("localhost"), port);
            socket.send(packet);
            logger.info("Data sent to server: " + message);
        } catch (Exception e) {
            logger.severe("UDP Client error: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}