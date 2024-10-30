package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomListPanel extends JPanel {
    private JList<String> roomList;

    public RoomListPanel() {
        setLayout(new BorderLayout());
        roomList = new JList<>(LivestreamClient.getRoomListModel());
        add(new JScrollPane(roomList), BorderLayout.CENTER);
        JButton joinButton = new JButton("Join Room");
        add(joinButton, BorderLayout.SOUTH);
        joinButton.addActionListener(new JoinRoomActionListener());
    }

    private class JoinRoomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedRoom = roomList.getSelectedValue();
            if (selectedRoom != null) {
                String roomName = selectedRoom.split(" ")[0]; // Extract the room name
                LivestreamClient.joinRoom(roomName);
            }
        }
    }
}