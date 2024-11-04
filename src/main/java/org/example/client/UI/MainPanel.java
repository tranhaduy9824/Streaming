package org.example.client.UI;

import javax.swing.*;
import org.example.client.LivestreamClient;
import org.example.client.UI.components.UIUtils;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel {
    private JList<String> roomList;

    public MainPanel() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.COLOR_BACKGROUND);

        JLabel usernameLabel = new JLabel("Logged in as: " + LivestreamClient.getUsername());
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setFont(UIUtils.FONT_GENERAL_UI);
        usernameLabel.setForeground(UIUtils.OFFWHITE);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(usernameLabel, BorderLayout.NORTH);

        roomList = new JList<>(LivestreamClient.getRoomListModel());
        roomList.setBackground(UIUtils.COLOR_BACKGROUND);
        roomList.setForeground(UIUtils.OFFWHITE);
        roomList.setFont(UIUtils.FONT_GENERAL_UI);
        roomList.setSelectionBackground(UIUtils.COLOR_INTERACTIVE);
        roomList.setSelectionForeground(Color.white);
        add(new JScrollPane(roomList), BorderLayout.CENTER);

        roomList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedRoom = roomList.getSelectedValue();
                    if (selectedRoom != null) {
                        String roomName = selectedRoom.split(" ")[0];
                        LivestreamClient.joinRoom(roomName);
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton createRoomButton = new JButton("Create Room");
        styleButton(createRoomButton);
        createRoomButton.addActionListener(e -> {
            String roomName = JOptionPane.showInputDialog(this, "Enter room name:");
            if (roomName != null && !roomName.trim().isEmpty()) {
                LivestreamClient.createRoom(roomName.trim());
            }
        });
        buttonPanel.add(createRoomButton);

        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton);
        logoutButton.addActionListener(e -> LivestreamClient.showLoginPanel());
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button) {
        button.setBackground(UIUtils.COLOR_INTERACTIVE);
        button.setForeground(Color.white);
        button.setFont(UIUtils.FONT_GENERAL_UI);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(UIUtils.COLOR_INTERACTIVE_DARKER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(UIUtils.COLOR_INTERACTIVE);
            }
        });
    }
}