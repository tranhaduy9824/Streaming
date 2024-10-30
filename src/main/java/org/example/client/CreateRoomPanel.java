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
            // Logic to create a room
            System.out.println("Room created: " + roomName);
        }
    }
}