package org.example.client;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class RoomOwnerPanel extends JPanel {
    private JTextPane commentPane;
    private JTextField commentField;
    private StyledDocument doc;
    private JPanel videoPanel;
    private FrameGrabber grabber;
    private Java2DFrameConverter converter;
    private BufferedImage currentImage;

    public RoomOwnerPanel() {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Room Owner - Live Stream");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        videoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentImage != null) {
                    g.drawImage(currentImage, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        videoPanel.setPreferredSize(new Dimension(640, 480));
        add(videoPanel, BorderLayout.WEST);

        commentPane = new JTextPane();
        commentPane.setEditable(false);
        doc = commentPane.getStyledDocument();
        add(new JScrollPane(commentPane), BorderLayout.CENTER);

        JPanel commentPanel = new JPanel(new BorderLayout());
        commentField = new JTextField();
        commentPanel.add(commentField, BorderLayout.CENTER);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendCommentActionListener());
        commentPanel.add(sendButton, BorderLayout.EAST);
        add(commentPanel, BorderLayout.SOUTH);

        JButton closeRoomButton = new JButton("Close Room");
        closeRoomButton.addActionListener(new CloseRoomActionListener());
        add(closeRoomButton, BorderLayout.NORTH);

        startVideoStream();
    }

    private void startVideoStream() {
        new Thread(() -> {
            try {
                grabber = new VideoInputFrameGrabber(1);
                grabber.start();
                converter = new Java2DFrameConverter();
                while (true) {
                    Frame frame = grabber.grab();
                    if (frame != null) {
                        currentImage = converter.convert(frame);
                        videoPanel.repaint();
                    }
                }
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private class SendCommentActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comment = commentField.getText();
            if (!comment.trim().isEmpty()) {
                LivestreamClient.sendComment(comment);
                addComment("You: " + comment, true);
                commentField.setText("");
            }
        }
    }

    private class CloseRoomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LivestreamClient.leaveRoom();
        }
    }

    public void addComment(String comment, boolean isOwner) {
        try {
            Style style = doc.addStyle("Style", null);
            StyleConstants.setForeground(style, isOwner ? Color.BLUE : Color.BLACK);
            doc.insertString(doc.getLength(), comment + "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}