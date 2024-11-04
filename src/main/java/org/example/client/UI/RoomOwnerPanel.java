package org.example.client.UI;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;
import org.example.client.LivestreamClient;
import org.example.client.UI.components.UIUtils;
import org.example.config.ServerConfig;
import org.example.utils.Constants;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Objects;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoomOwnerPanel extends JPanel {
    private JTextPane commentPane;
    private JTextField commentField;
    private StyledDocument doc;
    private JPanel videoPanel;
    private FrameGrabber grabber;
    private Java2DFrameConverter converter;
    private BufferedImage currentImage;
    private WebSocketClient client;
    private JLabel participantsLabel;

    public RoomOwnerPanel() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.COLOR_BACKGROUND);

        JLabel titleLabel = new JLabel("Room Owner - Live Stream");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(UIUtils.FONT_GENERAL_UI.deriveFont(Font.BOLD, 24));
        titleLabel.setForeground(UIUtils.OFFWHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        videoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentImage != null) {
                    g.drawImage(currentImage, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        videoPanel.setPreferredSize(new Dimension(640, 480));
        videoPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        add(videoPanel, BorderLayout.WEST);

        JPanel participantsPanel = new JPanel();
        participantsPanel.setOpaque(false);
        participantsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("group.png")));
        Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(scaledIcon);
        participantsLabel = new JLabel("0");
        participantsLabel.setForeground(UIUtils.OFFWHITE);
        participantsLabel.setFont(UIUtils.FONT_GENERAL_UI);
        participantsPanel.add(iconLabel);
        participantsPanel.add(participantsLabel);
        videoPanel.add(participantsPanel, BorderLayout.NORTH);

        commentPane = new JTextPane();
        commentPane.setEditable(false);
        commentPane.setBackground(UIUtils.COLOR_BACKGROUND);
        commentPane.setForeground(UIUtils.OFFWHITE);
        commentPane.setFont(UIUtils.FONT_GENERAL_UI);
        doc = commentPane.getStyledDocument();
        add(new JScrollPane(commentPane), BorderLayout.CENTER);

        JPanel commentPanel = new JPanel(new BorderLayout());
        commentPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        commentField = new JTextField();
        commentField.setBackground(UIUtils.COLOR_BACKGROUND);
        commentField.setForeground(UIUtils.OFFWHITE);
        commentField.setFont(UIUtils.FONT_GENERAL_UI);
        commentPanel.add(commentField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        styleButton(sendButton);
        sendButton.addActionListener(new SendCommentActionListener());
        commentPanel.add(sendButton, BorderLayout.EAST);
        add(commentPanel, BorderLayout.SOUTH);

        JButton closeRoomButton = new JButton("Close Room");
        styleButton(closeRoomButton);
        closeRoomButton.addActionListener(new CloseRoomActionListener());
        add(closeRoomButton, BorderLayout.NORTH);

        connectWebSocket();
        startVideoStream();
    }

    public void updateParticipantsCount(int count) {
        participantsLabel.setText(String.valueOf(count));
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

    private void startVideoStream() {
        new Thread(() -> {
            try {
                grabber = new VideoInputFrameGrabber(1);
                grabber.start();
                converter = new Java2DFrameConverter();
                while (true) {
                    try {
                        Frame frame = grabber.grab();
                        if (frame != null) {
                            currentImage = converter.convert(frame);
                            videoPanel.repaint();

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(currentImage, "jpg", baos);
                            byte[] imageBytes = baos.toByteArray();
                            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
                            if (client != null && client.isOpen()) {
                                client.send(encodedImage);
                            } else {
                                System.out.println("WebSocket connection is not open.");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private class SendCommentActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comment = commentField.getText();
            if (!comment.trim().isEmpty()) {
                LivestreamClient.sendComment(comment);
                addComment("You: " + comment, true);
                commentField.setText("");
            }
        }
    }

    private class CloseRoomActionListener implements ActionListener {
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
            StyleConstants.setForeground(style, isOwner ? Color.RED : Color.WHITE);
            doc.insertString(doc.getLength(), comment + "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}