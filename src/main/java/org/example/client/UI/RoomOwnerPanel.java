package org.example.client.UI;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;
import org.example.client.LivestreamClient;
import org.example.client.UI.components.UIUtils;
import org.example.config.ServerConfig;
import org.example.utils.Constants;
import org.java_websocket.handshake.ServerHandshake;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import javax.sound.sampled.*;
import java.util.List;
import java.util.ArrayList;

public class RoomOwnerPanel extends JPanel {
    public static JTextPane commentPane;
    public static JTextField commentField;
    public static StyledDocument doc;
    public static JPanel videoPanel;
    public static JPanel screenSharePanel;
    public static JPanel controlPanel;
    public static FrameGrabber grabber;
    public static Java2DFrameConverter converter;
    public static BufferedImage currentImage;
    public static BufferedImage screenImage;
    public static JLabel participantsLabel;
    public static boolean isCameraOn = true;
    public static boolean isMicOn = true;
    public static boolean isScreenSharing = false;
    public static TargetDataLine microphone;
    public static AudioFormat audioFormat;
    public static boolean running = true;
    public static JLayeredPane layeredPane;
    public static List<String> participants;
    public static MulticastSocket multicastSocket;
    public static InetAddress multicastGroup;

    public RoomOwnerPanel() {
        participants = new ArrayList<>();

        setLayout(new BorderLayout());
        setBackground(UIUtils.COLOR_BACKGROUND);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));
        add(layeredPane, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Room Owner - Live Stream");
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
                if (currentImage != null) {
                    g.drawImage(currentImage, 0, 0, getWidth(), getHeight(), null);
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
                if (screenImage != null) {
                    g.drawImage(screenImage, 0, 0, getWidth(), getHeight(), null);
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
        participantsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showParticipantsDialog();
            }
        });
        participantsPanel.add(iconLabel);
        participantsPanel.add(participantsLabel);

        controlPanel = new JPanel();
        controlPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        ImageIcon cameraOnIcon = resizeIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("video-camera-alt.png"))),
                20, 20);
        ImageIcon cameraOffIcon = resizeIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("video-slash.png"))), 20,
                20);
        ImageIcon micOnIcon = resizeIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("mic.png"))), 20, 20);
        ImageIcon micOffIcon = resizeIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("mute.png"))), 20, 20);
        ImageIcon shareScreenIcon = resizeIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("monitor.png"))), 20, 20);

        JButton toggleCameraButton = new JButton(cameraOnIcon);
        styleButton(toggleCameraButton);
        toggleCameraButton.addActionListener(e -> {
            isCameraOn = !isCameraOn;
            toggleCameraButton.setIcon(isCameraOn ? cameraOnIcon : cameraOffIcon);
            toggleCamera(isCameraOn);
        });
        controlPanel.add(toggleCameraButton);

        JButton toggleMicButton = new JButton(micOnIcon);
        styleButton(toggleMicButton);
        toggleMicButton.addActionListener(e -> {
            isMicOn = !isMicOn;
            toggleMicButton.setIcon(isMicOn ? micOnIcon : micOffIcon);
            toggleMic(isMicOn);
        });
        controlPanel.add(toggleMicButton);

        JButton shareScreenButton = new JButton(shareScreenIcon);
        styleButton(shareScreenButton);
        shareScreenButton.addActionListener(e -> shareScreen());
        controlPanel.add(shareScreenButton);

        controlPanel.setBounds(0, 780, 800, 40);
        videoPanel.setLayout(new BorderLayout());
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

        JButton closeRoomButton = new JButton("Close Room");
        styleButton(closeRoomButton);
        closeRoomButton.addActionListener(new CloseRoomActionListener());
        closeRoomButton.setBounds(0, 0, 150, 40);
        layeredPane.add(closeRoomButton, JLayeredPane.DEFAULT_LAYER);

        videoPanel.add(participantsPanel, BorderLayout.NORTH);

        startVideoStream();
    }

    private void showParticipantsDialog() {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Participants", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 400);
        dialog.setLocationRelativeTo(this);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String participant : participants) {
            listModel.addElement(participant);
        }

        JList<String> participantsList = new JList<>(listModel);
        participantsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        participantsList.setFont(UIUtils.FONT_GENERAL_UI);
        participantsList.setForeground(UIUtils.OFFWHITE);
        participantsList.setBackground(UIUtils.COLOR_BACKGROUND);

        JScrollPane scrollPane = new JScrollPane(participantsList);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton kickButton = new JButton("Kick");
        styleButton(kickButton);
        kickButton.addActionListener(e -> {
            String selectedParticipant = participantsList.getSelectedValue();
            if (selectedParticipant != null) {
                // kickParticipant(selectedParticipant);
                listModel.removeElement(selectedParticipant);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        buttonPanel.add(kickButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // private void kickParticipant(String participant) {
    //     // Implement the logic to kick the participant
    //     // For example, send a message to the server to remove the participant
    //     if (client != null && client.isOpen()) {
    //         client.send("KICK:" + participant);
    //     }
    //     participants.remove(participant);
    //     System.out.println("Kicked participant: " + participant);
    // }

    public void updateParticipantsList(List<String> newParticipants) {
        participants.clear();
        participants.addAll(newParticipants);
        updateParticipantsCount(participants.size());
    }

    private void shareScreen() {
        isScreenSharing = !isScreenSharing;
        screenSharePanel.setVisible(isScreenSharing);
        if (isScreenSharing) {
            videoPanel.setBounds(0, 60, 320, 240);
            screenSharePanel.setBounds(0, 60, 800, 480);
            layeredPane.setLayer(videoPanel, JLayeredPane.PALETTE_LAYER);
            layeredPane.setLayer(screenSharePanel, JLayeredPane.DEFAULT_LAYER);
            screenSharePanel.add(controlPanel, BorderLayout.SOUTH);
            new Thread(() -> {
                try {
                    Robot robot = new Robot();
                    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    while (isScreenSharing && running) {
                        screenImage = robot.createScreenCapture(screenRect);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(screenImage, "jpg", baos);
                        byte[] imageBytes = baos.toByteArray();
                        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
                        sendMessage("SCREEN_SHARE:" + encodedImage);
                        SwingUtilities.invokeLater(() -> screenSharePanel.repaint());
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            sendMessage("SCREEN_SHARE_START");
            System.out.println("Screen sharing started");
        } else {
            isScreenSharing = false;
            screenImage = null;
            videoPanel.setBounds(0, 60, 800, 480);
            layeredPane.setLayer(videoPanel, JLayeredPane.DEFAULT_LAYER);
            layeredPane.setLayer(screenSharePanel, JLayeredPane.PALETTE_LAYER);
            videoPanel.add(controlPanel, BorderLayout.SOUTH);
            this.screenSharePanel.repaint();
            sendMessage("SCREEN_SHARE_STOP");
            System.out.println("Screen sharing stopped");
        }
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    private void toggleCamera(boolean isOn) {
        isCameraOn = isOn;
        if (isCameraOn) {
            try {
                if (grabber == null) {
                    grabber = new VideoInputFrameGrabber(1);
                    grabber.start();
                } else {
                    grabber.restart();
                }
                System.out.println("Camera turned on");
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (grabber != null) {
                    grabber.stop();
                    grabber.release();
                    grabber = null;
                }
                currentImage = null;
                videoPanel.repaint();
                System.out.println("Camera turned off");
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void toggleMic(boolean isOn) {
        isMicOn = isOn;
        if (isMicOn) {
            try {
                if (microphone == null) {
                    audioFormat = new AudioFormat(44100, 16, 2, true, true);
                    DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
                    microphone = (TargetDataLine) AudioSystem.getLine(info);
                    microphone.open(audioFormat);
                    microphone.start();
                    new Thread(() -> {
                        byte[] buffer = new byte[4096];
                        while (isMicOn && running) {
                            int bytesRead = microphone.read(buffer, 0, buffer.length);
                            if (bytesRead > 0) {
                                String encodedAudio = Base64.getEncoder().encodeToString(buffer);
                                sendMessage("AUDIO:" + encodedAudio);
                            }
                        }
                    }).start();
                }
                System.out.println("Microphone turned on");
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        } else {
            if (microphone != null) {
                microphone.stop();
                microphone.close();
                microphone = null;
            }
            System.out.println("Microphone turned off");
        }
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

    private void startVideoStream() {
        new Thread(() -> {
            try {
                grabber = new VideoInputFrameGrabber(1);
                grabber.start();
                converter = new Java2DFrameConverter();
                while (running) {
                    if (isCameraOn && grabber != null) {
                        try {
                            Frame frame = grabber.grab();
                            if (frame != null) {
                                currentImage = converter.convert(frame);
                                videoPanel.repaint();

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                ImageIO.write(currentImage, "jpg", baos);
                                byte[] imageBytes = baos.toByteArray();
                                String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
                                sendMessage(encodedImage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendMessage(String message) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, multicastGroup, LivestreamClient.getCurrentMulticastPort());
            multicastSocket.send(packet);
            System.out.println("Sent message: " + message + " to " + multicastGroup.getHostAddress() + ":" + LivestreamClient.getCurrentMulticastPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopAllStreams() {
        running = false;
        if (grabber != null) {
            try {
                grabber.stop();
                grabber.release();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            grabber = null;
        }
        if (microphone != null) {
            microphone.stop();
            microphone.close();
            microphone = null;
        }
        isScreenSharing = false;
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
            LivestreamClient.closeRoom();
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