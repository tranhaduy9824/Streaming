package org.example.client.UI;

import org.example.client.LivestreamClient;
import org.example.client.UI.components.HyperlinkText;
import org.example.client.UI.components.TextFieldPassword;
import org.example.client.UI.components.TextFieldUsername;
import org.example.client.UI.components.Toaster.Toaster;
import org.example.client.UI.components.UIUtils;
import org.example.controller.UserController;
import org.example.server.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Objects;

public class RegistrationPanel extends JPanel {

    private final Toaster toaster;
    private TextFieldUsername usernameField;
    private TextFieldPassword passwordField;
    private TextFieldPassword confirmPasswordField;
    private UserController userController = new UserController();

    public RegistrationPanel() {
        setLayout(null); // Absolute layout
        setPreferredSize(new Dimension(800, 500));
        setBackground(new Color(174,190,201));

        toaster = new Toaster(this);

        addLogo();
//        addSeparator();
        addSignUpLabel();
        addUsernameTextField();
        addPasswordTextField();
        addConfirmPasswordTextField();
        addRegisterButton();
        addLoginLink();
//        addSwitchToLoginButton();

    }

    private JPanel getMainJPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 500));
        panel.setBackground(UIUtils.COLOR_BACKGROUND_REGISTER);
        panel.setLayout(null);

        MouseAdapter ma = new MouseAdapter() {
            int lastX, lastY;

            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getXOnScreen();
                lastY = e.getYOnScreen();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                SwingUtilities.getWindowAncestor(RegistrationPanel.this).setLocation(
                        SwingUtilities.getWindowAncestor(RegistrationPanel.this).getLocationOnScreen().x + x - lastX,
                        SwingUtilities.getWindowAncestor(RegistrationPanel.this).getLocationOnScreen().y + y - lastY);
                lastX = x;
                lastY = y;
            }
        };
        panel.addMouseListener(ma);
        panel.addMouseMotionListener(ma);

        return panel;
    }

    private void addLogo() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(255, 214, 214));
        leftPanel.setBounds(0, 0, 340, 500);
        leftPanel.setLayout(null);

        JLabel logoLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/2.png")));
            Image scaledImage = icon.getImage().getScaledInstance(340, 500, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (NullPointerException e) {
            System.err.println("Error: Logo file not found!");
        }
        logoLabel.setBounds(0, 0, 340, 500);
        leftPanel.add(logoLabel);

        add(leftPanel);
    }

    //    private void addSeparator(JPanel panel) {
//        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
//        separator.setForeground(UIUtils.COLOR_OUTLINE);
//        separator.setBounds(340, 80, 1, 290);
//        panel.add(separator);
//    }
    private void addSignUpLabel() {
        JLabel signUpLabel = new JLabel("SIGN UP");
        signUpLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
        signUpLabel.setForeground(new Color(0, 135, 255));
        signUpLabel.setBounds(490, 20, 300, 50);
        add(signUpLabel);
    }


    private void addUsernameTextField() {
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setBounds(420, 90, 300, 20);
        add(usernameLabel);

        usernameField = new TextFieldUsername();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBounds(420, 120, 280, 30);
        add(usernameField);
    }

    private void addPasswordTextField() {
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(420, 160, 300, 20);
        add(passwordLabel);

        passwordField = new TextFieldPassword();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(420, 190, 280, 30);
        add(passwordField);
    }

    private void addConfirmPasswordTextField() {
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordLabel.setBounds(420, 230, 300, 20);
        add(confirmPasswordLabel);

        confirmPasswordField = new TextFieldPassword();
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setBounds(420, 260, 280, 30);
        add(confirmPasswordField);
    }


    private void addRegisterButton() {
        JButton registerButton = new JButton("Sign Up");
        registerButton.setBounds(420, 310, 280, 40);
        registerButton.setBackground(new Color(255, 102, 102));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                registerEventHandler();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(new Color(255, 80, 80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(new Color(255, 102, 102));
            }
        });

        add(registerButton);
    }



//    private void addSwitchToLoginButton(JPanel panel) {
//        panel.add(new HyperlinkText(UIUtils.BUTTON_TEXT_LOGIN, 620, 350, () -> {
//            toaster.success("Switching to login panel");
//            LivestreamClient.showLoginPanel();
//        }));
//    }

    private void addLoginLink() {
        JLabel loginPromptLabel = new JLabel("Already have an account?");
        loginPromptLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        loginPromptLabel.setBounds(420, 360, 150, 30);
        add(loginPromptLabel);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(580, 360, 90, 30);
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(new Color(255,153,0));
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginButton.addActionListener(e -> LivestreamClient.showLoginPanel());
        add(loginButton);
    }

    private void addLoginButton() {
        System.out.println("Login button clicked");
        add(new HyperlinkText(UIUtils.BUTTON_TEXT_REGISTER, 625, 320, () -> {
            LivestreamClient.showLoginPanel();
        }));
    }

    private void registerEventHandler() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Check if any of the fields are empty
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            toaster.error("Username, Password, and Confirm Password cannot be empty.");
            return; // Prevent registration if any field is empty
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            toaster.error("Passwords do not match.");
            return;
        }

        try {
            User user = userController.register(username, password);
            String message = "REGISTER:" + username + ":" + password;
            if (LivestreamClient.sendBroadcastMessage(message)) {
                LivestreamClient.setUsername(user.getUsername());
                LivestreamClient.setUserId(String.valueOf(user.getId()));
                LivestreamClient.showMainPanel();
            } else {
                toaster.error("Registration failed.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            toaster.error("An error occurred while registering.");
        }
    }

}
