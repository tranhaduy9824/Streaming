package org.example.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Map;

import org.example.config.ServerConfig;

public class UDPBroadcastServer extends Thread {
    private UserManager userManager;
    private RoomManager roomManager;

    public UDPBroadcastServer(UserManager userManager, RoomManager roomManager) {
        this.userManager = userManager;
        this.roomManager = roomManager;
    }

    @Override
    public void run() {
        MulticastSocket socket = null;
        try {
            InetAddress group = InetAddress.getByName(ServerConfig.BROADCAST_ADDRESS);
            socket = new MulticastSocket(ServerConfig.BROADCAST_PORT);
            socket.joinGroup(group);
            byte[] buffer = new byte[1024];
            System.out.println("UDP server is running on port " + ServerConfig.BROADCAST_PORT + "...");

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                handleBroadcastMessage(message, packet.getAddress().getHostAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.leaveGroup(InetAddress.getByName(ServerConfig.BROADCAST_ADDRESS));
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleBroadcastMessage(String message, String address) {
        String[] parts = message.split(":");
        String command = parts[0];
        String username = parts.length > 1 ? parts[1] : null;
        String roomName = parts.length > 2 ? parts[2] : null;

        switch (command) {
            case "REGISTER":
                if (userManager.registerUser(username)) {
                    userManager.getUser(username).setAddress(address);
                    System.out.println("User registered: " + username + " at " + address);
                } else {
                    System.out.println("Registration failed for user: " + username);
                }
                break;
            case "LOGIN":
                if (userManager.loginUser(username)) {
                    userManager.getUser(username).setAddress(address);
                    System.out.println("User logged in: " + username + " at " + address);
                } else {
                    System.out.println("Login failed for user: " + username);
                }
                break;
            case "CREATE_ROOM":
                roomManager.createRoom(roomName);
                break;
            case "CLOSE_ROOM":
                roomManager.closeRoom(roomName);
                break;
            case "LIST_ROOMS":
                sendRoomList(address);
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }

    private void sendRoomList(String address) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress clientAddress = InetAddress.getByName(address);
            StringBuilder roomList = new StringBuilder("ROOM_LIST:");
            for (String roomName : roomManager.getRooms().keySet()) {
                roomList.append(roomName).append(",");
            }
            byte[] buffer = roomList.toString().getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientAddress, ServerConfig.BROADCAST_PORT);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}