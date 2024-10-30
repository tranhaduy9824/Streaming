package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    private JList<String> roomList;

    public MainPanel() {
        setLayout(new BorderLayout());

        JButton refreshButton = new JButton("Refresh Room List");
        refreshButton.addActionListener(new RefreshRoomListActionListener());
        add(refreshButton, BorderLayout.NORTH);

        roomList = new JList<>(LivestreamClient.getRoomListModel());
        add(new JScrollPane(roomList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        JButton createRoomButton = new JButton("Create Room");
        createRoomButton.addActionListener(new CreateRoomActionListener());
        buttonPanel.add(createRoomButton);

        JButton closeRoomButton = new JButton("Close Room");
        closeRoomButton.addActionListener(new CloseRoomActionListener());
        buttonPanel.add(closeRoomButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class RefreshRoomListActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LivestreamClient.sendBroadcastMessage("LIST_ROOMS");
            // No need to call receiveRoomList() here, as the client will update the room list asynchronously
        }
    }

    private class CreateRoomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String roomName = JOptionPane.showInputDialog(MainPanel.this, "Enter room name:");
            String username = LivestreamClient.getUsername(); // Get the stored username
            if (roomName != null && !roomName.trim().isEmpty()) {
                LivestreamClient.sendBroadcastMessage("CREATE_ROOM:" + username + ":" + roomName);
            }
        }
    }

    private class CloseRoomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String roomName = roomList.getSelectedValue();
            if (roomName != null) {
                LivestreamClient.sendBroadcastMessage("CLOSE_ROOM:" + roomName);
            }
        }
    }
}