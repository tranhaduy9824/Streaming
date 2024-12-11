package org.example.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.example.config.ServerConfig;
import org.example.server.Networking.UDPBroadcastServer;
import org.example.server.Networking.WebRTCSignalingServer;
import org.example.server.manager.UserManager;

public class LivestreamServerMain {
    public static void main(String[] args) {
        try {
            UserManager userManager = new UserManager();
            UDPBroadcastServer udpBroadcastServer = new UDPBroadcastServer(userManager);
            udpBroadcastServer.start();

            int signalingPort = ServerConfig.SIGNALING_PORT;
            WebRTCSignalingServer signalingServer = new WebRTCSignalingServer(signalingPort);
            signalingServer.start();

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