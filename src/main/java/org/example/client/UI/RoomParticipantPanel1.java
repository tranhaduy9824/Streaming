package org.example.client.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.example.client.LivestreamClientJFrame;
import org.example.client.UI.components.UIUtils;
import org.example.config.ServerConfig;
import org.example.utils.Constants;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;


public class RoomParticipantPanel1 extends JPanel {
    private StyledDocument doc;
    public static JPanel videoPanel;
    public static JPanel screenSharePanel;
    private WebSocketClient client;
    private BufferedImage videoImage;
    private BufferedImage screenShareImage;
    private boolean isScreenSharing;
    public static List<String> participants;
    public static boolean isVolumeOn = true;
 
    public RoomParticipantPanel1() {
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
                if (videoImage != null) {
                    g.drawImage(videoImage, 0, 0, getWidth(), getHeight(), null);
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
                if (screenShareImage != null) {
                    g.drawImage(screenShareImage, 0, 0, getWidth(), getHeight(), null);
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
        participantsLabel.setForeground(UIUtils.OFFWHITE);
        participantsLabel.setFont(UIUtils.FONT_GENERAL_UI);

 
        //--------------controlPanel---------------------------------------
        ImageIcon volumeOnIcon = resizeIcon(
            new ImageIcon(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("volumeOn.png"))),20, 20);
        
        ImageIcon volumeOffIcon = resizeIcon(
            new ImageIcon(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("volumeOff.png"))), 20,20);
        
        JButton toggleVolumeButton = new JButton(volumeOnIcon);
        styleButton(toggleVolumeButton);
        toggleVolumeButton.addActionListener(e -> {
            isVolumeOn = !isVolumeOn;
            toggleVolumeButton.setIcon(isVolumeOn ? volumeOnIcon : volumeOffIcon);
            toggleVolume(isVolumeOn);
        });
        
        controlPanel.add(toggleVolumeButton);
        
        ImageIcon leaveRomIcon = resizeIcon(
            new ImageIcon(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("leave.png"))),20, 20);
        JButton leaveRoomButton = new JButton(leaveRomIcon);
        styleButton(leaveRoomButton);
        leaveRoomButton.addActionListener(new LeaveRoomActionListener());
        controlPanel.add(leaveRoomButton);
        
        //--------------commentPane---------------------------------------
        commentPane.setEditable(false);
        doc = commentPane.getStyledDocument();
        sendButton.addActionListener(new SendCommentActionListener());
        // --------------------------------------------------------------------
        connectWebSocket();
    }
    
    private void toggleVolume(boolean isOn) {
         
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
                    SwingUtilities.invokeLater(() -> {
                        try {
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

    private void setScreenSharing(boolean isScreenSharing) {
        this.isScreenSharing = isScreenSharing;
        if (isScreenSharing) {
            screenSharePanel.setVisible(true);
            videoPanel.setBounds(0, 0, 220, 140);
            screenSharePanel.setBounds(0, 0, 571, 326);
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
        
    private class SendCommentActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comment = commentField.getText();
            if (!comment.trim().isEmpty()) {
                LivestreamClientJFrame.sendComment(comment);
                addComment("You: " + comment, false);
                commentField.setText("");
            }
        }
    }
    
        
    private class LeaveRoomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LivestreamClientJFrame.leaveRoom();
            if (client != null) {
                client.close();
            }
        }
    }
        
    public void addComment(String comment, boolean isOwner) {
        try {
            Style style = doc.addStyle("Style", null);
            StyleConstants.setForeground(style, isOwner ? Color.RED : Color.BLACK);
            doc.insertString(doc.getLength(), comment + "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    
    
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        participantsLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jPanel2 = new JPanel();
        videoMainPanel = new JPanel();
        layeredPane = new JLayeredPane();
        jPanel3 = new JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new JPanel();
        jLabel7 = new javax.swing.JLabel();
        commentField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentPane = new javax.swing.JTextPane();
        sendButton = new JButton();
        controlPanel = new JPanel();

        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jPanel1.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\user32.png")); // NOI18N
        jLabel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jLabel2.setText("'username' - 4 minute ago");

        participantsLabel.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        participantsLabel.setText("15 view");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel4.setText("100 like");

        jProgressBar1.setBackground(new Color(0, 0, 0));

        jButton1.setBackground(new Color(135, 210, 239));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jButton1.setForeground(new Color(255, 255, 255));
        jButton1.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON2\\plus32.png")); // NOI18N
        jButton1.setText("Follow");
        jButton1.setToolTipText("");
        jButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jButton2.setBackground(new Color(241, 167, 99));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jButton2.setForeground(new Color(255, 255, 255));
        jButton2.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON2\\share32.png")); // NOI18N
        jButton2.setText("Share");
        jButton2.setHideActionText(true);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jButton3.setBackground(new Color(255, 213, 46));
        jButton3.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON2\\warning32.png")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(participantsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(participantsLabel)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layeredPaneLayout = new javax.swing.GroupLayout(layeredPane);
        layeredPane.setLayout(layeredPaneLayout);
        layeredPaneLayout.setHorizontalGroup(
            layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        layeredPaneLayout.setVerticalGroup(
            layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout videoMainPanelLayout = new javax.swing.GroupLayout(videoMainPanel);
        videoMainPanel.setLayout(videoMainPanelLayout);
        videoMainPanelLayout.setHorizontalGroup(
            videoMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane)
        );
        videoMainPanelLayout.setVerticalGroup(
            videoMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(videoMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(videoMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)));

        jLabel5.setText("Xin chào mọi người đã đến với livestreame của mình");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel5.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\chat-16.png")); // NOI18N
        jLabel7.setText("Chat live");

        jScrollPane1.setViewportView(commentPane);

        sendButton.setBackground(new Color(138, 217, 163));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(commentField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(commentField, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        controlPanel.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField commentField;
    private javax.swing.JTextPane commentPane;
    private JPanel controlPanel;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private JLayeredPane layeredPane;
    private javax.swing.JLabel participantsLabel;
    private JButton sendButton;
    private JPanel videoMainPanel;
    // End of variables declaration//GEN-END:variables
}
