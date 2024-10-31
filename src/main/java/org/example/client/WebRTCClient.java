package org.example.client;

import dev.onvoid.webrtc.*;
import dev.onvoid.webrtc.media.video.VideoTrack;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class WebRTCClient {
    private PeerConnectionFactory peerConnectionFactory;
    private RTCPeerConnection peerConnection;
    private WebSocketClient socket;
    private VideoTrack localVideoTrack;
    private VideoTrack remoteVideoTrack;

    public WebRTCClient() {
        // Initialize WebRTC
        PeerConnectionFactory.InitializationOptions initializationOptions =
                PeerConnectionFactory.InitializationOptions.builder()
                        .createInitializationOptions();
        PeerConnectionFactory.initialize(initializationOptions);

        peerConnectionFactory = PeerConnectionFactory.builder().createPeerConnectionFactory();

        // Initialize WebSocket
        try {
            socket = new WebSocketClient(new URI("ws://" + ClientConfig.SIGNALING_SERVER_ADDRESS + ":" + ClientConfig.SIGNALING_SERVER_PORT)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("WebSocket connection opened");
                }

                @Override
                public void onMessage(String message) {
                    handleMessage(message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("WebSocket connection closed: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    System.err.println("WebSocket error: " + ex.getMessage());
                }
            };
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Initialize PeerConnection
        RTCConfiguration rtcConfig = new RTCConfiguration();
        rtcConfig.iceServers = new ArrayList<>();
        peerConnection = peerConnectionFactory.createPeerConnection(rtcConfig, new PeerConnectionObserver() {
            @Override
            public void onIceCandidate(RTCIceCandidate candidate) {
                sendIceCandidate(candidate);
            }

            @Override
            public void onAddStream(MediaStream stream) {
                if (!stream.getVideoTracks().isEmpty()) {
                    remoteVideoTrack = stream.getVideoTracks().get(0);
                }
            }

            @Override
            public void onRemoveStream(MediaStream stream) {
                remoteVideoTrack = null;
            }
        });
    }

    private void handleMessage(String message) {
        // Handle incoming signaling messages (offer, answer, ICE candidates)
        // This method needs to be implemented based on your signaling protocol
        // Example:
        // if (message.contains("offer")) {
        //     handleOffer(message);
        // } else if (message.contains("answer")) {
        //     handleAnswer(message);
        // } else if (message.contains("candidate")) {
        //     handleIceCandidate(message);
        // }
    }

    private void sendIceCandidate(RTCIceCandidate candidate) {
        // Send ICE candidate to the signaling server
        // This method needs to be implemented based on your signaling protocol
        // Example:
        // socket.send("candidate:" + candidate.toString());
    }

    public void createOffer() {
        peerConnection.createOffer(new CreateSessionDescriptionObserver() {
            @Override
            public void onSuccess(RTCSessionDescription description) {
                peerConnection.setLocalDescription(new SetSessionDescriptionObserver() {
                    @Override
                    public void onSuccess() {
                        // Send offer to the signaling server
                        // This method needs to be implemented based on your signaling protocol
                        // Example:
                        // socket.send("offer:" + description.toString());
                    }

                    @Override
                    public void onFailure(String error) {
                        System.err.println("Failed to set local description: " + error);
                    }
                }, description);
            }

            @Override
            public void onFailure(String error) {
                System.err.println("Failed to create offer: " + error);
            }
        });
    }

    public void createAnswer() {
        peerConnection.createAnswer(new CreateSessionDescriptionObserver() {
            @Override
            public void onSuccess(RTCSessionDescription description) {
                peerConnection.setLocalDescription(new SetSessionDescriptionObserver() {
                    @Override
                    public void onSuccess() {
                        // Send answer to the signaling server
                        // This method needs to be implemented based on your signaling protocol
                        // Example:
                        // socket.send("answer:" + description.toString());
                    }

                    @Override
                    public void onFailure(String error) {
                        System.err.println("Failed to set local description: " + error);
                    }
                }, description);
            }

            @Override
            public void onFailure(String error) {
                System.err.println("Failed to create answer: " + error);
            }
        });
    }

    public void addIceCandidate(RTCIceCandidate candidate) {
        peerConnection.addIceCandidate(candidate);
    }

    // Example methods to handle offers and answers
    private void handleOffer(String sdp) {
        RTCSessionDescription offer = new RTCSessionDescription(RTCSdpType.OFFER, sdp);
        peerConnection.setRemoteDescription(new SetSessionDescriptionObserver() {
            @Override
            public void onSuccess() {
                createAnswer();
            }

            @Override
            public void onFailure(String error) {
                System.err.println("Failed to set remote description: " + error);
            }
        }, offer);
    }

    private void handleAnswer(String sdp) {
        RTCSessionDescription answer = new RTCSessionDescription(RTCSdpType.ANSWER, sdp);
        peerConnection.setRemoteDescription(new SetSessionDescriptionObserver() {
            @Override
            public void onSuccess() {
                // Successfully set remote description
            }

            @Override
            public void onFailure(String error) {
                System.err.println("Failed to set remote description: " + error);
            }
        }, answer);
    }

    private void handleIceCandidate(String candidate) {
        // Parse the candidate string and add it to the peer connection
        // Example:
        // RTCIceCandidate iceCandidate = new RTCIceCandidate(candidate);
        // addIceCandidate(iceCandidate);
    }
}