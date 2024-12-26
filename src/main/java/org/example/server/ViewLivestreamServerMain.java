package org.example.server;

import javax.swing.*;
import java.awt.*;

public class ViewLivestreamServerMain extends JFrame {


    private JTextArea logArea;


    public ViewLivestreamServerMain() {
        initComponents();
        addLogArea();
    }


    private void addLogArea() {
        logArea = new JTextArea(20, 50);
        logArea.setEditable(false);
        logArea.setBorder(BorderFactory.createEmptyBorder());
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);


        JScrollPane scrollPane = new JScrollPane(logArea);
        panel_log.setLayout(new BorderLayout());
        panel_log.add(scrollPane, BorderLayout.CENTER);


        panel_log.revalidate();
        panel_log.repaint();
    }


    public void updateLog(String message) {
        logArea.append(message + "\n");
    }




    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jtfUsername = new JLabel();
        jLabel6 = new JLabel();
        jbtManageStream = new JButton();
        jbtManageUser = new JButton();
        jbtManageStream1 = new JButton();
        jPanel3 = new JPanel();
        jlbInfor = new JLabel();
        searchField = new JTextField();
        jbtSearch = new JButton();
        jbtLogout = new JButton();
        jPanel4 = new JPanel();
        jPanel5 = new JPanel();
        jScrollPane1 = new JScrollPane();
        panel_log = new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jPanel2.setBackground(new Color(118, 204, 145));

        jLabel2.setIcon(new ImageIcon(getClass().getResource("/logo32.png"))); // NOI18N

        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/icons8-computer-100.png"))); // NOI18N
        jLabel3.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel3.setMaximumSize(new Dimension(38, 32));
        jLabel3.setPreferredSize(new Dimension(38, 32));

        jLabel4.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setText("Welcome,");

        jtfUsername.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        jtfUsername.setForeground(new Color(255, 255, 255));
        jtfUsername.setHorizontalAlignment(SwingConstants.CENTER);
        jtfUsername.setText("ADMIN");

        jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel6.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\bg1.png")); // NOI18N

        jbtManageStream.setBackground(new Color(138, 217, 163));
        jbtManageStream.setFont(new Font("Segoe UI", 1, 16)); // NOI18N
        jbtManageStream.setForeground(new Color(255, 255, 255));
        jbtManageStream.setIcon(new ImageIcon(getClass().getResource("/ICON2/stream.png"))); // NOI18N
        jbtManageStream.setText("Manage stream");
        jbtManageStream.setHorizontalAlignment(SwingConstants.LEFT);
        jbtManageStream.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtManageStreamActionPerformed(evt);
            }
        });

        jbtManageUser.setBackground(new Color(138, 217, 163));
        jbtManageUser.setFont(new Font("Segoe UI", 1, 16)); // NOI18N
        jbtManageUser.setForeground(new Color(255, 255, 255));
        jbtManageUser.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON2\\follow.png")); // NOI18N
        jbtManageUser.setText("Manage user");
        jbtManageUser.setHorizontalAlignment(SwingConstants.LEFT);
        jbtManageUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtManageUserActionPerformed(evt);
            }
        });

        jbtManageStream1.setBackground(new Color(138, 217, 163));
        jbtManageStream1.setFont(new Font("Segoe UI", 1, 16)); // NOI18N
        jbtManageStream1.setForeground(new Color(255, 255, 255));
        jbtManageStream1.setIcon(new ImageIcon(getClass().getResource("/ICON2/setting.png"))); // NOI18N
        jbtManageStream1.setText("Setting");
        jbtManageStream1.setHorizontalAlignment(SwingConstants.LEFT);
        jbtManageStream1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtManageStream1ActionPerformed(evt);
            }
        });

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfUsername, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtManageUser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtManageStream, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtManageStream1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jtfUsername, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jbtManageUser, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtManageStream, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtManageStream1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new Color(118, 204, 145));

        jlbInfor.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jlbInfor.setForeground(new Color(255, 255, 255));
        jlbInfor.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\home2.png")); // NOI18N
        jlbInfor.setText("SERVER");

        searchField.setBackground(new Color(242, 242, 242));
        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });

        jbtSearch.setBackground(new Color(138, 217, 163));
        jbtSearch.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jbtSearch.setForeground(new Color(255, 255, 255));
        jbtSearch.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\search-icon-24.png")); // NOI18N
        jbtSearch.setText("Search");
        jbtSearch.setHorizontalAlignment(SwingConstants.LEFT);
        jbtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSearchActionPerformed(evt);
            }
        });

        jbtLogout.setBackground(new Color(138, 217, 163));
        jbtLogout.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jbtLogout.setForeground(new Color(255, 255, 255));
        jbtLogout.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\Login-icon-16.png")); // NOI18N
        jbtLogout.setText("CLOSE SERVER");
        jbtLogout.setHorizontalAlignment(SwingConstants.TRAILING);
        jbtLogout.setHorizontalTextPosition(SwingConstants.LEFT);
        jbtLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtLogoutActionPerformed(evt);
            }
        });

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbInfor, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchField, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtSearch, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jbtLogout)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbInfor, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(jbtSearch, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtLogout))
                        .addGap(9, 9, 9))
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(searchField)
                        .addContainerGap())))
        );

        jPanel4.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jPanel5.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        GroupLayout panel_logLayout = new GroupLayout(panel_log);
        panel_log.setLayout(panel_logLayout);
        panel_logLayout.setHorizontalGroup(
            panel_logLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 659, Short.MAX_VALUE)
        );
        panel_logLayout.setVerticalGroup(
            panel_logLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panel_log);

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
      
    }//GEN-LAST:event_searchFieldActionPerformed

    private void jbtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSearchActionPerformed

    }//GEN-LAST:event_jbtSearchActionPerformed

    private void jbtLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtLogoutActionPerformed
    
    }//GEN-LAST:event_jbtLogoutActionPerformed

    private void jbtManageStreamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtManageStreamActionPerformed
        
    }//GEN-LAST:event_jbtManageStreamActionPerformed

    private void jbtManageStream1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtManageStream1ActionPerformed
       
    }//GEN-LAST:event_jbtManageStream1ActionPerformed

    private void jbtManageUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtManageUserActionPerformed
      
    }//GEN-LAST:event_jbtManageUserActionPerformed

    public static void main(String args[]) {
     
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewLivestreamServerMain().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JScrollPane jScrollPane1;
    private JButton jbtLogout;
    private JButton jbtManageStream;
    private JButton jbtManageStream1;
    private JButton jbtManageUser;
    private JButton jbtSearch;
    private JLabel jlbInfor;
    private JLabel jtfUsername;
    private JPanel panel_log;
    private JTextField searchField;
    // End of variables declaration//GEN-END:variables
}
