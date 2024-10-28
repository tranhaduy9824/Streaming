package org.example.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class LiveStreamServer {
    private static Map<String, InetAddress> liveSessions = new HashMap<>(); // Store live session information

    public static void main(String[] args) {
        int port = 12345;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("Server running at IP: " + ip.getHostAddress() + " and port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle client requests (live machine or viewer)
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String command = in.readLine();

                if (command.startsWith("START_LIVE")) {
                    String sessionName = command.split(" ")[1];
                    InetAddress ip = clientSocket.getInetAddress();
                    liveSessions.put(sessionName, ip);
                    out.println("Session " + sessionName + " started.");

                } else if (command.startsWith("LIST_SESSIONS")) {
                    out.println("Available sessions: " + String.join(", ", liveSessions.keySet()));

                } else if (command.startsWith("JOIN_SESSION")) {
                    String sessionName = command.split(" ")[1];
                    InetAddress ip = liveSessions.get(sessionName);
                    if (ip != null) {
                        out.println("Session " + sessionName + " is at " + ip.getHostAddress());
                    } else {
                        out.println("Session not found.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
