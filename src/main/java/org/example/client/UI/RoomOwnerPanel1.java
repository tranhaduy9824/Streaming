package org.example.client.UI;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;
import org.example.client.LivestreamClientJFrame;
import org.example.client.UI.components.UIUtils;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


public class RoomOwnerPanel1 extends JPanel {

    public static StyledDocument doc;
    public static JPanel videoPanel;
    public static JPanel screenSharePanel;
    
    public static FrameGrabber grabber;
    public static Java2DFrameConverter converter;
    public static BufferedImage currentImage;
    public static BufferedImage screenImage;
    public static boolean isCameraOn = true;
    public static boolean isMicOn = true;
    public static boolean isScreenSharing = false;
    public static TargetDataLine microphone;
    public static AudioFormat audioFormat;
    public static boolean running = true;
    public static List<String> participants;
    public static MulticastSocket multicastSocket;
    public static InetAddress multicastGroup;
    
    public RoomOwnerPanel1() {
        initComponents();
        
        participants = new ArrayList<>();
        //--------------------videoPanel---------------------------------
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
        };//326,751
        videoPanel.setPreferredSize(new Dimension(751,326));
        videoPanel.setBackground(UIUtils.COLOR_BACKGROUND_MAIN);
        videoPanel.setBounds(0, 0, 751, 326);
        layeredPane.add(videoPanel, JLayeredPane.DEFAULT_LAYER);
                
        //--------------------screenSharePanel---------------------------------
        screenSharePanel = new JPanel(new BorderLayout()){
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
        screenSharePanel.setPreferredSize(new Dimension(751,326));
        screenSharePanel.setBackground(UIUtils.COLOR_BACKGROUND_MAIN);
        screenSharePanel.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
        screenSharePanel.setVisible(false);
        layeredPane.add(screenSharePanel, JLayeredPane.PALETTE_LAYER);
           
        //---------------------participantsLabel--------------------------------
        participantsLabel.setText("0 view");
        participantsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showParticipantsDialog();
            }
        });  
        //--------------controlPanel---------------------------------------
        
                ImageIcon cameraOnIcon = resizeIcon(
                        new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("video-camera-alt.png"))),20, 20);
                ImageIcon cameraOffIcon = resizeIcon(
                        new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("video-slash.png"))), 20,20);
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
        
         //--------------commentPane---------------------------------------
        commentPane.setEditable(false);
        doc = commentPane.getStyledDocument();
        sendButton.addActionListener(new SendCommentActionListener());
        //------------------Close room-----------------------------------------
        closeRoomButton.addActionListener(new CloseRoomActionListener());
        //------------------------------------------------------------
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
    
    public void updateParticipantsList(List<String> newParticipants) {
        participants.clear();
        participants.addAll(newParticipants);
        updateParticipantsCount(participants.size());
    }
    
    private void shareScreen() {
        isScreenSharing = !isScreenSharing;
        screenSharePanel.setVisible(isScreenSharing);
        if (isScreenSharing) {
            videoPanel.setBounds(0, 0, 220, 140);
            screenSharePanel.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
            layeredPane.setLayer(videoPanel, JLayeredPane.PALETTE_LAYER);
            layeredPane.setLayer(screenSharePanel, JLayeredPane.DEFAULT_LAYER);
//            screenSharePanel.add(controlPanel, BorderLayout.SOUTH);
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
            videoPanel.setBounds(0, 0, 751, 326);
            layeredPane.setLayer(videoPanel, JLayeredPane.DEFAULT_LAYER);
            layeredPane.setLayer(screenSharePanel, JLayeredPane.PALETTE_LAYER);
//            videoPanel.add(controlPanel, BorderLayout.SOUTH);
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
                    grabber = new VideoInputFrameGrabber(1);//set cam
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
        button.setBackground(new Color(135,210,239));
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
                button.setBackground(new Color(135,210,239));
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
                        screenImage = image;
                        screenSharePanel.repaint();
                    }
                } else if (message.equals("SCREEN_SHARE_START")) {
                    RoomOwnerPanel1.setScreenSharing(true);
                } else if (message.equals("SCREEN_SHARE_STOP")) {
                    RoomOwnerPanel1.setScreenSharing(false);
                } else {
                    byte[] imageBytes = Base64.getDecoder().decode(message);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                    if (image != null) {
                        currentImage = image;
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
        
        RoomOwnerPanel1.isScreenSharing = isScreenSharing;
        if (isScreenSharing) {
            screenSharePanel.setVisible(true);
            videoPanel.setBounds(0, 0, 220, 140);
            screenSharePanel.setBounds(0, 0,571 ,326);//571 326
             
//            layeredPane.setLayer(videoPanel, JLayeredPane.PALETTE_LAYER);
//            layeredPane.setLayer(screenSharePanel, JLayeredPane.DEFAULT_LAYER);
//            screenSharePanel.add(controlPanel, BorderLayout.SOUTH);
        } else {
            screenSharePanel.setVisible(false);
            videoPanel.setBounds(0, 0, 751, 326);
//            layeredPane.setLayer(videoPanel, JLayeredPane.DEFAULT_LAYER);
//            layeredPane.setLayer(screenSharePanel, JLayeredPane.PALETTE_LAYER);
//            videoPanel.add(controlPanel, BorderLayout.SOUTH);
        }
        screenSharePanel.revalidate();
        screenSharePanel.repaint();
        videoPanel.revalidate();
        videoPanel.repaint();
    }
    
        private void startVideoStream() {
        new Thread(() -> {
            try {
                grabber = new VideoInputFrameGrabber(1);//Setup camera to default
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
                 JOptionPane.showMessageDialog(this, "Error: Could not setup video device. Please check if the device is available and not in use by another application.", "Video Device Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }
        
        private void sendMessage(String message) {
            try {
                if (multicastSocket == null || multicastSocket.isClosed()) {
                    System.err.println("Socket is closed or not initialized");
                    return;
                }
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, multicastGroup, LivestreamClientJFrame.getCurrentMulticastPort());
                multicastSocket.send(packet);
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
                LivestreamClientJFrame.sendComment(comment);
                addComment("You: " + comment, true);
                commentField.setText("");
            }
        }
    }
        
    private class CloseRoomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LivestreamClientJFrame.closeRoom();
            System.err.println("Close room 494");
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
        RoomOwnerPanel1.multicastSocket = multicastSocket;
    }

    public static void setMulticastGroup(InetAddress multicastGroup) {
        RoomOwnerPanel1.multicastGroup = multicastGroup;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new JPanel();
        videoMainPanel = new JPanel();
        layeredPane = new JLayeredPane();
        jPanel3 = new JPanel();
        jLabel5 = new JLabel();
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        participantsLabel = new JLabel();
        jLabel4 = new JLabel();
        jProgressBar1 = new JProgressBar();
        closeRoomButton = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jPanel5 = new JPanel();
        jLabel7 = new JLabel();
        commentScrollPane = new JScrollPane();
        commentPane = new JTextPane();
        commentField = new JTextField();
        sendButton = new JButton();
        controlPanel = new JPanel();

        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        videoMainPanel.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        layeredPane.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        GroupLayout layeredPaneLayout = new GroupLayout(layeredPane);
        layeredPane.setLayout(layeredPaneLayout);
        layeredPaneLayout.setHorizontalGroup(
            layeredPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        layeredPaneLayout.setVerticalGroup(
            layeredPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );

        GroupLayout videoMainPanelLayout = new GroupLayout(videoMainPanel);
        videoMainPanel.setLayout(videoMainPanelLayout);
        videoMainPanelLayout.setHorizontalGroup(
            videoMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane)
        );
        videoMainPanelLayout.setVerticalGroup(
            videoMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane)
        );

        jPanel3.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)));

        jLabel5.setText("'Status'");

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(videoMainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(videoMainPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\user32.png")); // NOI18N
        jLabel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jLabel2.setText("'username' - 4 minute ago");

        participantsLabel.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        participantsLabel.setText("15 view");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel4.setText("'number of like'");

        jProgressBar1.setBackground(new Color(0, 0, 0));

        closeRoomButton.setBackground(new Color(135, 210, 239));
        closeRoomButton.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        closeRoomButton.setForeground(new Color(255, 255, 255));
        closeRoomButton.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON2\\close32.png")); // NOI18N
        closeRoomButton.setText("Close room");
        closeRoomButton.setToolTipText("");
        closeRoomButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeRoomButton.setHorizontalAlignment(SwingConstants.RIGHT);
        closeRoomButton.setHorizontalTextPosition(SwingConstants.LEFT);

        jButton2.setBackground(new Color(241, 167, 99));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jButton2.setForeground(new Color(255, 255, 255));
        jButton2.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON2\\share32.png")); // NOI18N
        jButton2.setText("Share");
        jButton2.setHideActionText(true);
        jButton2.setHorizontalAlignment(SwingConstants.RIGHT);
        jButton2.setHorizontalTextPosition(SwingConstants.LEFT);

        jButton3.setBackground(new Color(255, 213, 46));
        jButton3.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON2\\warning32.png")); // NOI18N

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(participantsLabel, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar1, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeRoomButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jProgressBar1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
                            .addComponent(participantsLabel)))
                    .addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                        .addComponent(closeRoomButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel5.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel7.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\chat-16.png")); // NOI18N
        jLabel7.setText("Chat live");

        commentScrollPane.setViewportBorder(BorderFactory.createLineBorder(new Color(153, 153, 153)));
        commentScrollPane.setViewportView(commentPane);

        sendButton.setBackground(new Color(138, 217, 163));

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(commentScrollPane)
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(commentField)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commentScrollPane)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(commentField, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(sendButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        controlPanel.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                    .addComponent(jPanel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton closeRoomButton;
    private JTextField commentField;
    private JTextPane commentPane;
    private JScrollPane commentScrollPane;
    private JPanel controlPanel;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel7;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel5;
    private JProgressBar jProgressBar1;
    private JLayeredPane layeredPane;
    private JLabel participantsLabel;
    private JButton sendButton;
    private JPanel videoMainPanel;
    // End of variables declaration//GEN-END:variables
}
