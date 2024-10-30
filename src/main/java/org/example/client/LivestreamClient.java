package org.example.client;

import org.example.config.ClientConfig;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class LivestreamClient {
    private static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("Livestream Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        showLoginPanel();

        frame.setVisible(true);
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
        frame.add(new MainPanel(), BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public static boolean sendBroadcastMessage(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(ClientConfig.BROADCAST_ADDRESS);
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, ClientConfig.BROADCAST_PORT);
            socket.send(packet);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> receiveRoomList() {
        List<String> roomNames = new ArrayList<>();
        try (DatagramSocket socket = new DatagramSocket(ClientConfig.BROADCAST_PORT)) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            if (message.startsWith("ROOM_LIST:")) {
                String[] rooms = message.substring(10).split(",");
                for (String room : rooms) {
                    if (!room.isEmpty()) {
                        roomNames.add(room);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return roomNames;
    }
}