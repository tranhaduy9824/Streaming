package org.example.client.UI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.example.client.LivestreamClient;
import org.example.client.UI.components.UIUtils;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel {
    private JTable roomTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private String[] allRooms;

    public MainPanel() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.COLOR_BACKGROUND);

        JLabel usernameLabel = new JLabel("Logged in as: " + LivestreamClient.getUsername());
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setFont(UIUtils.FONT_GENERAL_UI);
        usernameLabel.setForeground(UIUtils.OFFWHITE);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(usernameLabel, BorderLayout.NORTH);

        searchField = new JTextField();
        searchField.setFont(UIUtils.FONT_GENERAL_UI);
        searchField.setForeground(UIUtils.OFFWHITE);
        searchField.setBackground(UIUtils.COLOR_BACKGROUND);
        searchField.setCaretColor(UIUtils.OFFWHITE);
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterRooms();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterRooms();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterRooms();
            }
        });

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(2, 20, 5, 20));
        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setFont(UIUtils.FONT_GENERAL_UI.deriveFont(18f));
        searchLabel.setForeground(UIUtils.OFFWHITE);
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        northPanel.add(usernameLabel, BorderLayout.NORTH);
        northPanel.add(searchPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        String[] columnNames = { "Name", "Owner", "Participants" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row)) {
                    c.setBackground(UIUtils.COLOR_INTERACTIVE);
                    c.setForeground(Color.white);
                } else {
                    c.setBackground(UIUtils.COLOR_BACKGROUND);
                    c.setForeground(UIUtils.OFFWHITE);
                }
                if (c instanceof JLabel) {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                }
                return c;
            }
        };
        roomTable.setBackground(UIUtils.COLOR_BACKGROUND);
        roomTable.setForeground(UIUtils.OFFWHITE);
        roomTable.setFont(UIUtils.FONT_GENERAL_UI);
        roomTable.setSelectionBackground(UIUtils.COLOR_INTERACTIVE);
        roomTable.setSelectionForeground(Color.white);
        roomTable.setRowHeight(30);

        JTableHeader tableHeader = roomTable.getTableHeader();
        tableHeader.setBackground(UIUtils.COLOR_BACKGROUND);
        tableHeader.setForeground(UIUtils.OFFWHITE);
        tableHeader.setFont(UIUtils.FONT_GENERAL_UI);
        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        add(new JScrollPane(roomTable), BorderLayout.CENTER);

        roomTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = roomTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String roomName = (String) tableModel.getValueAt(selectedRow, 0);
                        LivestreamClient.joinRoom(roomName);
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton createRoomButton = new JButton("Create Room");
        styleButton(createRoomButton);
        createRoomButton.addActionListener(e -> {
            String roomName = JOptionPane.showInputDialog(this, "Enter room name:");
            if (roomName != null && !roomName.trim().isEmpty()) {
                String multicastAddress = generateRandomMulticastAddress();
                int multicastPort = generateRandomMulticastPort();
                LivestreamClient.setCurrentMulticastAddress(multicastAddress);
                LivestreamClient.setCurrentMulticastPort(multicastPort);
                LivestreamClient.createRoom(roomName.trim(), multicastAddress, multicastPort);
            }
        });
        buttonPanel.add(createRoomButton);

        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton);
        logoutButton.addActionListener(e -> LivestreamClient.showLoginPanel());
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String generateRandomMulticastAddress() {
        int firstOctet = 224 + (int) (Math.random() * 16);
        int secondOctet = (int) (Math.random() * 256);
        int thirdOctet = (int) (Math.random() * 256);
        int fourthOctet = (int) (Math.random() * 256);
        return firstOctet + "." + secondOctet + "." + thirdOctet + "." + fourthOctet;
    }

    private int generateRandomMulticastPort() {
        return 5000 + (int) (Math.random() * 1000);
    }

    private void styleButton(JButton button) {
        button.setBackground(UIUtils.COLOR_INTERACTIVE);
        button.setForeground(Color.white);
        button.setFont(UIUtils.FONT_GENERAL_UI);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(UIUtils.COLOR_INTERACTIVE_DARKER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(UIUtils.COLOR_INTERACTIVE);
            }
        });
    }

    public void updateRoomList(String roomListString) {
        allRooms = roomListString.split(",");
        filterRooms();
    }

    private void filterRooms() {
        if (allRooms == null) {
            return;
        }
        String searchText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);
        for (String room : allRooms) {
            if (!room.trim().isEmpty()) {
                String[] roomDetails = room.split("\\|");
                if (roomDetails.length == 6) {
                    String roomName = roomDetails[0].toLowerCase();
                    String roomOwner = roomDetails[1].toLowerCase();
                    if (roomName.contains(searchText) || roomOwner.contains(searchText)) {
                        tableModel.addRow(roomDetails);
                    }
                }
            }
        }
    }
}