package org.example.client.UI;

import javax.swing.*;

import org.example.client.LivestreamClient;
import org.example.client.UI.components.HyperlinkText;
import org.example.client.UI.components.TextFieldPassword;
import org.example.client.UI.components.TextFieldUsername;
import org.example.client.UI.components.UIUtils;
import org.example.client.UI.components.Toaster.Toaster;
import org.example.controller.UserController;
import org.example.server.model.User;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Objects;

public class LoginPanel extends JPanel {

    private final Toaster toaster;
    private TextFieldUsername usernameField;
    private TextFieldPassword passwordField;
    private UserController userController = new UserController();

    public LoginPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel mainJPanel = getMainJPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(mainJPanel, gbc);

        addLogo(mainJPanel);
        addSeparator(mainJPanel);
        addUsernameTextField(mainJPanel);
        addPasswordTextField(mainJPanel);
        addLoginButton(mainJPanel);
        addRegisterButton(mainJPanel);

        toaster = new Toaster(mainJPanel);
        this.setPreferredSize(new Dimension(800, 450));
    }

    private JPanel getMainJPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 450));
        panel.setBackground(UIUtils.COLOR_BACKGROUND);
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
                SwingUtilities.getWindowAncestor(LoginPanel.this).setLocation(
                        SwingUtilities.getWindowAncestor(LoginPanel.this).getLocationOnScreen().x + x - lastX,
                        SwingUtilities.getWindowAncestor(LoginPanel.this).getLocationOnScreen().y + y - lastY);
                lastX = x;
                lastY = y;
            }
        };
        panel.addMouseListener(ma);
        panel.addMouseMotionListener(ma);

        return panel;
    }

    private void addLogo(JPanel panel) {
        JLabel label = new JLabel();
        label.setFocusable(false);

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("login.jpg")));
        Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImage));

        label.setBounds(40, 90, 250, 250);
        panel.add(label);
    }

    private void addSeparator(JPanel panel) {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setForeground(UIUtils.COLOR_OUTLINE);
        separator.setBounds(340, 80, 1, 290);
        panel.add(separator);
    }

    private void addUsernameTextField(JPanel panel) {
        usernameField = new TextFieldUsername();
        usernameField.setBounds(420, 110, 250, 44);

        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals(UIUtils.PLACEHOLDER_TEXT_USERNAME)) {
                    usernameField.setText("");
                }
                usernameField.setForeground(Color.white);
                usernameField.setBorderColor(UIUtils.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText(UIUtils.PLACEHOLDER_TEXT_USERNAME);
                }
                usernameField.setForeground(UIUtils.COLOR_OUTLINE);
                usernameField.setBorderColor(UIUtils.COLOR_OUTLINE);
            }
        });
        panel.add(usernameField);
    }

    private void addPasswordTextField(JPanel panel) {
        passwordField = new TextFieldPassword();
        passwordField.setBounds(420, 170, 250, 44);

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setForeground(Color.white);
                passwordField.setBorderColor(UIUtils.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                passwordField.setForeground(UIUtils.COLOR_OUTLINE);
                passwordField.setBorderColor(UIUtils.COLOR_OUTLINE);
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    loginEventHandler();
            }
        });
        panel.add(passwordField);
    }

    private void addLoginButton(JPanel panel) {
        JLabel loginButton = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIUtils.COLOR_INTERACTIVE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);
                g2.setColor(Color.white);
                g2.setFont(UIUtils.FONT_GENERAL_UI);
                g2.drawString(UIUtils.BUTTON_TEXT_LOGIN, getWidth() / 2 - 30, getHeight() / 2 + 5);
            }
        };

        loginButton.setBounds(420, 250, 250, 44);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                loginEventHandler();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(UIUtils.COLOR_INTERACTIVE_DARKER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(UIUtils.COLOR_INTERACTIVE);
            }
        });

        panel.add(loginButton);
    }

    private void addRegisterButton(JPanel panel) {
        panel.add(new HyperlinkText(UIUtils.BUTTON_TEXT_REGISTER, 625, 320, () -> {
            LivestreamClient.showRegistrationPanel();
        }));
    }

    private void loginEventHandler() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            User user = userController.login(username, password);
            if (user != null) {
                String message = "LOGIN:" + username;
                if (LivestreamClient.sendBroadcastMessage(message)) {
                    System.out.println("Sent login request for username: " + username);
                    LivestreamClient.setUsername(username);
                    LivestreamClient.showMainPanel();
                } else {
                    toaster.error("Login failed");
                }
            } else {
                toaster.error("Invalid username or password");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            toaster.error("An error occurred while logging in");
        }
    }
}
