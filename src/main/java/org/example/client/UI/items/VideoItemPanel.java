package org.example.client.UI.items;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.example.client.LivestreamClientJFrame;

public class VideoItemPanel extends JPanel {

     private String roomName;
        
    public VideoItemPanel() {
//        initComponents();
    }


    public VideoItemPanel(String roomName, String username, ImageIcon coverImage, ImageIcon userImage, String titleStream) {
            this.roomName = roomName;
            initComponents();
            setRoomName(roomName);
            setUsername(username);
            jlbPhotoCover.setIcon(coverImage);
            jlbImageUser.setIcon(userImage);
            setStatus(titleStream);
            setPreferredSize(new Dimension(200, 150));

//            // Thêm MouseListener cho sự kiện đúp chuột
//            this.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    if (e.getClickCount() == 2) {
//                        onDoubleClick();
//                    }
//                }
//            });
        }
    
        
    private void onDoubleClick() {
        System.out.println("Joined room: " + roomName);
        // Giả sử LivestreamClientJFrame có một phương thức gọi là joinRoom
        LivestreamClientJFrame.joinRoom(roomName);
    }
    
    public void setRoomName(String roomName) {
        jtfRoomName.setText(roomName);
    }
    public void setStatus(String titleStream) {
        jlbStatus.setText(titleStream);
    }

    public void setUsername(String username) {
        jlbUsername.setText(username);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        jlbPhotoCover = new JLabel();
        jPanel2 = new JPanel();
        jlbImageUser = new JLabel();
        jlbStatus = new JLabel();
        jlbUsername = new JLabel();
        jtfRoomName = new JTextField();

        setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jlbPhotoCover.setHorizontalAlignment(SwingConstants.CENTER);
        jlbPhotoCover.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\ok-icon.png")); // NOI18N

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jlbPhotoCover, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jlbPhotoCover, GroupLayout.PREFERRED_SIZE, 90, Short.MAX_VALUE)
        );

        jPanel2.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jlbImageUser.setHorizontalAlignment(SwingConstants.CENTER);
        jlbImageUser.setIcon(new ImageIcon(getClass().getResource("/ICON/user32.png"))); // NOI18N
        jlbImageUser.setToolTipText("");
        jlbImageUser.setVerticalAlignment(SwingConstants.BOTTOM);
        jlbImageUser.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jlbImageUser.setHorizontalTextPosition(SwingConstants.CENTER);

        jlbStatus.setFont(new Font("Segoe UI", 1, 12)); // NOI18N
        jlbStatus.setText("'status'");

        jlbUsername.setText("'username");

        jtfRoomName.setText("1");
        jtfRoomName.setEditable(false);
        jtfRoomName.setVisible(false);

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jlbImageUser)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jlbStatus, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jlbUsername, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfRoomName, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jlbImageUser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jlbStatus)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtfRoomName, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlbUsername, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JLabel jlbImageUser;
    private JLabel jlbPhotoCover;
    private JLabel jlbStatus;
    private JLabel jlbUsername;
    private JTextField jtfRoomName;
    // End of variables declaration//GEN-END:variables
}
