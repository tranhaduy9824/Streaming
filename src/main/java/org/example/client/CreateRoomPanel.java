package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateRoomPanel extends JPanel {
    private JTextField roomNameField;

    public CreateRoomPanel() {
        setLayout(new GridLayout(2, 2));
        add(new JLabel("Room Name:"));
        roomNameField = new JTextField();
        add(roomNameField);
        JButton createRoomButton = new JButton("Create Room");
        add(createRoomButton);
        createRoomButton.addActionListener(new CreateRoomActionListener());
    }

    private class CreateRoomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String roomName = roomNameField.getText();
            String username = LivestreamClient.getUsername(); // Get the stored username
            System.out.println("Room name entered: " + roomName); // Add this line
    
            if (roomName != null && !roomName.trim().isEmpty()) {
                String message = "CREATE_ROOM:" + username + ":" + roomName;
                System.out.println("Message to be sent: " + message); // Add this line
                if (LivestreamClient.sendBroadcastMessage(message)) {
                    System.out.println("Create room request sent successfully for room: " + roomName);
                } else {
                    JOptionPane.showMessageDialog(CreateRoomPanel.this, "Failed to send create room request.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(CreateRoomPanel.this, "Room name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}