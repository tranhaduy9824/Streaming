package org.example.client;

import javax.swing.*;
import javax.swing.text.*;
import org.example.config.ServerConfig;
import org.example.utils.Constants;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class RoomParticipantPanel extends JPanel {
    private JTextPane commentPane;
    private JTextField commentField;
    private StyledDocument doc;
    private JLabel videoLabel;
    private WebSocketClient client;

    public RoomParticipantPanel() {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Room Participant - Live Stream");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        videoLabel = new JLabel();
        add(videoLabel, BorderLayout.CENTER);

        commentPane = new JTextPane();
        commentPane.setEditable(false);
        doc = commentPane.getStyledDocument();
        add(new JScrollPane(commentPane), BorderLayout.EAST);

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

        connectWebSocket();
    }

    private void connectWebSocket() {
        try {
            client = new WebSocketClient(
                    new URI("ws://" + Constants.SERVER_ADDRESS + ":" + ServerConfig.SIGNALING_PORT)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to server");
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received: " + message);
                    SwingUtilities.invokeLater(() -> {
                        try {
                            byte[] imageBytes = Base64.getDecoder().decode(message);
                            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                            if (image != null) {
                                videoLabel.setIcon(new ImageIcon(image));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Connection closed");
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
            if (client != null) {
                client.close();
            }
        }
    }

    public void addComment(String comment, boolean isOwner) {
        try {
            Style style = doc.addStyle("Style", null);
            if (isOwner) {
                StyleConstants.setForeground(style, Color.BLUE);
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