package org.example.client;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;
import org.example.config.ServerConfig;
import org.example.utils.Constants;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebRTCClient extends JFrame {
    private WebSocketClient client;
    private FrameGrabber grabber;

    public WebRTCClient() {
        setTitle("Livestream Client");
        setSize(640, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        connectWebSocket();

        try {
            grabber = new VideoInputFrameGrabber(0);
            grabber.start();
            new Thread(this::startStreaming).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    private void connectWebSocket() {
        try {
            client = new WebSocketClient(new URI("ws://" + Constants.SERVER_ADDRESS + ":" + ServerConfig.SIGNALING_PORT)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to server");
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received: " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Connection closed");
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void startStreaming() {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        while (true) {
            try {
                Frame frame = grabber.grab();
                if (frame != null) {
                    BufferedImage image = converter.convert(frame);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(image, "jpg", baos);
                    byte[] imageBytes = baos.toByteArray();
                    if (client != null && client.isOpen()) {
                        client.send(imageBytes);
                    } else {
                        System.out.println("WebSocket connection is not open.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WebRTCClient::new);
    }
}