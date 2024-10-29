package org.example.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerMain {
    private static final int TCP_PORT = 12345;
    private static final int UDP_PORT = 12346;
    private static Map<String, List<ClientHandler>> rooms = new HashMap<>(); // Use ClientHandler instead of Socket
    private static Set<String> users = new HashSet<>(); // Store registered users

    public static void main(String[] args) {
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress(); // Automatically get server's IP address
            try (ServerSocket serverSocket = new ServerSocket(TCP_PORT, 50, InetAddress.getByName(hostAddress))) {
                System.out.println("TCP server is running on " + hostAddress + ":" + TCP_PORT + "...");

                // Start a thread for the UDP server
                new Thread(ServerMain::startUdpServer).start();

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new ClientHandler(clientSocket).start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startUdpServer() {
        try (DatagramSocket udpSocket = new DatagramSocket(UDP_PORT)) {
            System.out.println("UDP server is running on port " + UDP_PORT + "...");
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                udpSocket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received UDP message: " + message);
                // Handle UDP message here
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private String username;
        private String currentRoom;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String request;
                while ((request = in.readLine()) != null) {
                    System.out.println("Received: " + request);
                    if (request.startsWith("GET") && request.contains("Upgrade: websocket")) {
                        handleWebSocketHandshake(in, out);
                        // Continue handling WebSocket after successful handshake
                    } else {
                        // Handle other requests
                        handleRequest(request, out);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Clean up on disconnect
                if (currentRoom != null && rooms.containsKey(currentRoom)) {
                    rooms.get(currentRoom).remove(this);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleWebSocketHandshake(BufferedReader in, PrintWriter out) throws IOException {
            // Read all lines in the request and confirm
            String line;
            while (!(line = in.readLine()).isEmpty()) {
                System.out.println("Received: " + line);
            }
            // Send WebSocket response
            out.println("HTTP/1.1 101 Switching Protocols");
            out.println("Upgrade: websocket");
            out.println("Connection: Upgrade");
            out.println(); // Add a blank line to end the header

            // Loop to handle WebSocket data (add your own decoding logic)
            while (true) {
                // Read data from WebSocket (implement reading and decoding)
                // String message = ...; // Read message from the input stream
                // System.out.println("WebSocket message: " + message);
                // Handle message...
            }
        }

        private void handleRequest(String request, PrintWriter out) {
            String[] parts = request.split(":", 2);
            String command = parts[0].trim();

            switch (command) {
                case "REGISTER":
                    handleRegister(parts[1].trim(), out);
                    break;
                case "LOGIN":
                    handleLogin(parts[1].trim(), out);
                    break;
                case "CREATE_ROOM":
                    handleCreateRoom(parts[1].trim(), out);
                    break;
                case "JOIN_ROOM":
                    handleJoinRoom(parts[1].trim(), out);
                    break;
                case "COMMENT":
                    handleComment(parts[1].trim());
                    break;
                default:
                    out.println("UNKNOWN_COMMAND");
                    break;
            }
        }

        private void handleRegister(String username, PrintWriter out) {
            if (users.contains(username)) {
                out.println("REGISTER_FAILED: Username already exists.");
            } else {
                users.add(username);
                this.username = username;
                out.println("REGISTER_SUCCESS");
            }
        }

        private void handleLogin(String username, PrintWriter out) {
            if (users.contains(username)) {
                this.username = username;
                out.println("LOGIN_SUCCESS");
            } else {
                out.println("LOGIN_FAILED: Username does not exist.");
            }
        }

        private void handleCreateRoom(String roomName, PrintWriter out) {
            rooms.putIfAbsent(roomName, new ArrayList<>());
            rooms.get(roomName).add(this);
            currentRoom = roomName; // Set current room for this user
            out.println("ROOM_CREATED: " + roomName);
        }

        private void handleJoinRoom(String roomName, PrintWriter out) {
            List<ClientHandler> room = rooms.get(roomName);
            if (room != null) {
                room.add(this);
                currentRoom = roomName; // Set current room for this user
                out.println("JOINED_ROOM: " + roomName);
            } else {
                out.println("JOIN_FAILED: Room does not exist.");
            }
        }

        private void handleComment(String message) {
            // Send comment to all users in the current room
            if (currentRoom != null && rooms.containsKey(currentRoom)) {
                for (ClientHandler handler : rooms.get(currentRoom)) {
                    try {
                        PrintWriter writer = new PrintWriter(handler.socket.getOutputStream(), true);
                        writer.println("COMMENT_FROM_" + username + ": " + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
