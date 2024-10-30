package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationPanel extends JPanel {
    private JTextField usernameField;

    public RegistrationPanel() {
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
        JButton registerButton = new JButton("Register");
        add(registerButton, gbc);
        registerButton.addActionListener(new RegisterActionListener());

        gbc.gridy = 2;
        JButton switchToLoginButton = new JButton("Login");
        add(switchToLoginButton, gbc);
        switchToLoginButton.addActionListener(e -> LivestreamClient.showLoginPanel());
    }

    private class RegisterActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String message = "REGISTER:" + username;
            if (LivestreamClient.sendBroadcastMessage(message)) {
                System.out.println("Sent registration request for username: " + username);
                LivestreamClient.setUsername(username); // Set the username
                LivestreamClient.showMainPanel();
            } else {
                JOptionPane.showMessageDialog(RegistrationPanel.this, "Failed to send registration request.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}