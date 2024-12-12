package org.example.client;

import org.example.client.UI.LiveStreamPanel;
import org.example.client.UI.LoginPanel;
import org.example.client.UI.MainPanel;
import org.example.client.UI.RegistrationPanel;
import org.example.client.UI.RoomOwnerPanel;
import org.example.client.UI.RoomParticipantPanel;
import org.example.client.UI.components.Toaster.Toaster;
import org.example.config.ClientConfig;
import org.example.config.ServerConfig;
import org.example.server.model.Room;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class LivestreamClient {
    private static JFrame frame;
    private static String username;
    private static String userId;
    private static String currentRoom;
    private static DefaultListModel<String> roomListModel = new DefaultListModel<>();
    private static LiveStreamPanel liveStreamPanel;
    private static RoomOwnerPanel roomOwnerPanel;
    private static RoomParticipantPanel roomParticipantPanel;
    private static boolean checkRoomOwnerAfterUpdate = false;
    private static Toaster toaster;
    private static MainPanel mainPanel;
    private static String currentMulticastAddress;
    private static int currentMulticastPort;

    public static void main(String[] args) {
        frame = new JFrame("Livestream Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        showLoginPanel();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new Thread(LivestreamClient::listenForBroadcastMessages).start();
    }

    public static void showLoginPanel() {
        frame.getContentPane().removeAll();
        frame.add(new LoginPanel(), BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public static void showRegistrationPanel() {
        frame.getContentPane().removeAll();
        frame.add(new RegistrationPanel(), BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public static void showMainPanel() {
        frame.getContentPane().removeAll();
        mainPanel = new MainPanel();
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        toaster = new Toaster(mainPanel);
    }

    public static void showLiveStreamPanel() {
        frame.getContentPane().removeAll();
        liveStreamPanel = new LiveStreamPanel();
        frame.add(liveStreamPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public static void showRoomOwnerPanel() {
        frame.getContentPane().removeAll();
        roomOwnerPanel = new RoomOwnerPanel();
        frame.add(roomOwnerPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        toaster = new Toaster(roomOwnerPanel);
    }

    public static void showRoomParticipantPanel() {
        frame.getContentPane().removeAll();
        roomParticipantPanel = new RoomParticipantPanel();
        frame.add(roomParticipantPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        toaster = new Toaster(roomParticipantPanel);
    }

    public static boolean sendBroadcastMessage(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            InetAddress group = InetAddress.getByName(ClientConfig.BROADCAST_ADDRESS);
            String fullMessage = message + ":" + InetAddress.getLocalHost().getHostAddress();
            byte[] buffer = fullMessage.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, ServerConfig.BROADCAST_PORT);
            socket.send(packet);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void listenForBroadcastMessages() {
        try (DatagramSocket socket = new DatagramSocket(ClientConfig.BROADCAST_PORT)) {
            byte[] buffer = new byte[1024];
            System.out.println("Listening for broadcast messages on port " + ClientConfig.BROADCAST_PORT + "...");

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received broadcast message: " + message);

                // Xử lý thông điệp tùy theo loại message
                if (message.startsWith("ROOM_LIST:")) {
                    updateRoomList(message.substring(10));
                } else if (message.startsWith("COMMENT:")) {
                    handleCommentMessage(message);
                } else if (message.startsWith("ROOM_CLOSED:")) {
                    handleRoomClosedMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void listenForMulticastMessages(String multicastAddress, int multicastPort) {
        new Thread(() -> {
            try (MulticastSocket socket = new MulticastSocket(multicastPort)) {
                InetAddress group = InetAddress.getByName(multicastAddress);
                socket.joinGroup(group);

                if (isRoomOwner(currentRoom)) {
                    RoomOwnerPanel.setMulticastSocket(socket);
                    RoomOwnerPanel.setMulticastGroup(group);
                } else {
                    RoomParticipantPanel.setMulticastSocket(socket);
                    RoomParticipantPanel.setMulticastGroup(group);
                }

                byte[] buffer = new byte[1024];
                System.out.println(
                        "Listening for multicast messages on " + multicastAddress + ":" + multicastPort + "...");

                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Received multicast message: " + message);

                    if (message.startsWith("COMMENT:")) {
                        handleCommentMessage(message);
                    } else if (message.startsWith("ROOM_CLOSED:")) {
                        handleRoomClosedMessage(message);
                    } else {
                        if (isRoomOwner(currentRoom)) {
                            RoomOwnerPanel.handleMessage(message, socket, group);
                        } else {
                            RoomParticipantPanel.handleMessage(message, socket, group);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void handleCommentMessage(String message) {
        String[] parts = message.split(":");
        if (parts.length == 5) {
            String sender = parts[1];
            String comment = parts[2];
            boolean isOwner = sender.equals(getRoomOwner(currentRoom));
            if (!sender.equals(username) && currentRoom != null && currentRoom.equals(parts[3])) {
                comment = sender + ": " + comment;
                if (liveStreamPanel != null) {
                    liveStreamPanel.addComment(comment, isOwner);
                } else if (roomOwnerPanel != null) {
                    roomOwnerPanel.addComment(comment, isOwner);
                } else if (roomParticipantPanel != null) {
                    roomParticipantPanel.addComment(comment, isOwner);
                }
            }
        }
    }

    private static void handleRoomClosedMessage(String message) {
        String roomName = message.split(":")[1];
        System.out.println("Received room closed message for room: " + roomName);
        if (currentRoom != null && currentRoom.equals(roomName)) {
            toaster.success("The room has been closed by the owner.");
            JOptionPane.showMessageDialog(frame, "The room has been closed by the owner.", "Room Closed",
                    JOptionPane.INFORMATION_MESSAGE);
            leaveRoom();
        }
    }

    private static String getRoomOwner(String roomName) {
        for (int i = 0; i < roomListModel.size(); i++) {
            String roomDetails = roomListModel.get(i);
            if (roomDetails.startsWith(roomName + " (Owner: ")) {
                int startIndex = roomDetails.indexOf("Owner: ") + 7;
                int endIndex = roomDetails.indexOf(", Participants:");
                if (startIndex != -1 && endIndex != -1) {
                    System.out.println("Room owner: " + roomDetails.substring(startIndex, endIndex));
                    return roomDetails.substring(startIndex, endIndex);
                }
            }
        }
        return null;
    }

    private static void updateRoomList(String roomList) {
        SwingUtilities.invokeLater(() -> {
            roomListModel.clear();
            String[] rooms = roomList.split(",");
            for (String room : rooms) {
                if (!room.isEmpty()) {
                    String[] roomDetails = room.split("\\|");
                    if (roomDetails.length == 6) {
                        String roomName = roomDetails[0];
                        String owner = roomDetails[1];
                        String participantCount = roomDetails[3];

                        setCurrentMulticastAddress(roomDetails[4]);
                        setCurrentMulticastPort(Integer.parseInt(roomDetails[5]));

                        roomListModel.addElement(
                                roomName + " (Owner: " + owner + ", Participants: " + participantCount + ")");

                        if (currentRoom != null && currentRoom.equals(roomName) && roomOwnerPanel != null) {
                            roomOwnerPanel.updateParticipantsCount(Integer.parseInt(participantCount));
                        } else if (currentRoom != null && currentRoom.equals(roomName)
                                && roomParticipantPanel != null) {
                            roomParticipantPanel.updateParticipantsCount(Integer.parseInt(participantCount));
                        }
                    } else {
                        System.err.println("Invalid room details: " + room);
                    }
                }
            }
            System.out.println("Updated room list: " + roomList);

            if (checkRoomOwnerAfterUpdate) {
                checkRoomOwnerAfterUpdate = false;
                if (isRoomOwner(currentRoom)) {
                    showRoomOwnerPanel();
                } else {
                    showRoomParticipantPanel();
                }
            }

            if (frame.getContentPane().getComponent(0) instanceof MainPanel) {
                MainPanel mainPanel = (MainPanel) frame.getContentPane().getComponent(0);
                mainPanel.updateRoomList(roomList);
            }
        });
    }

    public static void joinRoom(String roomName) {
        currentRoom = roomName;
        System.out.println("Attempting to join room: " + roomName);
        sendBroadcastMessage("JOIN_ROOM:" + username + ":" + userId + ":" + roomName);
        checkRoomOwnerAfterUpdate = true;

        listenForMulticastMessages(LivestreamClient.getCurrentMulticastAddress(),
                LivestreamClient.getCurrentMulticastPort());
    }

    public static void closeRoom() {
        if (currentRoom != null) {
            sendBroadcastMessage("CLOSE_ROOM:" + username + ":" + userId + ":" + currentRoom);
            currentRoom = null;
            showMainPanel();
            if (roomOwnerPanel != null) {
                roomOwnerPanel.stopAllStreams();
            }
        }
    }

    public static void leaveRoom() {
        if (currentRoom != null) {
            sendBroadcastMessage("LEAVE_ROOM:" + username + ":" + userId + ":" + currentRoom);
            currentRoom = null;
            showMainPanel();
        }
    }

    public static void createRoom(String roomName, String multicastAddress, int multicastPort) {
        String message = "CREATE_ROOM:" + username + ":" + userId + ":" + roomName + ":" + multicastAddress + ":"
                + multicastPort;
        if (sendBroadcastMessage(message)) {
            System.out.println("Create room request sent successfully for room: " + roomName);
            currentRoom = roomName;
            showRoomOwnerPanel();
        } else {
            toaster.error("Failed to send create room request.");
        }

        Room room = new Room();
        room.setRoomName(roomName);
        room.setOwnerId(Integer.parseInt(userId));
        room.setMulticastAddress(multicastAddress);
        room.setMulticastPort(multicastPort);

        listenForMulticastMessages(multicastAddress, multicastPort);
    }

    public static void sendComment(String comment) {
        if (currentRoom != null) {
            String message = "COMMENT:" + username + ":" + userId + ":" + currentRoom + ":" + comment;
            sendBroadcastMessage(message);
        }
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        LivestreamClient.username = username;
    }

    public static void setUserId(String userId) {
        LivestreamClient.userId = userId;
    }

    public static DefaultListModel<String> getRoomListModel() {
        return roomListModel;
    }

    public static String getCurrentRoom() {
        return currentRoom;
    }

    private static boolean isRoomOwner(String roomName) {
        for (int i = 0; i < roomListModel.size(); i++) {
            String roomDetails = roomListModel.get(i);
            System.out.println("Checking room details: " + roomDetails);
            if (roomDetails.startsWith(roomName + " (Owner: " + username)) {
                System.out.println("User is the owner of the room: " + roomName);
                return true;
            }
        }
        System.out.println("User is not the owner of the room: " + roomName);
        return false;
    }

    public static Room getRoomDetails(String roomName) {
        for (int i = 0; i < roomListModel.size(); i++) {
            String roomDetails = roomListModel.get(i);
            System.out.println("Checking room details: " + roomDetails);
            if (roomDetails.startsWith(roomName + " (Owner: ")) {
                String[] details = roomDetails.split("\\s*\\(Owner:\\s*|,\\s*Participants:\\s*|\\)");
                System.out.println("Room details split into: " + Arrays.toString(details));
                if (details.length >= 3) {
                    String owner = details[1].trim();
                    int participantCount = Integer.parseInt(details[2].trim());

                    String multicastAddress = getCurrentMulticastAddress();
                    int multicastPort = getCurrentMulticastPort();

                    System.out.println("Owner: " + owner);
                    System.out.println("Participant Count: " + participantCount);
                    System.out.println("Multicast Address: " + multicastAddress);
                    System.out.println("Multicast Port: " + multicastPort);

                    Room room = new Room();
                    room.setRoomName(roomName);
                    room.setOwnerId(Integer.parseInt(owner));
                    room.setMulticastAddress(multicastAddress);
                    room.setMulticastPort(multicastPort);

                    return room;
                } else {
                    System.out.println("Room details length is less than expected.");
                }
            }
        }
        System.out.println("Room not found: " + roomName);
        return null;
    }

    public static void setCurrentMulticastAddress(String currentMulticastAddress) {
        LivestreamClient.currentMulticastAddress = currentMulticastAddress;
    }

    public static void setCurrentMulticastPort(int currentMulticastPort) {
        LivestreamClient.currentMulticastPort = currentMulticastPort;
    }

    public static String getCurrentMulticastAddress() {
        return currentMulticastAddress;
    }

    public static int getCurrentMulticastPort() {
        return currentMulticastPort;
    }
}