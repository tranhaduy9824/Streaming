package org.example.client.UI;

import javax.swing.*;

import org.example.client.LivestreamClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LiveStreamPanel extends JPanel {
    private JTextArea commentArea;
    private JTextField commentField;

    public LiveStreamPanel() {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Live Stream");
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
    }

    private class SendCommentActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comment = commentField.getText();
            if (!comment.trim().isEmpty()) {
                LivestreamClient.sendComment(comment);
                commentField.setText("");
            }
        }
    }

    public void addComment(String comment, boolean isOwner) {
        if (isOwner) {
            commentArea.append("Owner: " + comment + "\n");
            commentArea.setForeground(Color.RED);
        } else {
            commentArea.append(comment + "\n");
            commentArea.setForeground(Color.BLACK);
        }
    }
}