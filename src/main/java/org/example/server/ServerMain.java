package org.example.server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.HashSet;
import java.util.Set;

public class ServerMain {
    private static final int UDP_PORT = 12346; // Cổng UDP
    private static final String MULTICAST_GROUP = "224.0.0.1"; // Địa chỉ multicast
    private static final int HTTP_PORT = 12347; // Cổng HTTP
    private static final Set<String> users = new HashSet<>(); // Danh sách người dùng đã đăng ký

    public static void main(String[] args) {
        new Thread(ServerMain::startUdpServer).start(); // Khởi động server UDP
        startHttpServer(); // Khởi động server HTTP
    }

    private static void startUdpServer() {
        try (MulticastSocket multicastSocket = new MulticastSocket(UDP_PORT)) {
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP);
            multicastSocket.joinGroup(group);
            System.out.println("UDP multicast server is running on port " + UDP_PORT + "...");

            byte[] buffer = new byte[1024]; // Bộ đệm để nhận tin nhắn
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet); // Nhận tin nhắn UDP
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received UDP message: " + message);
                handleMessage(message, packet.getAddress(), packet.getPort(), multicastSocket); // Xử lý tin nhắn
            }
        } catch (IOException e) {
            System.err.println("Error in UDP server: " + e.getMessage());
        }
    }

    private static void startHttpServer() {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(HTTP_PORT), 0);
            httpServer.createContext("/comment", new CommentHandler()); // Tạo context cho bình luận
            httpServer.setExecutor(null); // Sử dụng executor mặc định
            httpServer.start();
            System.out.println("HTTP server is running on port " + HTTP_PORT + "...");
        } catch (IOException e) {
            System.err.println("Error in HTTP server: " + e.getMessage());
        }
    }

    static class CommentHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Xử lý phương thức OPTIONS
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                // Thiết lập header CORS
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
                exchange.sendResponseHeaders(200, -1); // Không có nội dung trả về
                return;
            }
    
            // Xử lý phương thức POST
            if ("POST".equals(exchange.getRequestMethod())) {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                System.out.println("Received HTTP comment: " + requestBody);
    
                // Xử lý bình luận
                String response = "Comment received: " + requestBody;
    
                // Gửi phản hồi
                sendResponse(exchange, 200, response);
            } else {
                String response = "Only POST requests are supported.";
                sendResponse(exchange, 405, response);
            }
        }
    
        private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
            exchange.sendResponseHeaders(statusCode, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }    

    private static void handleMessage(String message, InetAddress address, int port, MulticastSocket multicastSocket) {
        String[] parts = message.split(":", 2);
        String command = parts[0].trim();

        switch (command) {
            case "REGISTER":
                handleRegister(parts.length > 1 ? parts[1].trim() : "", address, port, multicastSocket);
                break;
            case "COMMENT":
                handleComment(parts.length > 1 ? parts[1].trim() : "", multicastSocket);
                break;
            default:
                sendResponse("UNKNOWN_COMMAND", multicastSocket);
                break;
        }
    }

    private static void sendResponse(String message, MulticastSocket multicastSocket) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_GROUP), UDP_PORT);
            multicastSocket.send(packet); // Gửi phản hồi qua multicast
        } catch (IOException e) {
            System.err.println("Error sending response: " + e.getMessage());
        }
    }

    private static void handleRegister(String username, InetAddress address, int port, MulticastSocket multicastSocket) {
        if (users.contains(username)) {
            sendResponse("REGISTER_FAILED: Username already exists.", multicastSocket);
        } else {
            users.add(username);
            sendResponse("REGISTER_SUCCESS", multicastSocket);
            System.out.println("User registered: " + username);
        }
    }

    private static void handleComment(String message, MulticastSocket multicastSocket) {
        String responseMessage = "COMMENT_FROM: " + message;
        sendResponse(responseMessage, multicastSocket);
        System.out.println("Forwarded comment: " + responseMessage);
    }
}
