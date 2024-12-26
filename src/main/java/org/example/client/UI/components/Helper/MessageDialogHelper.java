package org.example.client.UI.components.Helper;

import java.awt.Component;
import javax.swing.JOptionPane;


public class MessageDialogHelper {
    public static void showMessageDiaalog (Component parent,String content ,String title){
        JOptionPane.showMessageDialog(parent,content,title,JOptionPane.INFORMATION_MESSAGE);
        
    }
    public static void showErrorDiaalog (Component parent,String content ,String title){
        JOptionPane.showMessageDialog(parent,content,title,JOptionPane.ERROR_MESSAGE);
        
    }
    public static int showConfirmDiaalog (Component parent,String content ,String title){
       int choose =  JOptionPane.showConfirmDialog(parent,content,title,
               JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE
                    );
       return choose;
        
    }
}
