package org.example.server;


import org.example.config.ServerConfig;
import org.example.server.Networking.UDPBroadcastServer;
import org.example.server.Networking.WebRTCSignalingServer;
import org.example.server.manager.UserManager;


import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class LivestreamServerMain {
    public static void main(String[] args) {
        try {
            UserManager userManager = new UserManager();
            ViewLivestreamServerMain view = new ViewLivestreamServerMain();
            view.setVisible(true);


            UDPBroadcastServer udpBroadcastServer = new UDPBroadcastServer(userManager, view);
            udpBroadcastServer.start();


            int signalingPort = ServerConfig.SIGNALING_PORT;
            WebRTCSignalingServer signalingServer = new WebRTCSignalingServer(signalingPort);
            signalingServer.start();


            String serverAddress = InetAddress.getLocalHost().getHostAddress();
            System.out.println("Server is running at " + serverAddress + ":" + ServerConfig.BROADCAST_PORT);


            SwingUtilities.invokeLater(() -> {
                view.updateLog("UDP server is running on port " + ServerConfig.BROADCAST_PORT + "...");
                view.updateLog("Server is running at " + serverAddress + ":" + ServerConfig.BROADCAST_PORT);
                view.updateLog("WebRTC Signaling Server is running on port " + signalingPort);
            });
        } catch (UnknownHostException e) {
            System.err.println("Failed to get local host address.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

