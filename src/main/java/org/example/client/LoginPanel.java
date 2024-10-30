package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField usernameField;

    public LoginPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        add(loginButton, gbc);
        loginButton.addActionListener(new LoginActionListener());

        gbc.gridy = 2;
        JButton switchToRegisterButton = new JButton("Register");
        add(switchToRegisterButton, gbc);
        switchToRegisterButton.addActionListener(e -> LivestreamClient.showRegistrationPanel());
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String message = "LOGIN:" + username;
            if (LivestreamClient.sendBroadcastMessage(message)) {
                System.out.println("Sent login request for username: " + username);
                LivestreamClient.setUsername(username); // Set the username
                LivestreamClient.showMainPanel();
            } else {
                JOptionPane.showMessageDialog(LoginPanel.this, "Failed to send login request.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}