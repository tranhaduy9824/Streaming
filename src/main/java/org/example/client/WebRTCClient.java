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
import java.util.Base64;

public abstract class WebRTCClient {
    protected WebSocketClient client;
    protected FrameGrabber grabber;
    protected Java2DFrameConverter converter;
    protected BufferedImage currentImage;

    public WebRTCClient() {
        connectWebSocket();
        startVideoStream();
    }

    protected void connectWebSocket() {
        try {
            client = new WebSocketClient(new URI("ws://" + Constants.SERVER_ADDRESS + ":" + ServerConfig.SIGNALING_PORT)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to server");
                }

                @Override
                public void onMessage(String message) {
                    handleWebSocketMessage(message);
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

    protected void startVideoStream() {
        new Thread(() -> {
            try {
                grabber = new VideoInputFrameGrabber(1);
                grabber.start();
                converter = new Java2DFrameConverter();
                while (true) {
                    try {
                        Frame frame = grabber.grab();
                        if (frame != null) {
                            currentImage = converter.convert(frame);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(currentImage, "jpg", baos);
                            byte[] imageBytes = baos.toByteArray();
                            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
                            if (client != null && client.isOpen()) {
                                client.send(encodedImage);
                            } else {
                                System.out.println("WebSocket connection is not open.");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    protected abstract void handleWebSocketMessage(String message);
}