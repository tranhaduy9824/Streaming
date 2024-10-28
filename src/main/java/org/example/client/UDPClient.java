package org.example.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 9876;
    private DatagramSocket socket;
    private InetAddress serverAddress;

    public UDPClient() throws Exception {
        socket = new DatagramSocket();
        serverAddress = InetAddress.getByName(SERVER_ADDRESS);
    }

    public void startStreaming() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter text to simulate video data or 'exit' to quit:");

        while (true) {
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) break;

            byte[] buffer = input.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, PORT);
            socket.send(packet);
        }
    }
}
