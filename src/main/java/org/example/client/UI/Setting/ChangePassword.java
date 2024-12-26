package org.example.client.UI.Setting;


import org.example.client.UI.components.Helper.MessageDialogHelper;
import org.example.client.UI.Setting.Client;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.example.client.LivestreamClientJFrame;
import org.example.server.model.ShareData;


public class ChangePassword extends java.awt.Dialog {

    public ChangePassword(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
         initComponents();
         setTitle("Livestream Application️");
         this.setIconImage(new ImageIcon("group.png").getImage()); 
 
         this.setResizable(false);
         this.setLocationRelativeTo(null);
         jtf_Users.setText(LivestreamClientJFrame.getUsername());
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtf_Users = new javax.swing.JTextField();
        jpw_MKC = new javax.swing.JPasswordField();
        jpw_M2 = new javax.swing.JPasswordField();
        jpwM1 = new javax.swing.JPasswordField();
        jbt_OK = new javax.swing.JButton();
        jbt_Clear = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jcbb_Show = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(500, 350));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        setLayout(null);

        jtf_Users.setEditable(false);
        add(jtf_Users);
        jtf_Users.setBounds(220, 90, 210, 30);

        jpw_MKC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpw_MKCActionPerformed(evt);
            }
        });
        add(jpw_MKC);
        jpw_MKC.setBounds(220, 130, 210, 30);
        add(jpw_M2);
        jpw_M2.setBounds(220, 210, 210, 30);
        add(jpwM1);
        jpwM1.setBounds(220, 170, 210, 30);

        jbt_OK.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jbt_OK.setIcon(new ImageIcon(getClass().getResource("/ICON/icons8-ok-16.png"))); // NOI18N
        jbt_OK.setText("OK");
        jbt_OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_OKActionPerformed(evt);
            }
        });
        add(jbt_OK);
        jbt_OK.setBounds(120, 270, 100, 40);

        jbt_Clear.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jbt_Clear.setIcon(new ImageIcon(getClass().getResource("/ICON/icons8-eraser-16.png"))); // NOI18N
        jbt_Clear.setText("CLEAR");
        jbt_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_ClearActionPerformed(evt);
            }
        });
        add(jbt_Clear);
        jbt_Clear.setBounds(300, 270, 130, 40);

        jLabel1.setFont(new java.awt.Font("Roboto Bk", 3, 30)); // NOI18N
        jLabel1.setForeground(new Color(51, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("THAY ĐỔI MẬT KHẨU");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel1);
        jLabel1.setBounds(60, 30, 380, 50);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setText("UserName");
        add(jLabel2);
        jLabel2.setBounds(60, 90, 144, 30);

        jcbb_Show.setText("Show ");
        jcbb_Show.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbb_ShowActionPerformed(evt);
            }
        });
        add(jcbb_Show);
        jcbb_Show.setBounds(220, 240, 85, 20);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel3.setText("Mật Khẩu Cũ");
        add(jLabel3);
        jLabel3.setBounds(60, 130, 144, 30);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel4.setText("Mật Khẩu Mới");
        add(jLabel4);
        jLabel4.setBounds(60, 170, 144, 30);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel5.setText("Nhập Lại Mật Khẩu Mới");
        add(jLabel5);
        jLabel5.setBounds(60, 210, 144, 30);

        jLabel6.setIcon(new ImageIcon(getClass().getResource("/pnLogin.png"))); // NOI18N
        add(jLabel6);
        jLabel6.setBounds(0, 10, 490, 370);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void jpw_MKCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpw_MKCActionPerformed
      
    }//GEN-LAST:event_jpw_MKCActionPerformed

    private void jbt_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt_OKActionPerformed
     StringBuilder sb = new StringBuilder();
     String taiKhoan = jtf_Users.getText();
     String matKhau = String.copyValueOf(jpw_MKC.getPassword());
     String matKhauMoi = String.copyValueOf(jpw_M2.getPassword());
    if(jpw_MKC.getText().equals("")){
           sb.append("MẬT KHẨU CŨ KHÔNG ĐƯỢC ĐỂ TRỐNG \n");
           jpw_MKC.setBackground(Color.YELLOW);
       }else{
           jpw_MKC.setBackground(Color.white);
       }
    
     if(jpwM1.getText().equals("")|| jpw_M2.getText().equals("") ){
         sb.append("MẬT KHẨU MỚI KHÔNG ĐƯỢC ĐỂ TRỐNG\n");
       }
       if(sb.length()>0){
           JOptionPane.showMessageDialog(this, sb);
           return;
       }
       
        if(jpwM1.getText().equals(jpw_M2.getText())){
        }else{
            MessageDialogHelper.showErrorDiaalog(this, "MẬT KHẨU NHẬP LẠI KHÔNG ĐÚNG", "LỖI");     
            return;
        }  
       if(JOptionPane.showConfirmDialog(this, "BẠN CÓ MUỐN ĐỔI KHÔNG KHÔNG ?")== JOptionPane.NO_OPTION){
            return;
        } 
       try {
        Client.closeAllViews();
       
//        Client.socketHandle.write("change-pass,"+ taiKhoan +","+matKhau+","+matKhauMoi);
        
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this, "LỖI"+e.getMessage());
            e.printStackTrace(); 
        }
       

    
    }//GEN-LAST:event_jbt_OKActionPerformed

    private void jcbb_ShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbb_ShowActionPerformed
             if(jcbb_Show.isSelected()) {
			jpw_MKC.getPassword();
                        jpwM1.getPassword();
                        jpw_M2.getPassword();
			jpw_MKC.setEchoChar((char)0);
                        jpwM1.setEchoChar((char)0);
                        jpw_M2.setEchoChar((char)0);
		}else {
			jpw_MKC.setEchoChar('•');
                        jpwM1.setEchoChar('•');
                        jpw_M2.setEchoChar('•');
				}   
    }//GEN-LAST:event_jcbb_ShowActionPerformed

    
    private void jbt_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt_ClearActionPerformed
      jpw_MKC.setText("");
      jpwM1.setText("");
      jpw_M2.setText("");
    }//GEN-LAST:event_jbt_ClearActionPerformed

    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChangePassword dialog = new ChangePassword(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JButton jbt_Clear;
    private javax.swing.JButton jbt_OK;
    private javax.swing.JCheckBox jcbb_Show;
    private javax.swing.JPasswordField jpwM1;
    private javax.swing.JPasswordField jpw_M2;
    private javax.swing.JPasswordField jpw_MKC;
    private javax.swing.JTextField jtf_Users;
    // End of variables declaration//GEN-END:variables
}
