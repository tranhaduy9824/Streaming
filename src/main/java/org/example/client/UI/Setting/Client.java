
package org.example.client.UI.Setting;

import org.example.client.LivestreamClientJFrame;
import org.example.server.model.User;


public class Client {
    public enum View{
        HOME,
        APPNOTICE,
        VIEW_ACCOUNT,
        CHANGE_PASSWORD
        
    }
    public static User user;
    public static JFrame_AppNotice jfAppNotice;
    public static JFrame_Account jfAccount;
    public static ChangePassword jfChangePass;
    public static LivestreamClientJFrame jfHome;
        
    public Client() {
    } 
    public static void openView(View viewName){
        if(viewName !=null){
             switch(viewName){
                 case CHANGE_PASSWORD:
                    jfChangePass = new ChangePassword(jfHome, true);
                    jfChangePass.setVisible(true);
                    break;
                                 
                 case VIEW_ACCOUNT:
                    jfAccount = new JFrame_Account();
                    jfAccount.setVisible(true);
                    break;
                    
             }

        }
    }
         
    public static void closeView(View viewName){
        if(viewName !=null){
             switch(viewName){
                case CHANGE_PASSWORD:      
                    jfChangePass.dispose();
                    break;
                                     
                case VIEW_ACCOUNT:
                    jfAccount.dispose();
                    break;
                case APPNOTICE:
                    jfAppNotice.dispose();
                    break;
             }
        }
         
    }
    public static void openView(View viewName, String arg1, String arg2){
        if(viewName != null){
            switch(viewName){
                case APPNOTICE:
                    jfAppNotice = new JFrame_AppNotice(arg1, arg2);
                    jfAppNotice.setVisible(true);
                    break;
            }
        }
    }
    public static void closeAllViews(){
      
       if (jfChangePass!=null) jfChangePass.dispose();
       if (jfHome!=null) jfHome.dispose();
       if(jfAppNotice!=null) jfAppNotice.dispose();
       if(jfAccount!=null) jfAccount.dispose();
     }
    
    public static void closeViewAccount(){
      if(jfAccount!=null) jfAccount.dispose();
     }
     
    public static void closeViewAppNotice(){
      if(jfAppNotice!=null) jfAppNotice.dispose();
     }
    public static void main(String args[]) {
      
    }
}
