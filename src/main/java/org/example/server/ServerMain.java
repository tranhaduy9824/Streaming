package org.example.server;

public class ServerMain {
    public static void main(String[] args) {
        try {
//          Run server
            SignalingServer signalingServer = new SignalingServer();
            signalingServer.start();

            UDPServer udpServer = new UDPServer();
            udpServer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

