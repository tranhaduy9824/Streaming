package org.example.server;

import org.example.config.ServerConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LivestreamServer {
    public static void main(String[] args) {
        try {
            UserManager userManager = new UserManager();
            RoomManager roomManager = new RoomManager();

            // Start the UDP Broadcast server
            new UDPBroadcastServer(userManager, roomManager).start();
            // Start the room manager to handle rooms
            roomManager.start();

            // Log server address and port
            String serverAddress = InetAddress.getLocalHost().getHostAddress();
            System.out.println("Server is running at " + serverAddress + ":" + ServerConfig.BROADCAST_PORT);
        } catch (UnknownHostException e) {
            System.err.println("Failed to get local host address.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}