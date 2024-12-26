package org.example.client.UI.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Objects;

import org.example.client.LivestreamClient;
import org.example.client.UI.components.HyperlinkText;
import org.example.client.UI.components.TextFieldPassword;
import org.example.client.UI.components.TextFieldUsername;
import org.example.client.UI.components.Toaster.Toaster;
import org.example.client.UI.components.UIUtils;
import org.example.controller.UserController;
import org.example.server.model.User;

public class test extends JPanel {

    private final Toaster toaster;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserController userController = new UserController();

    public test() {
        setLayout(null); // Absolute layout
        setPreferredSize(new Dimension(800, 450));

        // Background color
        setBackground(new Color(255, 214, 214));

        // Add components
        addLogo();
        addLoginLabel();
        addUsernameField();
        addPasswordField();
        addLoginButton();
        addRegisterLink();
        
        // Initialize toaster
        toaster = new Toaster(this);
    }
        private JPanel getMainJPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 450));
        panel.setBackground(UIUtils.COLOR_BACKGROUND_LOGIN);
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
                SwingUtilities.getWindowAncestor(test.this).setLocation(
                        SwingUtilities.getWindowAncestor(test.this).getLocationOnScreen().x + x - lastX,
                        SwingUtilities.getWindowAncestor(test.this).getLocationOnScreen().y + y - lastY);
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
        leftPanel.setBounds(0, 0, 350, 450);
        leftPanel.setLayout(null);

        JLabel logoLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/1.png")));
            Image scaledImage = icon.getImage().getScaledInstance(340, 500, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (NullPointerException e) {
            System.err.println("Error: Logo file not found!");
        }
        logoLabel.setBounds(0, 0, 340, 500);
        leftPanel.add(logoLabel);

        add(leftPanel);
    }

    private void addLoginLabel() {
        JLabel loginLabel = new JLabel("LOGIN");
        loginLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
        loginLabel.setForeground(new Color(0, 135, 255));
        loginLabel.setBounds(450, 50, 300, 50);
        add(loginLabel);
    }

    private void addUsernameField() {
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setBounds(420, 110, 300, 20);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBounds(420, 140, 250, 30);
        add(usernameField);
    }

    private void addPasswordField() {
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(420, 180, 300, 20);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(420, 210, 250, 30);
        add(passwordField);
    }

    private void addLoginButton() {
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(420, 260, 250, 40);
        loginButton.setBackground(new Color(255, 102, 102));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogin());
        add(loginButton);
    }

    private void addRegisterLink() {
        JLabel registerLabel = new JLabel("I don't have an account");
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        registerLabel.setBounds(420, 310, 150, 30);
        add(registerLabel);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(580, 310, 90, 30);
        signUpButton.setBackground(new Color(255, 255, 255));
        signUpButton.setForeground(new Color(255,153,0));
        signUpButton.setFocusPainted(false);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 12));
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpButton.addActionListener(e -> handleRegister(this));
        add(signUpButton);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            User user = userController.login(username, password);
            if (user != null) {
                toaster.success("Login successful!");
            } else {
                toaster.error("Invalid username or password");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            toaster.error("An error occurred while logging in");
        }
    }

    private void handleRegister(JPanel panel) {
        System.out.println("Sign Up button clicked");
         panel.add(new HyperlinkText(UIUtils.BUTTON_TEXT_REGISTER, 625, 320, () -> {
            LivestreamClient.showRegistrationPanel();
        }));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new test());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
