package org.example.client.UI.Setting;


import org.example.client.UI.components.Helper.ImageHelper;
import org.example.client.UI.components.Helper.MessageDialogHelper;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import org.example.client.LivestreamClientJFrame;

public class JFrame_Account extends javax.swing.JFrame {
private byte[] personalImage;
private LivestreamClientJFrame parentForm;
public static JFrame_Account jfAccount;

    public JFrame_Account() {
        initComponents();
        setTitle("Livestream Application");
        this.setIconImage(new ImageIcon("group.png").getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtf_Users = new javax.swing.JTextField();
        jtf_Name = new javax.swing.JTextField();
        jtf_CCCD = new javax.swing.JTextField();
        jtf_Birthday = new javax.swing.JTextField();
        jtf_Email = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jtf_SDT = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jbt_Update = new javax.swing.JButton();
        jbt_ChangePass = new javax.swing.JButton();
        jbt_Delete = new javax.swing.JButton();
        rdbt_Nam = new javax.swing.JRadioButton();
        rdbt_Nu = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jlb_DiaChi = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jlb_IMG = new javax.swing.JLabel();
        jbt_Img = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Profile photo");

        jLabel1.setBackground(new Color(204, 255, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/ICON/users-social-symbol.png"))); // NOI18N
        jLabel1.setText("  Account information");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setIcon(new ImageIcon(getClass().getResource("/ICON/icons8-ok-16.png"))); // NOI18N
        jLabel12.setText("Information");

        jPanel4.setBackground(new Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0)));
        jPanel4.setForeground(new Color(242, 242, 242));

        jLabel2.setText("Full name");

        jLabel4.setText("Username");

        jLabel5.setText("CIC");

        jLabel6.setText("Date of birth");

        jLabel7.setText("Sex");

        jLabel8.setText("Email");

        jtf_Users.setEditable(false);

        jtf_CCCD.setForeground(new Color(204, 204, 204));
        jtf_CCCD.setText("Please add more information!");
        jtf_CCCD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtf_CCCDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtf_CCCDFocusLost(evt);
            }
        });

        jtf_Birthday.setForeground(new Color(204, 204, 204));
        jtf_Birthday.setText("Please add more information!");
        jtf_Birthday.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtf_BirthdayFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtf_BirthdayFocusLost(evt);
            }
        });

        jtf_Email.setForeground(new Color(204, 204, 204));
        jtf_Email.setText("Please add more information!");
        jtf_Email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtf_EmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtf_EmailFocusLost(evt);
            }
        });

        jLabel14.setText("Phone number");

        jtf_SDT.setForeground(new Color(204, 204, 204));
        jtf_SDT.setText("Please add more information!");
        jtf_SDT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtf_SDTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtf_SDTFocusLost(evt);
            }
        });

        jPanel2.setBackground(new Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jbt_Update.setBackground(new Color(153, 255, 153));
        jbt_Update.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbt_Update.setText("Update");
        jbt_Update.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbt_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_UpdateActionPerformed(evt);
            }
        });

        jbt_ChangePass.setBackground(new Color(255, 239, 112));
        jbt_ChangePass.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbt_ChangePass.setText("Change pass");
        jbt_ChangePass.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbt_ChangePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_ChangePassActionPerformed(evt);
            }
        });

        jbt_Delete.setBackground(new Color(255, 102, 102));
        jbt_Delete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbt_Delete.setText("Delete");
        jbt_Delete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbt_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_DeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jbt_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbt_Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbt_ChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jbt_ChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jbt_Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jbt_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        buttonGroup1.add(rdbt_Nam);
        rdbt_Nam.setText("Nam");

        buttonGroup1.add(rdbt_Nu);
        rdbt_Nu.setText("Nữ");

        jLabel3.setText("Address");

        jlb_DiaChi.setForeground(new Color(204, 204, 204));
        jlb_DiaChi.setText("Please add more information!");
        jlb_DiaChi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jlb_DiaChiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jlb_DiaChiFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jlb_DiaChi))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtf_Users, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtf_Name, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtf_CCCD, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtf_Birthday, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtf_Email, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtf_SDT)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(rdbt_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdbt_Nu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jtf_Users, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtf_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtf_CCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtf_Birthday, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_SDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(rdbt_Nam)
                    .addComponent(rdbt_Nu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jlb_DiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jlb_IMG.setBackground(new Color(204, 204, 204));
        jlb_IMG.setForeground(new Color(204, 204, 204));
        jlb_IMG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlb_IMG.setIcon(new ImageIcon(getClass().getResource("/ICON/Actions-document-edit-icon-48.png"))); // NOI18N
        jlb_IMG.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jbt_Img.setBackground(new Color(204, 204, 204));
        jbt_Img.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbt_Img.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\open-file-icon-16.png")); // NOI18N
        jbt_Img.setText("Tải lên");
        jbt_Img.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbt_Img.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_ImgActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new Color(242, 242, 242));

        jSeparator2.setForeground(new Color(242, 242, 242));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbt_Img, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(jlb_IMG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlb_IMG, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(jbt_Img, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
     try {
        processloginSuccessful();
    } catch (IOException ex) {
        Logger.getLogger(JFrame_Account.class.getName()).log(Level.SEVERE, null, ex);
    }




    }//GEN-LAST:event_formWindowOpened

    private void jbt_ImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt_ImgActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".jpg");
                }
            }

            @Override
            public String getDescription() {
                return "Image File (*.jpg)";
            }
        });

        if (chooser.showOpenDialog(JFrame_Account.this) == JFileChooser.CANCEL_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        try {
            ImageIcon icon = new ImageIcon(file.getPath());
            Image img = ImageHelper.resize(icon.getImage(), 100, 100);
            ImageIcon resizedIcon = new ImageIcon(img);
            jlb_IMG.setIcon(resizedIcon);
            personalImage = ImageHelper.toByteArray(img, "jpg");
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showMessageDiaalog(parentForm, e.getMessage(), "LỖI");
        }
    }//GEN-LAST:event_jbt_ImgActionPerformed

    private void jbt_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt_DeleteActionPerformed
        if (JOptionPane.showConfirmDialog(this, "BẠN CÓ MUỐN XÓA TÀI KHOẢN KHÔNG?") == JOptionPane.NO_OPTION) {
            return;
        }
        try {
            String username = jtf_Users.getText();
            Client.closeAllViews();
            Client.openView(Client.View.APPNOTICE, "XÓA TÀI KHOẢN!", "Đang thực hiện");
//            Client.socketHandle.write("delete-user," + username);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "LỖI!" + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_jbt_DeleteActionPerformed

    private void jbt_ChangePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt_ChangePassActionPerformed
        Client.closeViewAccount();
        Client.openView(Client.View.CHANGE_PASSWORD);
    }//GEN-LAST:event_jbt_ChangePassActionPerformed

    private void jbt_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt_UpdateActionPerformed
        if (JOptionPane.showConfirmDialog(this, "BẠN CÓ MUỐN CẬP NHẬT TÀI KHOẢN KHÔNG?") == JOptionPane.NO_OPTION) {
            return;
        }
        try {

            String dateString = jtf_Birthday.getText();
            String quequaString =  jtf_Birthday.getText();
            String inputPattern = "dd-MM-yyyy";
            String outputPattern = "yyyy-MM-dd";
            SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputPattern);
            //------------------------------
            Date date = inputDateFormat.parse(dateString);
            String reversedDate = outputDateFormat.format(date);

            String username = jtf_Users.getText();
            String hoTen = jtf_Name.getText();
            int sdt = Integer.parseInt(jtf_SDT.getText());
            String email = jtf_Email.getText();
            int cccd = Integer.parseInt(jtf_CCCD.getText());

            int gioiTinh = rdbt_Nam.isSelected() ? 1 : 0;
            String diaChi = jlb_DiaChi.getText();

            String encodedImage = null;
            if (personalImage != null && personalImage.length > 0) {
                encodedImage = Base64.getEncoder().encodeToString(personalImage);
            }

            //        Client.closeAllViews();
            Client.closeView(Client.View.APPNOTICE);
            Client.closeView(Client.View.VIEW_ACCOUNT);
            Client.openView(Client.View.APPNOTICE, "ĐANG CẬP NHẬT LẠI THÔNG TIN!", "Đang thực hiện");
//            Client.socketHandle.write("update-user," +  hoTen + "," + sdt + "," + email + "," + cccd + "," + reversedDate + "," + gioiTinh + "," + diaChi + "," + encodedImage + "," + username);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "LỖI!" + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_jbt_UpdateActionPerformed

    private void jtf_SDTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtf_SDTFocusLost
        if(jtf_SDT.getText().equals(""))
        {
            jtf_SDT.setText("Vui lòng thêm thông tin!");
            jtf_SDT.setForeground(new Color(204,204,204));

        }
    }//GEN-LAST:event_jtf_SDTFocusLost

    private void jtf_SDTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtf_SDTFocusGained
        if(jtf_SDT.getText().equals("Vui lòng thêm thông tin!"))
        {
            jtf_SDT.setText("");
            jtf_SDT.setForeground(new Color(0,0,0));//[204,204,204]
        }
    }//GEN-LAST:event_jtf_SDTFocusGained

    private void jtf_EmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtf_EmailFocusLost
        if(jtf_Email.getText().equals(""))
        {
            jtf_Email.setText("Vui lòng thêm thông tin!");
            jtf_Email.setForeground(new Color(204,204,204));

        }

    }//GEN-LAST:event_jtf_EmailFocusLost

    private void jtf_EmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtf_EmailFocusGained
        if(jtf_Email.getText().equals("Vui lòng thêm thông tin!"))
        {
            jtf_Email.setText("");
            jtf_Email.setForeground(new Color(0,0,0));//[204,204,204]
        }

    }//GEN-LAST:event_jtf_EmailFocusGained

    private void jtf_BirthdayFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtf_BirthdayFocusLost
        if(jtf_Birthday.getText().equals(""))
        {
            jtf_Birthday.setText("Vui lòng thêm thông tin!");
            jtf_Birthday.setForeground(new Color(204,204,204));

        }
    }//GEN-LAST:event_jtf_BirthdayFocusLost

    private void jtf_BirthdayFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtf_BirthdayFocusGained
        if(jtf_Birthday.getText().equals("Vui lòng thêm thông tin!"))
        {
            jtf_Birthday.setText("");
            jtf_Birthday.setForeground(new Color(0,0,0));//[204,204,204]
        }
    }//GEN-LAST:event_jtf_BirthdayFocusGained

    private void jtf_CCCDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtf_CCCDFocusLost
        if(jtf_CCCD.getText().equals(""))
        {
            jtf_CCCD.setText("Vui lòng thêm thông tin!");
            jtf_CCCD.setForeground(new Color(204,204,204));

        }

    }//GEN-LAST:event_jtf_CCCDFocusLost

    private void jtf_CCCDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtf_CCCDFocusGained
        if(jtf_CCCD.getText().equals("Vui lòng thêm thông tin!"))
        {
            jtf_CCCD.setText("");
            jtf_CCCD.setForeground(new Color(0,0,0));//[204,204,204]
        }

    }//GEN-LAST:event_jtf_CCCDFocusGained

    private void jlb_DiaChiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jlb_DiaChiFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jlb_DiaChiFocusGained

    private void jlb_DiaChiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jlb_DiaChiFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jlb_DiaChiFocusLost

    
    private void processloginSuccessful () throws IOException{

     String sDT = Integer.toString(Client.user.getPhonenumber());
     //String cCCD = Integer.toString(Client.user.getCCCD());
     String cCCD = String.valueOf(Client.user.getCic());
     String dateString = Client.user.getBirthday();
     String quequaString = Client.user.getBirthday();
     String inputPattern = "yyyy-MM-dd";
     String outputPattern = "dd-MM-yyyy";

        SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputPattern);

       jtf_Users.setText(Client.user.getUsername());
       jtf_Name.setText(Client.user.getFullname());

       if(dateString.equals("null")){
           jtf_Birthday.setText("Vui lòng thêm thông tin!");
        }else{
              try {
                   Date date = inputDateFormat.parse(dateString);
                   String reversedDate = outputDateFormat.format(date);
                   jtf_Birthday.setText(reversedDate);
                   jtf_Birthday.setForeground(new Color(0,0,0));
                 } catch (ParseException e) {
                   e.printStackTrace();
               }
        }


       if(cCCD.equals("0")){
           jtf_CCCD.setText("Vui lòng thêm thông tin!");
       }else{
             jtf_CCCD.setText("0"+cCCD);
             jtf_CCCD.setForeground(new Color(0,0,0));
       }

        if(sDT.equals("0")){
             jtf_SDT.setText("Vui lòng thêm thông tin!");
          }else{
            jtf_SDT.setText("0"+sDT);
            jtf_SDT.setForeground(new Color(0,0,0));
         }

        System.out.println("f"+Client.user.getSex());
        if(Client.user.getSex()== true){
            rdbt_Nam.setSelected(true);
           }else
            if(Client.user.getSex()== false){
             rdbt_Nu.setSelected(true);
        }

       if(Client.user.getEmail().equals("null")){
          jtf_Email.setText("Vui lòng thêm thông tin!");
       }else{
           jtf_Email.setText(Client.user.getEmail());
           jtf_Email.setForeground(new Color(0,0,0));
       }


       if(Client.user.getAddress().equals("null")){
           jlb_DiaChi.setText("VUI LÒNG CẬP NHẬT ĐỊA CHỈ GIAO HÀNG!");
       }else{
           jlb_DiaChi.setText(Client.user.getAddress());
           jlb_DiaChi.setForeground(new Color(0,0,0));
       }

       if(Client.user.getImage()!= null){
          try {
              Image img = ImageHelper.createImageFromByteArray(Client.user.getImage(), "jpg");
              jlb_IMG.setIcon(new ImageIcon(img));
              personalImage = Client.user.getImage();
          } catch (IOException ex) {
              Logger.getLogger(LivestreamClientJFrame.class.getName()).log(Level.SEVERE, null, ex);
          }
       } else {
       personalImage = Client.user.getImage();
        ImageIcon icon = new ImageIcon(
       getClass().getResource("/IconPack/Actions-document-edit-icon-48.png"));
        jlb_IMG.setIcon(icon);
       }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton jbt_ChangePass;
    private javax.swing.JButton jbt_Delete;
    private javax.swing.JButton jbt_Img;
    private javax.swing.JButton jbt_Update;
    private javax.swing.JTextField jlb_DiaChi;
    private javax.swing.JLabel jlb_IMG;
    private javax.swing.JTextField jtf_Birthday;
    private javax.swing.JTextField jtf_CCCD;
    private javax.swing.JTextField jtf_Email;
    private javax.swing.JTextField jtf_Name;
    private javax.swing.JTextField jtf_SDT;
    private javax.swing.JTextField jtf_Users;
    private javax.swing.JRadioButton rdbt_Nam;
    private javax.swing.JRadioButton rdbt_Nu;
    // End of variables declaration//GEN-END:variables
}
