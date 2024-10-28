package org.example.server;

public class ServerMain {
    public static void main(String[] args) {
        try {
            SignalingServer signalingServer = new SignalingServer();
            signalingServer.start(); // Gọi phương thức start() ở đây

            UDPServer udpServer = new UDPServer();
            udpServer.start();

            System.out.println("All servers started successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}