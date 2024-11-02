package org.example.client.UI;

import javax.swing.*;

import org.example.client.LivestreamClient;

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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        JButton createRoomButton = new JButton("Create Room");
        createRoomButton.addActionListener(new CreateRoomActionListener());
        buttonPanel.add(createRoomButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class CreateRoomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String roomName = JOptionPane.showInputDialog(MainPanel.this, "Enter room name:");
            if (roomName != null && !roomName.trim().isEmpty()) {
                LivestreamClient.createRoom(roomName);
            }
        }
    }
}