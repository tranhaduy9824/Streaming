package org.example.client;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.*;
import javax.swing.SwingUtilities;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.example.client.UI.HomePanel;
import org.example.client.UI.MainPanel;
import org.example.client.UI.LiveStreamPanel;
import org.example.client.UI.LoginPanel;
import org.example.client.UI.RegistrationPanel;
import org.example.client.UI.RoomOwnerPanel1;
import org.example.client.UI.RoomParticipantPanel;
import org.example.client.UI.components.Toaster.Toaster;
import org.example.client.backend.Backend;
import org.example.config.ClientConfig;
import org.example.config.ServerConfig;
import org.example.server.model.Room;

public class LivestreamClientJFrame extends JFrame {

    private static HomePanel HomePanel;
    private static MainPanel MainPanel;
    private static LiveStreamPanel liveStreamPanel;
    private static RoomOwnerPanel1 roomOwnerPanel;
    private static RoomParticipantPanel roomParticipantPanel;
    //private static JTabbedPane tapMain;
    private static JFrame frame2;
    //-----------------------------
    private static String username;
    private static String userId;
    private static String currentRoom;
    //--------------------------
//private static DefaultListModel<String> roomListModel = new DefaultListModel<>();
    private static List<String> roomListModel = new ArrayList<>();

    private static boolean checkRoomOwnerAfterUpdate = false;
    private static Toaster toaster;
    private static LivestreamClientJFrame MainFrame;

    private static String currentMulticastAddress;
    private static int currentMulticastPort;

    public LivestreamClientJFrame() {
        initComponents();
//        this.setResizable(false);
//        MainFrame = new LivestreamClientJFrame(); // Assign MainFrame here
//        MainFrame.setTitle("Livestream Application");
//        MainFrame.setIconImage(new ImageIcon("/LOGO.png").getImage());
//        MainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        MainFrame.setSize(1100, 630); // 960, 552
//        MainFrame.setLocationRelativeTo(null);
//        MainFrame.setVisible(true);
//        new Thread(LivestreamClientJFrame::listenForBroadcastMessages).start();
//            if (HomePanel == null) {
//            HomePanel = new HomePanel();
//            tapMain.addTab("Main", null, HomePanel, "Main Panel");
//        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfUsername = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jbtHome = new javax.swing.JButton();
        jbtCreateRoom = new javax.swing.JButton();
        jbtFollowing = new javax.swing.JButton();
        jbtAccount = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jlbInfor = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        jbtSearch = new javax.swing.JButton();
        jbtLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        tapMain = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(118, 204, 145));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo32.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(118, 204, 145));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-computer-100.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setMaximumSize(new java.awt.Dimension(38, 32));
        jLabel3.setPreferredSize(new java.awt.Dimension(38, 32));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Welcome,");

        jtfUsername.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jtfUsername.setForeground(new java.awt.Color(255, 255, 255));
        jtfUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jtfUsername.setText("example00");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jtfUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jtfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(118, 204, 145));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jbtHome.setBackground(new java.awt.Color(138, 217, 163));
        jbtHome.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jbtHome.setForeground(new java.awt.Color(255, 255, 255));
        jbtHome.setIcon(new javax.swing.ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\home2.png")); // NOI18N
        jbtHome.setText("Home");
        jbtHome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jbtHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtHomeActionPerformed(evt);
            }
        });

        jbtCreateRoom.setBackground(new java.awt.Color(138, 217, 163));
        jbtCreateRoom.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jbtCreateRoom.setForeground(new java.awt.Color(255, 255, 255));
        jbtCreateRoom.setIcon(new javax.swing.ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\home2.png")); // NOI18N
        jbtCreateRoom.setText("Live now");
        jbtCreateRoom.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jbtCreateRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCreateRoomActionPerformed(evt);
            }
        });

        jbtFollowing.setBackground(new java.awt.Color(138, 217, 163));
        jbtFollowing.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jbtFollowing.setForeground(new java.awt.Color(255, 255, 255));
        jbtFollowing.setIcon(new javax.swing.ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\user32.png")); // NOI18N
        jbtFollowing.setText("Following");
        jbtFollowing.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jbtAccount.setBackground(new java.awt.Color(138, 217, 163));
        jbtAccount.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jbtAccount.setForeground(new java.awt.Color(255, 255, 255));
        jbtAccount.setIcon(new javax.swing.ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\head.png")); // NOI18N
        jbtAccount.setText("Account");
        jbtAccount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\bg1.png")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jbtHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jbtCreateRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jbtFollowing, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jbtAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jbtHome, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtCreateRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtFollowing, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(99, 178, 124));

        jlbInfor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbInfor.setForeground(new java.awt.Color(255, 255, 255));
        jlbInfor.setIcon(new javax.swing.ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\home2.png")); // NOI18N
        jlbInfor.setText("Home");

        searchField.setBackground(new java.awt.Color(242, 242, 242));
        searchField.setPreferredSize(new java.awt.Dimension(64, 30));
        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });

        jbtSearch.setBackground(new java.awt.Color(138, 217, 163));
        jbtSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtSearch.setForeground(new java.awt.Color(255, 255, 255));
        jbtSearch.setIcon(new javax.swing.ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\search-icon-24.png")); // NOI18N
        jbtSearch.setText("Search");
        jbtSearch.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jbtSearch.setPreferredSize(new java.awt.Dimension(75, 29));
        jbtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSearchActionPerformed(evt);
            }
        });

        jbtLogout.setBackground(new java.awt.Color(138, 217, 163));
        jbtLogout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtLogout.setForeground(new java.awt.Color(255, 255, 255));
        jbtLogout.setIcon(new javax.swing.ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\Login-icon-16.png")); // NOI18N
        jbtLogout.setText("Logout");
        jbtLogout.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jbtLogout.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jbtLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jlbInfor, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                                .addComponent(jbtLogout)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jlbInfor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(searchField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jbtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jbtLogout))
                                .addContainerGap())
        );

        tapMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tapMain)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(tapMain, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 960, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addContainerGap()))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 558, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed

    private void jbtHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtHomeActionPerformed
//        if(HomePanel == null){
//            HomePanel = new HomePanel();
//        String mockRoomList = "Room1|Alice|10,Room2|Bob|20,Room3|Charlie|30";
//         ImageIcon icon = new ImageIcon(getClass().getResource("/ICON/chat-16.png"));
//           tapMain.addTab("HOME", icon,HomePanel,"HOME");
//           HomePanel.updateRoomList(mockRoomList);
//        }
//         tapMain.setSelectedComponent(HomePanel);
        if (HomePanel == null) {
            HomePanel = new HomePanel(this);

            List<String> videoList = Backend.getVideoList();

            StringBuilder mockRoomList = new StringBuilder();
            for (String room : videoList) {
                mockRoomList.append(room).append(",");
            }

            // Thêm tab và gọi phương thức
            ImageIcon icon = new ImageIcon(getClass().getResource("/ICON/chat-16.png"));
            tapMain.addTab("HOME", icon, HomePanel);
//                   HomePanel.updateRoomList(mockRoomList.toString());
        }
        tapMain.setSelectedComponent(HomePanel);
    }//GEN-LAST:event_jbtHomeActionPerformed

    private static LivestreamClientJFrame instance;
    public static LivestreamClientJFrame getInstance() {
        if (instance == null) {
            instance = new LivestreamClientJFrame();
        }
        return instance;
    }

    private void jbtCreateRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCreateRoomActionPerformed

        try {
            // Tạo JPanel chứa các trường nhập liệu
            JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5)); // GridLayout để sắp xếp gọn gàng
            JLabel lblRoomName = new JLabel("Enter room name:");
            JTextField txtRoomName = new JTextField();
            JLabel lblTitleStream = new JLabel("Enter title:");
            JTextField txtTitleStream = new JTextField();

            panel.add(lblRoomName);
            panel.add(txtRoomName);
            panel.add(lblTitleStream);
            panel.add(txtTitleStream);

            // Hiển thị JOptionPane với JPanel
            int result = JOptionPane.showConfirmDialog(this, panel, "Create Room", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String roomName = txtRoomName.getText().trim();
                String titleStream = txtTitleStream.getText().trim();

                // Kiểm tra dữ liệu đầu vào
                if (roomName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Room name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (titleStream.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Sinh địa chỉ và cổng multicast ngẫu nhiên
                String multicastAddress = generateRandomMulticastAddress();
                int multicastPort = generateRandomMulticastPort();

                // Thiết lập thông tin phòng hiện tại
                LivestreamClientJFrame.setCurrentMulticastAddress(multicastAddress);
                LivestreamClientJFrame.setCurrentMulticastPort(multicastPort);

                // Tạo phòng và thông báo kết quả
                LivestreamClientJFrame.createRoom(roomName, multicastAddress, multicastPort, titleStream);
                JOptionPane.showMessageDialog(this, "Room created successfully:\n" +
                        "Room Name: " + roomName + "\n" +
                        "Title: " + titleStream + "\n" +
                        "Multicast Address: " + multicastAddress + "\n" +
                        "Port: " + multicastPort, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create room. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jbtCreateRoomActionPerformed

    private String generateRandomMulticastAddress() {
        int firstOctet = 224 + (int) (Math.random() * 16);
        int secondOctet = (int) (Math.random() * 256);
        int thirdOctet = (int) (Math.random() * 256);
        int fourthOctet = (int) (Math.random() * 256);
        return firstOctet + "." + secondOctet + "." + thirdOctet + "." + fourthOctet;
    }

    private int generateRandomMulticastPort() {
        return 5000 + (int) (Math.random() * 1000);
    }

    private void jbtLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtLogoutActionPerformed
        MainFrame.dispose();
        showLoginPanel();
    }//GEN-LAST:event_jbtLogoutActionPerformed

    private void jbtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSearchActionPerformed

    }//GEN-LAST:event_jbtSearchActionPerformed

    public static void main(String args[]) {
        frame2 = new JFrame("Livestream Application");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(800, 500);
        frame2.setLayout(new BorderLayout());
        showLoginPanel();
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        new Thread(LivestreamClientJFrame::listenForBroadcastMessages).start();

    }
    public static void showLoginPanel() {
        frame2.dispose();
        frame2 = new JFrame("Livestream Application");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(800, 500);

        frame2.getContentPane().removeAll();
        frame2.add(new LoginPanel(), BorderLayout.CENTER);
        frame2.revalidate();
        frame2.repaint();

        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
    }
    public static void showRegistrationPanel() {

        frame2.getContentPane().removeAll();
        frame2.add(new RegistrationPanel(), BorderLayout.CENTER);
        frame2.revalidate();
        frame2.repaint();

    }

    public static void showMainPanel() {
        if (MainFrame == null) {
            MainFrame = new LivestreamClientJFrame();
            MainFrame.setTitle("Livestream Application");
            MainFrame.setIconImage(new ImageIcon("/LOGO.png").getImage());
            MainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            MainFrame.setSize(1130, 650); // 960, 552
            MainFrame.setLocationRelativeTo(null);
            MainFrame.jtfUsername.setText(LivestreamClientJFrame.getUsername());
        }
        MainFrame.setVisible(true);
        frame2.dispose();

    }
    public static void showLiveStreamPanel() {

    }
    public static void showRoomOwnerPanel() {
        if (roomOwnerPanel == null) {
            roomOwnerPanel = new RoomOwnerPanel1();
            ImageIcon icon = new ImageIcon(MainFrame.getClass().getResource("/ICON/chat-16.png"));
            MainFrame.tapMain.addTab("Room Owner", icon, roomOwnerPanel, "Room Owner");
        }
        MainFrame.tapMain.setSelectedComponent(roomOwnerPanel);
    }

    public static void closeRoomOwnerPanel() {
        if (MainFrame.tapMain != null && roomOwnerPanel != null) {
            int index = MainFrame.tapMain.indexOfComponent(roomOwnerPanel);
            if (index != -1) {
                MainFrame.tapMain.removeTabAt(index); // Xóa tab
                roomOwnerPanel = null; // Đặt giá trị roomOwnerPanel thành null để giải phóng tham chiếu
            }
        }
    }

    public static void showRoomParticipantPanel() {
        if (roomParticipantPanel == null) {
            roomParticipantPanel = new RoomParticipantPanel();
            ImageIcon icon = new ImageIcon(MainFrame.getClass().getResource("/ICON/chat-16.png"));
            MainFrame.tapMain.addTab("Room Participant", icon, roomParticipantPanel, "Room Participant");
        }
        MainFrame.tapMain.setSelectedComponent(roomParticipantPanel);
    }

    public static boolean sendBroadcastMessage(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            InetAddress group = InetAddress.getByName(ClientConfig.BROADCAST_ADDRESS);
            String fullMessage = message + ":" + InetAddress.getLocalHost().getHostAddress();
            byte[] buffer = fullMessage.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, ServerConfig.BROADCAST_PORT);
            socket.send(packet);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
//    public static void listenForBroadcastMessages() {
//        try (DatagramSocket socket = new DatagramSocket(ClientConfig.BROADCAST_PORT)) {
//            byte[] buffer = new byte[1024];
//            System.out.println("Listening for broadcast messages on port " + ClientConfig.BROADCAST_PORT + "...");
//
//            while (true) {
//                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
//                socket.receive(packet);
//                String message = new String(packet.getData(), 0, packet.getLength());
//                System.out.println("Received broadcast message: " + message);
//
//                // Xử lý thông điệp tùy theo loại message
//                if (message.startsWith("ROOM_LIST:")) {
//                    updateRoomList(message.substring(10));
//                } else if (message.startsWith("COMMENT:")) {
//                    handleCommentMessage(message);
//                } else if (message.startsWith("ROOM_CLOSED:")) {
//                    handleRoomClosedMessage(message);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


//    public static void listenForBroadcastMessages() {
//        try (DatagramSocket socket = new DatagramSocket(ClientConfig.BROADCAST_PORT)) {
//            byte[] buffer = new byte[1024];
//            System.out.println("Listening for broadcast messages on port " + ClientConfig.BROADCAST_PORT + "...");
//
//            while (true) {
//                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
//                socket.receive(packet);
//                String message = new String(packet.getData(), 0, packet.getLength());
//                System.out.println("Received broadcast message: " + message);
//
//                // Xử lý thông điệp tùy theo loại message
//                if (message.startsWith("ROOM_LIST:")) {
////                    updateRoomList(message.substring(10));
//                      String roomList = message.substring(10);
//                      SwingUtilities.invokeLater(() -> HomePanel.updateRoomList(roomList));
//                      System.out.println("R--"+roomList);
//                } else if (message.startsWith("COMMENT:")) {
//                    handleCommentMessage(message);
//                } else if (message.startsWith("ROOM_CLOSED:")) {
//                    handleRoomClosedMessage(message);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void listenForBroadcastMessages() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(ClientConfig.BROADCAST_PORT);
            socket.setReuseAddress(true);  // Cho phép tái sử dụng địa chỉ
            byte[] buffer = new byte[1024];
            System.out.println("Listening for broadcast messages on port " + ClientConfig.BROADCAST_PORT + "...");

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received broadcast message: " + message);

                if (message.startsWith("ROOM_LIST:")) {
                    String roomList = message.substring(10);
                    SwingUtilities.invokeLater(() -> HomePanel.updateRoomList(roomList));
                    System.out.println("R--" + roomList);
                } else if (message.startsWith("COMMENT:")) {
                    handleCommentMessage(message);
                } else if (message.startsWith("ROOM_CLOSED:")) {
                    handleRoomClosedMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();  // Đảm bảo đóng socket khi không còn cần thiết
            }
        }
    }


    private static void listenForMulticastMessages(String multicastAddress, int multicastPort) {
        new Thread(() -> {
            try (MulticastSocket socket = new MulticastSocket(multicastPort)) {
                InetAddress group = InetAddress.getByName(multicastAddress);
                socket.joinGroup(group);
                byte[] buffer = new byte[1024];
                System.out.println(
                        "Listening for multicast messages on " + multicastAddress + ":" + multicastPort + "...");

                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Received multicast message: " + message);

                    if (message.startsWith("COMMENT:")) {
                        handleCommentMessage(message);
                    } else if (message.startsWith("ROOM_CLOSED:")) {
                        handleRoomClosedMessage(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void handleCommentMessage(String message) {
        String[] parts = message.split(":");
        if (parts.length == 5) {
            String sender = parts[1];
            String comment = parts[2];
            boolean isOwner = sender.equals(getRoomOwner(currentRoom));
            if (!sender.equals(username) && currentRoom != null && currentRoom.equals(parts[3])) {
                comment = sender + ": " + comment;
                if (liveStreamPanel != null) {
                    liveStreamPanel.addComment(comment, isOwner);
                } else if (roomOwnerPanel != null) {
                    roomOwnerPanel.addComment(comment, isOwner);
                } else if (roomParticipantPanel != null) {
                    roomParticipantPanel.addComment(comment, isOwner);
                }
            }
        }
    }

    private static void handleRoomClosedMessage(String message) {
        String roomName = message.split(":")[1];
        System.out.println("Received room closed message for room: " + roomName);
        if (currentRoom != null && currentRoom.equals(roomName)) {
            toaster.success("The room has been closed by the owner.");
            JOptionPane.showMessageDialog(MainFrame, "The room has been closed by the owner.", "Room Closed",
                    JOptionPane.INFORMATION_MESSAGE);
            leaveRoom();
        }
    }


    private static String getRoomOwner(String roomName) {
        for (int i = 0; i < roomListModel.size(); i++) {
            String roomDetails = roomListModel.get(i);
            if (roomDetails.startsWith(roomName + " (Owner: ")) {
                int startIndex = roomDetails.indexOf("Owner: ") + 7;
                int endIndex = roomDetails.indexOf(", Participants:");
                if (startIndex != -1 && endIndex != -1) {
                    System.out.println("Room owner: " + roomDetails.substring(startIndex, endIndex));
                    return roomDetails.substring(startIndex, endIndex);
                }
            }
        }
        return null;
    }


    private static void updateRoomList(String roomList) {
        SwingUtilities.invokeLater(() -> {
            roomListModel.clear();
            String[] rooms = roomList.split(",");
            for (String room : rooms) {
                if (!room.isEmpty()) {
                    String[] roomDetails = room.split("\\|");
                    if (roomDetails.length == 7) {
                        String roomName = roomDetails[0];
                        String owner = roomDetails[1];
                        String participantCount = roomDetails[3];

                        setCurrentMulticastAddress(roomDetails[4]);
                        setCurrentMulticastPort(Integer.parseInt(roomDetails[5]));

                        String titleStream = roomDetails[6];
                        // videoList.add("1|Alice|10|titleStream");
                        // ROOM_LIST:4|example04|1|1|225.32.242.172|5452|Xin chao mn,

//                        roomListModel.add(
//                                roomName + " (Owner: " + owner + ", Participants: " + participantCount + ")");
                        roomListModel.add(roomName +"|"+owner+"|"+participantCount+"|"+ titleStream);

                        if (currentRoom != null && currentRoom.equals(roomName) && roomOwnerPanel != null) {
                            roomOwnerPanel.updateParticipantsCount(Integer.parseInt(participantCount));
                        } else if (currentRoom != null && currentRoom.equals(roomName)
                                && roomParticipantPanel != null) {
                            roomParticipantPanel.updateParticipantsCount(Integer.parseInt(participantCount));
                        }
                    } else {
                        System.err.println("Invalid room details: " + room);
                    }
                }
            }
            System.out.println("Updated room list: " + roomList);

            if (checkRoomOwnerAfterUpdate) {
                checkRoomOwnerAfterUpdate = false;
                if (isRoomOwner(currentRoom)) {
                    showRoomOwnerPanel();
                } else {
                    showRoomParticipantPanel();
                }
            }

            if (MainFrame.getContentPane().getComponent(0) instanceof MainPanel) {
                HomePanel homePanel = (HomePanel) MainFrame.getContentPane().getComponent(0);
                homePanel.updateRoomList(roomList);
            }
        });
    }

    public static void joinRoom(String roomName) {
        currentRoom = roomName;
        System.out.println("Attempting to join room: " + roomName);
        sendBroadcastMessage("JOIN_ROOM:" + username + ":" + userId + ":" + roomName);
        checkRoomOwnerAfterUpdate = true;
        listenForMulticastMessages(LivestreamClientJFrame.getCurrentMulticastAddress(), LivestreamClientJFrame.getCurrentMulticastPort());
        showRoomParticipantPanel();
    }

    public static void closeRoom() {
        if (currentRoom != null) {
            sendBroadcastMessage("CLOSE_ROOM:" + username + ":" + userId + ":" + currentRoom);
            currentRoom = null;
//            showMainPanel();
            closeRoomOwnerPanel();
            System.err.println("close 740");
            if (roomOwnerPanel != null) {
                roomOwnerPanel.stopAllStreams();
            }
        }
    }

    public static void leaveRoom() {
        if (currentRoom != null) {
            sendBroadcastMessage("LEAVE_ROOM:" + username + ":" + userId + ":" + currentRoom);
            currentRoom = null;
            showMainPanel();
        }
    }


    public static void createRoom(String roomName,String multicastAddress, int multicastPort, String titleStream) {
        setCurrentMulticastPort(multicastPort);
        String message = "CREATE_ROOM:" + username + ":" + userId + ":" + roomName + ":" + multicastAddress + ":" + multicastPort + ":"+ titleStream;
        if (sendBroadcastMessage(message)) {
            System.out.println("Create room request sent successfully for room: " + roomName);
            currentRoom = roomName;
            showRoomOwnerPanel();
        } else {
            toaster.error("Failed to send create room request.");
        }

        Room room = new Room();
        room.setRoomName(roomName);
        room.setOwnerId(Integer.parseInt(userId));
        room.setMulticastAddress(multicastAddress);
        room.setMulticastPort(multicastPort);

        try {
            MulticastSocket multicastSocket = new MulticastSocket(multicastPort);
            InetAddress multicastGroup = InetAddress.getByName(multicastAddress);
            multicastSocket.joinGroup(multicastGroup);
            RoomOwnerPanel1.setMulticastSocket(multicastSocket);
            RoomOwnerPanel1.setMulticastGroup(multicastGroup);
        } catch (IOException e) {
            e.printStackTrace();
        }

        listenForMulticastMessages(multicastAddress, multicastPort);
    }

    public static void sendComment(String comment) {
        if (currentRoom != null) {
            String message = "COMMENT:" + username + ":" + userId + ":" + currentRoom + ":" + comment;
            sendBroadcastMessage(message);
        }
    }


    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        LivestreamClientJFrame.username = username;
    }

    public static void setUserId(String userId) {
        LivestreamClientJFrame.userId = userId;
    }

    //    public static DefaultListModel<String> getRoomListModel() {
//        return roomListModel;
//    }
    public static List<String> getRoomListModel() {
        return roomListModel;
    }

    public static String getCurrentRoom() {
        return currentRoom;
    }

    private static boolean isRoomOwner(String roomName) {
        for (int i = 0; i < roomListModel.size(); i++) {
            String roomDetails = roomListModel.get(i);
            System.out.println("Checking room details: " + roomDetails);
            if (roomDetails.startsWith(roomName + " (Owner: " + username)) {
                System.out.println("User is the owner of the room: " + roomName);
                return true;
            }
        }
        System.out.println("User is not the owner of the room: " + roomName);
        return false;
    }



    public static Room getRoomDetails(String roomName) {
        for (int i = 0; i < roomListModel.size(); i++) {
            String roomDetails = roomListModel.get(i);
            System.out.println("Checking room details: " + roomDetails);
            if (roomDetails.startsWith(roomName + " (Owner: ")) {
                String[] details = roomDetails.split("\\s*\\(Owner:\\s*|,\\s*Participants:\\s*|\\)");
                System.out.println("Room details split into: " + Arrays.toString(details));
                if (details.length >= 3) {
                    String owner = details[1].trim();
                    int participantCount = Integer.parseInt(details[2].trim());

                    String multicastAddress = getCurrentMulticastAddress();
                    int multicastPort = getCurrentMulticastPort();

                    System.out.println("Owner: " + owner);
                    System.out.println("Participant Count: " + participantCount);
                    System.out.println("Multicast Address: " + multicastAddress);
                    System.out.println("Multicast Port: " + multicastPort);

                    Room room = new Room();
                    room.setRoomName(roomName);
                    room.setOwnerId(Integer.parseInt(owner));
                    room.setMulticastAddress(multicastAddress);
                    room.setMulticastPort(multicastPort);

                    return room;
                } else {
                    System.out.println("Room details length is less than expected.");
                }
            }
        }
        System.out.println("Room not found: " + roomName);
        return null;
    }

    public static void setCurrentMulticastAddress(String currentMulticastAddress) {
        LivestreamClientJFrame.currentMulticastAddress = currentMulticastAddress;
    }

    public static void setCurrentMulticastPort(int currentMulticastPort) {
        LivestreamClientJFrame.currentMulticastPort = currentMulticastPort;
    }

    public static int getCurrentMulticastPort() {
        return currentMulticastPort;
    }

    public static String getCurrentMulticastAddress() {
        return currentMulticastAddress;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JButton jbtAccount;
    private javax.swing.JButton jbtCreateRoom;
    private javax.swing.JButton jbtFollowing;
    private javax.swing.JButton jbtHome;
    private javax.swing.JButton jbtLogout;
    private javax.swing.JButton jbtSearch;
    private javax.swing.JLabel jlbInfor;
    private javax.swing.JLabel jtfUsername;
    private javax.swing.JTextField searchField;
    private javax.swing.JTabbedPane tapMain;
    // End of variables declaration//GEN-END:variables
}
