package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoomListPanel extends JPanel {
    private JList<String> roomList;

    public RoomListPanel() {
        setLayout(new BorderLayout());
        roomList = new JList<>(LivestreamClient.getRoomListModel());
        add(new JScrollPane(roomList), BorderLayout.CENTER);

        roomList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked: " + e.getClickCount() + " times"); // Debug statement
                if (e.getClickCount() == 2) {
                    String selectedRoom = roomList.getSelectedValue();
                    System.out.println("Double-click detected on room: " + selectedRoom); // Debug statement
                    if (selectedRoom != null) {
                        String roomName = selectedRoom.split(" ")[0]; // Extract the room name
                        System.out.println("Joining room: " + roomName); // Debug statement
                        LivestreamClient.joinRoom(roomName);
                    }
                }
            }
        });
    }
}