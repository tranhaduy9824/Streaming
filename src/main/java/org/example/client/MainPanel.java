package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainPanel extends JPanel {
    private JList<String> roomList;

    public MainPanel() {
        setLayout(new BorderLayout());

        JButton refreshButton = new JButton("Refresh Room List");
        refreshButton.addActionListener(new RefreshRoomListActionListener());
        add(refreshButton, BorderLayout.NORTH);

        roomList = new JList<>();
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
            List<String> roomNames = LivestreamClient.receiveRoomList();
            roomList.setListData(roomNames.toArray(new String[0]));
        }
    }

    private class CreateRoomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String roomName = JOptionPane.showInputDialog(MainPanel.this, "Enter room name:");
            if (roomName != null && !roomName.trim().isEmpty()) {
                LivestreamClient.sendBroadcastMessage("CREATE_ROOM:" + roomName);
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