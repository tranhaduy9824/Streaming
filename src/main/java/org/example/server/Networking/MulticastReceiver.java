package org.example.server.Networking;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver {
    private static final String MULTICAST_ADDRESS = "230.0.0.1";
    private static final int PORT = 8888; // Choose an appropriate port

    public void start() {
        MulticastSocket socket = null;
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket = new MulticastSocket(PORT); // Bind to the specified port
            socket.joinGroup(group);
            byte[] buffer = new byte[1024];

            System.out.println("Waiting for messages...");

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received message: " + message);
                // Process incoming multicast messages
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.leaveGroup(InetAddress.getByName(MULTICAST_ADDRESS));
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new MulticastReceiver().start();
    }
}
