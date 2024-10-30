package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RoomListPanel extends JPanel {
    private JList<String> roomList;

    public RoomListPanel(List<String> roomNames) {
        setLayout(new BorderLayout());
        roomList = new JList<>(roomNames.toArray(new String[0]));
        add(new JScrollPane(roomList), BorderLayout.CENTER);
        JButton joinButton = new JButton("Join Room");
        add(joinButton, BorderLayout.SOUTH);
        joinButton.addActionListener(new JoinRoomActionListener());
    }

    private class JoinRoomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedRoom = roomList.getSelectedValue();
            // Logic to join the selected room
            System.out.println("Joined room: " + selectedRoom);
        }
    }
}