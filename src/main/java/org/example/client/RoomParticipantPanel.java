package org.example.client;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomParticipantPanel extends JPanel {
    private JTextPane commentPane;
    private JTextField commentField;
    private StyledDocument doc;

    public RoomParticipantPanel() {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Room Participant - Live Stream");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        commentPane = new JTextPane();
        commentPane.setEditable(false);
        doc = commentPane.getStyledDocument();
        add(new JScrollPane(commentPane), BorderLayout.CENTER);

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
    }

    private class SendCommentActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comment = commentField.getText();
            if (!comment.trim().isEmpty()) {
                LivestreamClient.sendComment(comment);
                addComment("You: " + comment, false);
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

    public void addComment(String comment, boolean isOwner) {
        try {
            Style style = doc.addStyle("Style", null);
            if (isOwner) {
                StyleConstants.setForeground(style, Color.RED);
                doc.insertString(doc.getLength(), "Owner: " + comment + "\n", style);
            } else {
                StyleConstants.setForeground(style, Color.BLACK);
                doc.insertString(doc.getLength(), comment + "\n", style);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}