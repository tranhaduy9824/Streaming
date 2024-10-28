package org.example.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

public class UDPServer {
    private static final int PORT = 9876;
    private static final int BUFFER_SIZE = 65536;
    private DatagramSocket socket;
    private Set<InetAddress> clientAddresses = new HashSet<>();

    public UDPServer() throws Exception {
        socket = new DatagramSocket(PORT);
    }

    public void start() throws Exception {
        System.out.println("UDP Server started on port " + PORT);
        byte[] buffer = new byte[BUFFER_SIZE];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            clientAddresses.add(packet.getAddress());

            for (InetAddress address : clientAddresses) {
                if (!address.equals(packet.getAddress())) {
                    DatagramPacket sendPacket = new DatagramPacket(packet.getData(), packet.getLength(), address, packet.getPort());
                    socket.send(sendPacket);
                }
            }
        }
    }
}
