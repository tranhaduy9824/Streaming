package org.example.server.Networking;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.sql.SQLException;

import org.example.config.ClientConfig;
import org.example.config.ServerConfig;
import org.example.dao.CommentDAO;
import org.example.server.manager.RoomManager;
import org.example.server.manager.UserManager;
import org.example.server.model.Comment;
import org.example.server.model.Participant;
import org.example.server.model.Room;

public class UDPBroadcastServer extends Thread {
    private UserManager userManager;
    private RoomManager roomManager;

    public UDPBroadcastServer(UserManager userManager) {
        this.userManager = userManager;
        this.roomManager = new RoomManager();
    }

    @Override
    public void run() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(ServerConfig.BROADCAST_PORT);
            socket.setBroadcast(true);
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
                socket.close(); // Đảm bảo đóng socket khi kết thúc
            }
        }
    }

    private void handleBroadcastMessage(String message, String address) {
        System.out.println("Received broadcast message: " + message + " from " + address);
        String[] parts = message.split(":");
        String command = parts[0];
        String username = parts.length > 1 ? parts[1] : null;
        String password = parts.length > 2 ? parts[2] : null;
        String userId = parts.length > 2 ? parts[2] : null;
        String roomName = parts.length > 3 ? parts[3] : null;
        String comment = parts.length > 4 ? parts[4] : null;

        if ("ROOM_LIST".equals(command)) {
            return;
        }

        switch (command) {
            case "REGISTER":
                if (userManager.registerUser(username, password)) {
                    userManager.getUser(username).setAddress(address);
                    System.out.println("User registered: " + username + " at " + address);
                } else {
                    System.out.println("Registration failed for user: " + username);
                }
                sendRoomList();
                break;
            case "LOGIN":
                password = parts.length > 2 ? parts[2] : null;
                if (userManager.loginUser(username, password)) {
                    System.out.println("User logged in: " + username);
                } else {
                    System.out.println("Login failed for user: " + username);
                }
                sendRoomList();
                break;
            case "CREATE_ROOM":
                if (roomName == null || roomName.trim().isEmpty()) {
                    System.out.println("Invalid room name: " + roomName);
                } else {
                    String multicastAddress = parts.length > 4 ? parts[4] : "224.0.0.1";
                    int multicastPort = parts.length > 5 ? Integer.parseInt(parts[5]) : 5000;
            
                    boolean isDuplicate = roomManager.getRooms().values().stream()
                        .anyMatch(room -> room.getMulticastAddress().equals(multicastAddress) && room.getMulticastPort() == multicastPort);
            
                    if (isDuplicate) {
                        System.out.println("Multicast address and port combination already in use: " + multicastAddress + ":" + multicastPort);
                    } else {
                        roomManager.createRoom(roomName, username, Integer.parseInt(userId), multicastAddress, multicastPort);
                        System.out.println("Room created: " + roomName + " by " + username);
                    }
                }
                sendRoomList();
                break;
            case "CLOSE_ROOM":
                if (roomManager.getRooms().containsKey(roomName)) {
                    multicastRoomClosure(roomName);
                    roomManager.closeRoom(roomName);
                    System.out.println("Room closed: " + roomName);
                } else {
                    System.out.println("Room not found: " + roomName);
                }
                sendRoomList();
                break;
            case "LEAVE_ROOM":
                if (roomName != null && roomManager.getRooms().containsKey(roomName)) {
                    Room room = roomManager.getRooms().get(roomName);
                    room.removeParticipant(Integer.parseInt(userId));
                    System.out.println("User " + username + " left room: " + roomName);
                    if (room.getParticipants().isEmpty()) {
                        roomManager.closeRoom(roomName);
                    }
                } else {
                    System.out.println("Room not found: " + roomName);
                }
                sendRoomList();
                break;
            case "LIST_ROOMS":
                sendRoomList();
                break;
            case "JOIN_ROOM":
                if (roomName != null && roomManager.getRooms().containsKey(roomName)) {
                    Room room = roomManager.getRooms().get(roomName);
                    if (!room.hasParticipant(Integer.parseInt(userId))) {
                        room.addParticipant(new Participant(room.getId(), Integer.parseInt(userId)));
                        System.out.println("User " + username + " joined room: " + roomName);

                        try {
                            InetAddress group = InetAddress.getByName(room.getMulticastAddress());
                            MulticastSocket multicastSocket = new MulticastSocket(room.getMulticastPort());
                            multicastSocket.joinGroup(group);
                            System.out.println("User " + username + " joined multicast group: " + room.getMulticastAddress() + ":" + room.getMulticastPort());
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Failed to join multicast group for room: " + roomName);
                        }
                    } else {
                        System.out.println("User " + username + " is already in room: " + roomName);
                    }
                } else {
                    System.out.println("Room not found: " + roomName);
                }
                sendRoomList();
                break;
            case "COMMENT":
                if (comment != null && roomName != null && userId != null) {
                    try {
                        Room room = roomManager.getRoomByName(roomName);
                        if (room != null) {
                            int roomId = room.getId();
                            int userIdInt = Integer.parseInt(userId);
                            Comment commentObj = new Comment(roomId, userIdInt, comment);
                            CommentDAO commentDAO = new CommentDAO();
                            commentDAO.addComment(commentObj);
                            multicastComment(roomName, username, comment);
                        } else {
                            System.out.println("Room not found: " + roomName);
                        }
                    } catch (NumberFormatException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }

    private void sendRoomList() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);

            InetAddress group = InetAddress.getByName(ServerConfig.BROADCAST_ADDRESS);
            StringBuilder roomList = new StringBuilder("ROOM_LIST:");

            for (Room room : roomManager.getRooms().values()) {
                String ownerId = String.valueOf(room.getOwnerId());
                String ownerName = userManager.getUserById(Integer.parseInt(ownerId)).getUsername();
                roomList.append(room.getRoomName()).append("|")
                        .append(ownerName).append("|")
                        .append(room.getParticipantCount()).append("|")
                        .append(room.getOwnerId()).append(",");
            }

            byte[] buffer = roomList.toString().getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, ClientConfig.BROADCAST_PORT);

            socket.send(packet);
            System.out.println("Sent room list to broadcast address: " + roomList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void multicastComment(String roomName, String username, String comment) {
        Room room = roomManager.getRooms().get(roomName);
        if (room != null) {
            try (DatagramSocket socket = new DatagramSocket()) {
                InetAddress group = InetAddress.getByName(room.getMulticastAddress());
                String message = "COMMENT:" + username + ":" + comment + ":" + roomName + ":server";
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, room.getMulticastPort());
                socket.send(packet);
                System.out.println("Multicast comment to room: " + roomName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void multicastRoomClosure(String roomName) {
        Room room = roomManager.getRooms().get(roomName);
        if (room != null) {
            try (DatagramSocket socket = new DatagramSocket()) {
                InetAddress group = InetAddress.getByName(room.getMulticastAddress());
                String message = "ROOM_CLOSED:" + roomName;
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, room.getMulticastPort());
                socket.send(packet);
                System.out.println("Multicast room closure for room: " + roomName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }
}