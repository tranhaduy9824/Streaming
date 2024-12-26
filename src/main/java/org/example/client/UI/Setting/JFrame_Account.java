package org.example.client.UI.Setting;


import org.example.client.UI.components.Helper.ImageHelper;
import org.example.client.UI.components.Helper.MessageDialogHelper;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import javax.swing.filechooser.FileFilter;
import org.example.client.LivestreamClientJFrame;
import org.example.dao.UserDAO;
import org.example.server.model.User;

public class JFrame_Account extends JFrame {
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


    private void initComponents() {

        buttonGroup1 = new ButtonGroup();
        jPanel1 = new JPanel();
        jLabel13 = new JLabel();
        jLabel1 = new JLabel();
        jLabel12 = new JLabel();
        jPanel4 = new JPanel();
        jLabel2 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        jtf_Users = new JTextField();
        jtf_Name = new JTextField();
        jtf_CCCD = new JTextField();
        jtf_Birthday = new JTextField();
        jtf_Email = new JTextField();
        jLabel14 = new JLabel();
        jtf_SDT = new JTextField();
        jPanel2 = new JPanel();
        jbt_Update = new JButton();
        jbt_ChangePass = new JButton();
        jbt_Delete = new JButton();
        rdbt_Nam = new JRadioButton();
        rdbt_Nu = new JRadioButton();
        jLabel3 = new JLabel();
        jlb_DiaChi = new JTextField();
        jPanel3 = new JPanel();
        jlb_IMG = new JLabel();
        jbt_Img = new JButton();
        jSeparator1 = new JSeparator();
        jSeparator2 = new JSeparator();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel13.setText("Profile photo");

        jLabel1.setBackground(new Color(204, 255, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/ICON/users-social-symbol.png"))); // NOI18N
        jLabel1.setText("  Account information");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setIcon(new ImageIcon(getClass().getResource("/ICON/icons8-ok-16.png"))); // NOI18N
        jLabel12.setText("Information");

        jPanel4.setBackground(new Color(255, 255, 255));
        jPanel4.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
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
        jPanel2.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jbt_Update.setBackground(new Color(153, 255, 153));
        jbt_Update.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbt_Update.setText("Update");
        jbt_Update.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbt_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_UpdateActionPerformed(evt);
            }
        });

        jbt_ChangePass.setBackground(new Color(255, 239, 112));
        jbt_ChangePass.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbt_ChangePass.setText("Change pass");
        jbt_ChangePass.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbt_ChangePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_ChangePassActionPerformed(evt);
            }
        });

        jbt_Delete.setBackground(new Color(255, 102, 102));
        jbt_Delete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbt_Delete.setText("Delete");
        jbt_Delete.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbt_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_DeleteActionPerformed(evt);
            }
        });

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jbt_Update, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbt_Delete, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbt_ChangePass, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(jbt_ChangePass, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addComponent(jbt_Delete, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addComponent(jbt_Update, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
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

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jlb_DiaChi))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jtf_Users, GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtf_Name, GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtf_CCCD, GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtf_Birthday, GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtf_Email, GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtf_SDT)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(rdbt_Nam, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdbt_Nu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jtf_Users, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtf_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtf_CCCD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, GroupLayout.Alignment.TRAILING)
                    .addComponent(jtf_Birthday, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_SDT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(rdbt_Nam)
                    .addComponent(rdbt_Nu))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_Email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jlb_DiaChi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new Color(255, 255, 255));
        jPanel3.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jlb_IMG.setBackground(new Color(204, 204, 204));
        jlb_IMG.setForeground(new Color(204, 204, 204));
        jlb_IMG.setHorizontalAlignment(SwingConstants.CENTER);
        jlb_IMG.setIcon(new ImageIcon(getClass().getResource("/ICON/Actions-document-edit-icon-48.png"))); // NOI18N
        jlb_IMG.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jbt_Img.setBackground(new Color(204, 204, 204));
        jbt_Img.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbt_Img.setIcon(new ImageIcon("D:\\HOC TAP\\HK1 2024 - 2025\\DO AN CO SO 4\\Projects\\StreamingApp\\src\\main\\resources\\ICON\\open-file-icon-16.png")); // NOI18N
        jbt_Img.setText("Tải lên");
        jbt_Img.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbt_Img.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_ImgActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new Color(242, 242, 242));

        jSeparator2.setForeground(new Color(242, 242, 242));

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbt_Img, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(jlb_IMG, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1, GroupLayout.Alignment.TRAILING)
                    .addComponent(jlb_IMG, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(jbt_Img, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))
                    .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
    }// </editor-fold>                        

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        String username = LivestreamClientJFrame.getUsername();
       
        getInformationUser(username);
    }

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

    private void jlb_DiaChiFocusGained(java.awt.event.FocusEvent evt) {                                       
        if(jlb_DiaChi.getText().equals("Vui lòng thêm thông tin!"))
        {
            jlb_DiaChi.setText("");
            jlb_DiaChi.setForeground(new Color(0,0,0));//[204,204,204]
        }
    }

    private void jlb_DiaChiFocusLost(java.awt.event.FocusEvent evt) {
        if(jlb_DiaChi.getText().equals(""))
        {
            jlb_DiaChi.setText("Vui lòng thêm thông tin!");
            jlb_DiaChi.setForeground(new Color(204,204,204));

        }
    }

    private void getInformationUser(String username){
      try {
          UserDAO dao = new UserDAO();
          User us = dao.FindUserByUserName(username);
          if(us != null){

              String sDT = Integer.toString(us.getPhonenumber());
              String cCCD = String.valueOf(us.getCic());
              String dateString =us.getBirthday();
              String quequaString =us.getBirthday();
              String inputPattern = "yyyy-MM-dd";
              String outputPattern = "dd-MM-yyyy";

              SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputPattern);
              SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputPattern);

             jtf_Users.setText(us.getUsername());
             jtf_Name.setText(us.getFullname());

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

              System.out.println("f"+us.getSex());
              if(us.getSex()== true){
                  rdbt_Nam.setSelected(true);
              }else
              if(us.getSex()== false){
                  rdbt_Nu.setSelected(true);
              }
              if(us.getEmail().equals("null")){
                  jtf_Email.setText("Vui lòng thêm thông tin!");
              }else{
                  jtf_Email.setText(us.getEmail());
                  jtf_Email.setForeground(new Color(0,0,0));
              }
              if(us.getAddress().equals("null")){
                  jlb_DiaChi.setText("VUI LÒNG CẬP NHẬT ĐỊA CHỈ!");
              }else{
                  jlb_DiaChi.setText(us.getAddress());
                  jlb_DiaChi.setForeground(new Color(0,0,0));
              }
              if(us.getImage()!= null){
                  try {
                      Image img = ImageHelper.createImageFromByteArray(us.getImage(), "jpg");
                      jlb_IMG.setIcon(new ImageIcon(img));
                      personalImage = us.getImage();
                  } catch (IOException ex) {
                      Logger.getLogger(LivestreamClientJFrame.class.getName()).log(Level.SEVERE, null, ex);
                  }
              } else {
                  personalImage = us.getImage();
                  ImageIcon icon = new ImageIcon(
                          getClass().getResource("/IconPack/Actions-document-edit-icon-48.png"));
                  jlb_IMG.setIcon(icon);
              }

          }else{
              JOptionPane.showMessageDialog(this, "KHÔNG TÌM THẤY!!!");
          }
      }catch (Exception e) {
          JOptionPane.showMessageDialog(this, "LỖI"+e.getMessage());
          e.printStackTrace();
      }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_Account().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ButtonGroup buttonGroup1;
    private JLabel jLabel1;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JButton jbt_ChangePass;
    private JButton jbt_Delete;
    private JButton jbt_Img;
    private JButton jbt_Update;
    private JTextField jlb_DiaChi;
    private JLabel jlb_IMG;
    private JTextField jtf_Birthday;
    private JTextField jtf_CCCD;
    private JTextField jtf_Email;
    private JTextField jtf_Name;
    private JTextField jtf_SDT;
    private JTextField jtf_Users;
    private JRadioButton rdbt_Nam;
    private JRadioButton rdbt_Nu;
    // End of variables declaration//GEN-END:variables
}
