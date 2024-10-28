package org.example.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Logger;

public class UDPServer extends Thread {
    private static final Logger logger = Logger.getLogger(UDPServer.class.getName());
    private static final int PORT = 9877;

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            logger.info("UDP Server started on port " + PORT);

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                logger.info("Received UDP packet from " + packet.getAddress() + ":" + packet.getPort());
                // Process video data here
            }
        } catch (Exception e) {
            logger.severe("UDP Server error: " + e.getMessage());
        }
    }
}