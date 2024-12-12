package org.example.client.UI;

import javax.swing.*;
import javax.swing.text.*;
import org.example.client.LivestreamClient;
import org.example.client.UI.components.UIUtils;
import org.example.config.ServerConfig;
import org.example.utils.Constants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Objects;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class RoomParticipantPanel extends JPanel {
    public static JTextPane commentPane;
    public static JTextField commentField;
    public static StyledDocument doc;
    public static JPanel videoPanel;
    public static JPanel screenSharePanel;
    public static JPanel controlPanel;
    public static JLabel participantsLabel;
    public static BufferedImage videoImage;
    public static BufferedImage screenShareImage;
    public static JLayeredPane layeredPane;
    public static boolean isScreenSharing;
    public static MulticastSocket multicastSocket;
    public static InetAddress multicastGroup;

    public RoomParticipantPanel() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.COLOR_BACKGROUND);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));
        add(layeredPane, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Room Participant - Live Stream");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(UIUtils.FONT_GENERAL_UI.deriveFont(Font.BOLD, 24));
        titleLabel.setForeground(UIUtils.OFFWHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titleLabel.setBounds(0, 0, 800, 60);
        layeredPane.add(titleLabel, JLayeredPane.DEFAULT_LAYER);

        videoPanel = new JPanel(new BorderLayout()) {
            {
                setDoubleBuffered(true);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (videoImage != null) {
                    g.drawImage(videoImage, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        videoPanel.setPreferredSize(new Dimension(800, 480));
        videoPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        videoPanel.setBounds(0, 60, 800, 480);
        layeredPane.add(videoPanel, JLayeredPane.DEFAULT_LAYER);

        screenSharePanel = new JPanel(new BorderLayout()) {
            {
                setDoubleBuffered(true);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (screenShareImage != null) {
                    g.drawImage(screenShareImage, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        screenSharePanel.setPreferredSize(new Dimension(800, 480));
        screenSharePanel.setBackground(UIUtils.COLOR_BACKGROUND);
        screenSharePanel.setBounds(0, 60, 800, 480);
        screenSharePanel.setVisible(false);
        layeredPane.add(screenSharePanel, JLayeredPane.PALETTE_LAYER);

        JPanel participantsPanel = new JPanel();
        participantsPanel.setOpaque(false);
        participantsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        ImageIcon originalIcon = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource("group.png")));
        Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(scaledIcon);
        participantsLabel = new JLabel("0");
        participantsLabel.setForeground(UIUtils.OFFWHITE);
        participantsLabel.setFont(UIUtils.FONT_GENERAL_UI);
        participantsPanel.add(iconLabel);
        participantsPanel.add(participantsLabel);
        videoPanel.add(participantsPanel, BorderLayout.NORTH);

        controlPanel = new JPanel();
        controlPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setBounds(0, 540, 800, 40);
        videoPanel.add(controlPanel, BorderLayout.SOUTH);

        commentPane = new JTextPane();
        commentPane.setEditable(false);
        commentPane.setBackground(UIUtils.COLOR_BACKGROUND);
        commentPane.setForeground(UIUtils.OFFWHITE);
        commentPane.setFont(UIUtils.FONT_GENERAL_UI);
        doc = commentPane.getStyledDocument();
        JScrollPane commentScrollPane = new JScrollPane(commentPane);
        commentScrollPane.setBounds(0, 540, 800, 200);
        layeredPane.add(commentScrollPane, JLayeredPane.DEFAULT_LAYER);

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
        commentPanel.setBounds(0, 740, 800, 40);
        layeredPane.add(commentPanel, JLayeredPane.DEFAULT_LAYER);

        JButton leaveRoomButton = new JButton("Leave Room");
        styleButton(leaveRoomButton);
        leaveRoomButton.addActionListener(new LeaveRoomActionListener());
        leaveRoomButton.setBounds(0, 0, 150, 40);
        layeredPane.add(leaveRoomButton, JLayeredPane.DEFAULT_LAYER);
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

    public static void handleMessage(String message, MulticastSocket multicastSocket, InetAddress multicastGroup) {
        SwingUtilities.invokeLater(() -> {
            try {
                setMulticastSocket(multicastSocket);
                setMulticastGroup(multicastGroup);

                if (message.startsWith("SCREEN_SHARE:")) {
                    String base64Image = message.substring("SCREEN_SHARE:".length());
                    byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                    if (image != null) {
                        screenShareImage = image;
                        screenSharePanel.repaint();
                    }
                } else if (message.equals("SCREEN_SHARE_START")) {
                    setScreenSharing(true);
                } else if (message.equals("SCREEN_SHARE_STOP")) {
                    setScreenSharing(false);
                } else {
                    byte[] imageBytes = Base64.getDecoder().decode(message);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                    if (image != null) {
                        videoImage = image;
                        videoPanel.repaint();
                    }
                }
            } catch (IllegalArgumentException | IOException e) {
                e.printStackTrace();
                System.err.println("Failed to decode message: " + message);
            }
        });
    }

    public static void setScreenSharing(boolean isScreenSharing) {
        RoomParticipantPanel.isScreenSharing = isScreenSharing;
        if (isScreenSharing) {
            screenSharePanel.setVisible(true);
            videoPanel.setBounds(0, 60, 320, 240);
            screenSharePanel.setBounds(0, 60, 800, 480);
            layeredPane.setLayer(videoPanel, JLayeredPane.PALETTE_LAYER);
            layeredPane.setLayer(screenSharePanel, JLayeredPane.DEFAULT_LAYER);
            screenSharePanel.add(controlPanel, BorderLayout.SOUTH);
        } else {
            screenSharePanel.setVisible(false);
            videoPanel.setBounds(0, 60, 800, 480);
            layeredPane.setLayer(videoPanel, JLayeredPane.DEFAULT_LAYER);
            layeredPane.setLayer(screenSharePanel, JLayeredPane.PALETTE_LAYER);
            videoPanel.add(controlPanel, BorderLayout.SOUTH);
        }
        screenSharePanel.revalidate();
        screenSharePanel.repaint();
        videoPanel.revalidate();
        videoPanel.repaint();
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
            if (multicastSocket != null) {
                try {
                    multicastSocket.leaveGroup(multicastGroup);
                    multicastSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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

    public static void setMulticastSocket(MulticastSocket multicastSocket) {
        RoomOwnerPanel.multicastSocket = multicastSocket;
    }

    public static void setMulticastGroup(InetAddress multicastGroup) {
        RoomOwnerPanel.multicastGroup = multicastGroup;
    }
}