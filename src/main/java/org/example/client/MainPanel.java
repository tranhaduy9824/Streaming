package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel {
    private JList<String> roomList;

    public MainPanel() {
        setLayout(new BorderLayout());

        JLabel usernameLabel = new JLabel("Logged in as: " + LivestreamClient.getUsername());
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(usernameLabel, BorderLayout.NORTH);

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

    private class CreateRoomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String roomName = JOptionPane.showInputDialog(MainPanel.this, "Enter room name:");
            if (roomName != null && !roomName.trim().isEmpty()) {
                LivestreamClient.createRoom(roomName); // Use the new createRoom method
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