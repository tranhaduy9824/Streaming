package org.example.client.UI;

import javax.swing.*;

import org.example.client.LivestreamClient;

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
                System.out.println("Mouse clicked: " + e.getClickCount() + " times");
                if (e.getClickCount() == 2) {
                    String selectedRoom = roomList.getSelectedValue();
                    System.out.println("Double-click detected on room: " + selectedRoom);
                    if (selectedRoom != null) {
                        String roomName = selectedRoom.split(" ")[0]; 
                        System.out.println("Joining room: " + roomName);
                        LivestreamClient.joinRoom(roomName);
                    }
                }
            }
        });
    }
}