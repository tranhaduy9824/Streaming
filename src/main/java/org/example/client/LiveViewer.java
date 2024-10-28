package org.example.client;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class LiveViewer {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String serverAddress = "192.168.1.5"; // Change to the correct server IP
        int serverPort = 12345;

        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Request list of live sessions
            out.println("LIST_SESSIONS");
            String response = in.readLine();
            System.out.println(response);

            // Join live session
            out.println("JOIN_SESSION Live1");
            String ipAddress = in.readLine().split(" ")[4];
            System.out.println("Connecting to Live1 at " + ipAddress);

            // Now you can close the TCP connection
            socket.close();


            // Receive video stream from live session via UDP
            DatagramSocket udpSocket = new DatagramSocket(9876);
            udpSocket.setSoTimeout(10000); // Timeout of 10 seconds
            byte[] receiveData = new byte[65535];

            while (true) {
                try {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    udpSocket.receive(receivePacket);

                    // Check packet length
                    if (receivePacket.getLength() > 0) {
                        byte[] imageData = new byte[receivePacket.getLength()];
                        System.arraycopy(receivePacket.getData(), 0, imageData, 0, receivePacket.getLength());

                        Mat frame = Imgcodecs.imdecode(new MatOfByte(imageData), Imgcodecs.IMREAD_UNCHANGED);
                        if (!frame.empty()) {
                            HighGui.imshow("Live Stream", frame);
                            HighGui.waitKey(30);
                        }
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("No data received within the wait time.");
                }
            }
        } catch (SocketException e) {
            System.err.println("Connection error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Input/Output error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
