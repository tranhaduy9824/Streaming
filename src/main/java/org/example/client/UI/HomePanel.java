package org.example.client.UI;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.SwingUtilities;
import org.example.client.LivestreamClientJFrame;
import org.example.client.UI.items.VideoItemPanel;
import org.example.config.ClientConfig;

public class HomePanel extends JPanel {
    private final LivestreamClientJFrame parentForm;
    private final JPanel videoListPanel;
    private final JScrollPane jScrollPaneHome;

    public HomePanel(LivestreamClientJFrame parentForm) {
        this.parentForm = parentForm;
        this.videoListPanel = new JPanel();
        this.jScrollPaneHome = new JScrollPane(videoListPanel);
        initComponents();

        // Bắt đầu luồng lắng nghe thông điệp
//        new Thread(this::listenForBroadcastMessages).start();
    }

    private void initComponents() {
        // Thiết lập layout của videoListPanel
        videoListPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        videoListPanel.setPreferredSize(new Dimension(800, 500)); // Chiều cao cố định

        // Cấu hình JScrollPane
        jScrollPaneHome.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Thiết lập layout của HomePanel và thêm JScrollPane
        setLayout(new BorderLayout());
        add(jScrollPaneHome, BorderLayout.CENTER);
    }

//    private void listenForBroadcastMessages() {
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
//                if (message.startsWith("ROOM_LIST:")) {
//                    String roomList = message.substring(10);
//                    SwingUtilities.invokeLater(() -> updateRoomList(roomList));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

 public void updateRoomList(String roomList) {
        // Xóa danh sách cũ
        videoListPanel.removeAll();

        // Parse dữ liệu room
        String[] rooms = roomList.split(",");
        for (String room : rooms) {
            String[] roomDetails = room.split("\\|");
            
            if (roomDetails.length == 7) {
                String roomName = roomDetails[0];
                String owner = roomDetails[1];
                String participants = roomDetails[2];// ROOM_LIST:namer|user|view|2|230.108.64.234|5085|title,

                String titleStream = roomDetails[6];
                
                LivestreamClientJFrame.setCurrentMulticastAddress(roomDetails[4]);
                LivestreamClientJFrame.setCurrentMulticastPort(Integer.parseInt(roomDetails[5]));
                
                
                //1221 nameRoom
                //|3    nameUser
                //|1    view
                //|6    idU
                //|228.155.139.183 udp
                //|5208 port
                // Tạo VideoItemPanel //ò|3|1|6|235.184.185.115|5919|vãi cc
                VideoItemPanel videoItem = new VideoItemPanel(
                    titleStream, // Tiêu đề của stream
                    owner + " (" + participants + " participants)",
                    new ImageIcon(getClass().getResource("/PhotoCover/1.png")),
                    new ImageIcon(getClass().getResource("/ICON/user32.png")),
                    roomName
                );

                // Thêm hành động double-click vào phòng
                videoItem.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        if (evt.getClickCount() == 2) { // Double-click
                            parentForm.joinRoom(roomName); // Tham gia phòng
                        }
                    }
                });

                // Thêm videoItem vào panel
                videoListPanel.add(videoItem);
                System.out.println("R add--"+roomList);
            } else {
                System.err.println("Invalid room details: " + room);
            }
        }

        // Làm mới giao diện
        videoListPanel.revalidate();
        videoListPanel.repaint();
    }
}
