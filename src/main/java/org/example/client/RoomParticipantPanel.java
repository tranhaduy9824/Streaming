package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomParticipantPanel extends JPanel {
    private JTextArea commentArea;
    private JTextField commentField;
    private WebRTCClient webRTCClient;

    public RoomParticipantPanel() {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Room Participant - Live Stream");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        commentArea = new JTextArea();
        commentArea.setEditable(false);
        add(new JScrollPane(commentArea), BorderLayout.CENTER);

        JPanel commentPanel = new JPanel(new BorderLayout());
        commentField = new JTextField();
        commentPanel.add(commentField, BorderLayout.CENTER);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendCommentActionListener());
        commentPanel.add(sendButton, BorderLayout.EAST);
        add(commentPanel, BorderLayout.SOUTH);

        JButton leaveRoomButton = new JButton("Leave Room");
        leaveRoomButton.addActionListener(new LeaveRoomActionListener());
        add(leaveRoomButton, BorderLayout.NORTH);

        // Start WebRTC connection
        webRTCClient = new WebRTCClient();
        webRTCClient.start();
    }

    private class SendCommentActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comment = commentField.getText();
            if (!comment.trim().isEmpty()) {
                LivestreamClient.sendComment(comment);
                commentArea.append("You: " + comment + "\n");
                commentField.setText("");
            }
        }
    }

    private class LeaveRoomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LivestreamClient.leaveRoom();
        }
    }

    public void addComment(String comment) {
        commentArea.append(comment + "\n");
    }
}