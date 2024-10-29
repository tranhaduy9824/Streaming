package org.example.server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class ServerMain {
    private static final int TCP_PORT = 12345;
    private static final int UDP_PORT = 12346;
    private static Map<String, List<ClientHandler>> rooms = new HashMap<>();
    private static Set<String> users = new HashSet<>();

    public static void main(String[] args) {
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            try (ServerSocket serverSocket = new ServerSocket(TCP_PORT, 50, InetAddress.getByName(hostAddress))) {
                System.out.println("TCP server is running on " + hostAddress + ":" + TCP_PORT + "...");
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private String username;
        private String currentRoom;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                this.out = new PrintWriter(socket.getOutputStream(), true);

                String request;
                while ((request = in.readLine()) != null) {
                    System.out.println("Received: " + request);
                    if (request.startsWith("GET") && request.contains("Upgrade: websocket")) {
                        handleWebSocketHandshake(in);
                        handleWebSocketCommunication(in);
                        break;
                    } else {
                        handleRequest(request);
                    }
                }

            } catch (IOException e) {
                System.err.println("Error in ClientHandler: " + e.getMessage());
                e.printStackTrace();
            } finally {
                cleanup();
            }
        }

        private void handleWebSocketHandshake(BufferedReader in) throws IOException {
            String line;
            String webSocketKey = null;

            while (!(line = in.readLine()).isEmpty()) {
                if (line.startsWith("Sec-WebSocket-Key:")) {
                    webSocketKey = line.split(": ")[1].trim();
                }
            }

            if (webSocketKey != null) {
                try {
                    String acceptKey = createAcceptKey(webSocketKey);
                    out.println("HTTP/1.1 101 Switching Protocols");
                    out.println("Upgrade: websocket");
                    out.println("Connection: Upgrade");
                    out.println("Sec-WebSocket-Accept: " + acceptKey);
                    out.println();
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleWebSocketCommunication(BufferedReader in) throws IOException {
            try {
                while (true) {
                    String message = readWebSocketMessage(socket.getInputStream());
                    if (message != null) {
                        System.out.println("WebSocket message: " + message);
                        out.println("Server received: " + message); // Echo back to client
                        out.flush();
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error in WebSocket communication: " + e.getMessage());
            } finally {
                cleanup(); // Đảm bảo kết nối được đóng khi gặp lỗi
            }
        }        

        private String readWebSocketMessage(InputStream input) throws IOException {
            DataInputStream dataInputStream = new DataInputStream(input);
            byte[] header = new byte[2];
            dataInputStream.readFully(header);

            boolean isMasked = (header[1] & 0x80) != 0;
            int payloadLength = header[1] & 0x7F;

            if (payloadLength == 126) {
                payloadLength = dataInputStream.readUnsignedShort();
            } else if (payloadLength == 127) {
                payloadLength = (int) dataInputStream.readLong();
            }

            byte[] maskingKey = new byte[4];
            if (isMasked) {
                dataInputStream.readFully(maskingKey);
            }

            byte[] payloadData = new byte[payloadLength];
            dataInputStream.readFully(payloadData);

            if (isMasked) {
                for (int i = 0; i < payloadLength; i++) {
                    payloadData[i] ^= maskingKey[i % 4];
                }
            }

            return new String(payloadData, StandardCharsets.UTF_8);
        }

        private String createAcceptKey(String key) throws Exception {
            return Base64.getEncoder().encodeToString(
                    MessageDigest.getInstance("SHA-1")
                            .digest((key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes(StandardCharsets.UTF_8)));
        }

        private void cleanup() {
            if (currentRoom != null && rooms.containsKey(currentRoom)) {
                rooms.get(currentRoom).remove(this);
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleRequest(String request) {
            String[] parts = request.split(":", 2);
            String command = parts[0].trim();

            switch (command) {
                case "REGISTER":
                    handleRegister(parts[1].trim());
                    break;
                case "LOGIN":
                    handleLogin(parts[1].trim());
                    break;
                case "CREATE_ROOM":
                    handleCreateRoom(parts[1].trim());
                    break;
                case "JOIN_ROOM":
                    handleJoinRoom(parts[1].trim());
                    break;
                case "COMMENT":
                    handleComment(parts[1].trim());
                    break;
                default:
                    out.println("UNKNOWN_COMMAND");
                    break;
            }
        }

        private void handleRegister(String username) {
            if (users.contains(username)) {
                out.println("REGISTER_FAILED: Username already exists.");
            } else {
                users.add(username);
                this.username = username;
                out.println("REGISTER_SUCCESS");
            }
        }

        private void handleLogin(String username) {
            if (users.contains(username)) {
                this.username = username;
                out.println("LOGIN_SUCCESS");
            } else {
                out.println("LOGIN_FAILED: Username does not exist.");
            }
        }

        private void handleCreateRoom(String roomName) {
            rooms.putIfAbsent(roomName, new ArrayList<>());
            rooms.get(roomName).add(this);
            currentRoom = roomName;
            out.println("ROOM_CREATED: " + roomName);
        }

        private void handleJoinRoom(String roomName) {
            List<ClientHandler> room = rooms.get(roomName);
            if (room != null) {
                room.add(this);
                currentRoom = roomName;
                out.println("JOINED_ROOM: " + roomName);
            } else {
                out.println("JOIN_FAILED: Room does not exist.");
            }
        }

        private void handleComment(String message) {
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
