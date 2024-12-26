
package org.example.client.UI;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.example.client.LivestreamClientJFrame;
import org.example.client.UI.components.Helper.MessageDialogHelper;
import org.example.dao.RoomDAO;
import org.example.dao.UserDAO;
import org.example.server.model.Room;
import org.example.server.model.User;

public class HistoryStreamPanel extends javax.swing.JPanel {
 
    private DefaultTableModel tableModel; 
    
    public HistoryStreamPanel() {
        initComponents();
        initTable();
        String uname = LivestreamClientJFrame.getUsername();
        getIdOwner(uname);
       
    }
    
    private void getIdOwner(String username){
        try{
            UserDAO dao = new UserDAO();
            User us = dao.FindIdByUsername(username);
            if(us != null){
                System.out.println("IdOwner: "+us.getId());
                String id = String.valueOf(us.getId());
                 loadDataToTable(id);
            }else{
               JOptionPane.showMessageDialog(this, "ERROR");
           }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "LỖI!"+e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    private void initTable(){
           tableModel = new DefaultTableModel();
           tableModel.setColumnIdentifiers(new String[]{"ID","NAME ROOM","TITLE STREAM","START_TIME","END_TIME"});
           jtb_History.setModel(tableModel);
     }
    
     private void  loadDataToTable (String id){
        try {
           
        RoomDAO dao = new RoomDAO();
        List<Room> list = dao.findRoomById(id);
            
            tableModel.setRowCount(0);
            
            for (Room room : list) {
               tableModel.addRow(new Object[]{
                   room.getId(),
                    room.getRoomName(),
                    room.getTitleStream(),
                    room.getStartTime(),
                    room.getEndTime()
               });
                
            }
            tableModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDiaalog(this,e.getMessage(), "LỖI!");
        }
    }  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtb_History = new javax.swing.JTable();

        setBackground(new java.awt.Color(204, 255, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("History livestream");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jtb_History.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtb_History);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtb_History;
    // End of variables declaration//GEN-END:variables
}
