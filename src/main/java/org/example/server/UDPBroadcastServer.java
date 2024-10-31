package org.example.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import org.example.config.ServerConfig;

public class UDPBroadcastServer extends Thread {
    private UserManager userManager;
    private RoomManager roomManager;

    public UDPBroadcastServer(UserManager userManager) {
        this.userManager = userManager;
        this.roomManager = new RoomManager(this); // Pass the server instance to RoomManager
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
                System.out.println("Received broadcast message: " + message);
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
        System.out.println("Received broadcast message: " + message + " from " + address);
        String[] parts = message.split(":");
        String command = parts[0];
        String username = parts.length > 1 ? parts[1] : null;
        String roomName = parts.length > 2 ? parts[2] : null;

        System.out.println("Received command: " + command);
        System.out.println("Username: " + username);
        System.out.println("Room Name: " + roomName);

        // Ignore ROOM_LIST messages received by the server
        if ("ROOM_LIST".equals(command)) {
            return;
        }

        switch (command) {
            case "REGISTER":
                if (userManager.registerUser(username)) {
                    userManager.getUser(username).setAddress(address);
                    System.out.println("User registered: " + username + " at " + address);
                    sendRoomList(address);
                } else {
                    System.out.println("Registration failed for user: " + username);
                }
                break;
            case "LOGIN":
                if (userManager.loginUser(username)) {
                    System.out.println("User logged in: " + username);
                    sendRoomList(address);
                } else {
                    System.out.println("Login failed for user: " + username);
                }
                break;
            case "CREATE_ROOM":
                if (roomName == null || roomName.trim().isEmpty()) {
                    System.out.println("Invalid room name: " + roomName);
                } else {
                    roomManager.createRoom(roomName, username);
                    sendRoomList(address);
                }
                break;
            case "CLOSE_ROOM":
                if (roomManager.getRooms().containsKey(roomName)) {
                    Room room = roomManager.getRooms().get(roomName);
                    if (room.getOwner().equals(username)) {
                        roomManager.closeRoom(roomName);
                        sendRoomList(address);
                    } else {
                        System.out.println("Only the owner can close the room: " + roomName);
                    }
                } else {
                    System.out.println("Room not found: " + roomName);
                }
                break;
            case "LEAVE_ROOM":
                if (roomName != null && roomManager.getRooms().containsKey(roomName)) {
                    Room room = roomManager.getRooms().get(roomName);
                    room.removeParticipant(username);
                    System.out.println("User " + username + " left room: " + roomName);
                    if (room.getParticipantCount() == 0) {
                        roomManager.closeRoom(roomName);
                    }
                    sendRoomList(address);
                } else {
                    System.out.println("Room not found: " + roomName);
                }
                break;
            case "LIST_ROOMS":
                sendRoomList(address);
                break;
            case "JOIN_ROOM":
                if (roomName != null && roomManager.getRooms().containsKey(roomName)) {
                    Room room = roomManager.getRooms().get(roomName);
                    if (!room.hasParticipant(username)) {
                        room.addParticipant(new Participant(username));
                        System.out.println("User " + username + " joined room: " + roomName);
                    } else {
                        System.out.println("User " + username + " is already in room: " + roomName);
                    }
                    sendRoomList(address);
                } else {
                    System.out.println("Room not found: " + roomName);
                }
                break;
            case "COMMENT":
                String comment = parts.length > 2 ? parts[2] : null;
                if (comment != null) {
                    broadcastComment(username, comment);
                }
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }

    private void sendRoomList(String address) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress group = InetAddress.getByName(ServerConfig.BROADCAST_ADDRESS);
            StringBuilder roomList = new StringBuilder("ROOM_LIST:");
            for (Room room : roomManager.getRooms().values()) {
                roomList.append(room.getRoomName()).append("|")
                        .append(room.getOwner()).append("|")
                        .append(room.getParticipantCount()).append(",");
            }
            byte[] buffer = roomList.toString().getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, ServerConfig.BROADCAST_PORT);
            socket.send(packet);
            System.out.println("Sent room list to " + address + ": " + roomList.toString()); // Log the room list
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void broadcastComment(String username, String comment) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress group = InetAddress.getByName(ServerConfig.BROADCAST_ADDRESS);
            String message = "COMMENT:" + username + ":" + comment;
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, ServerConfig.BROADCAST_PORT);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }
}