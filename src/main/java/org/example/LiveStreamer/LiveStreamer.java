package org.example.LiveStreamer;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class LiveStreamer {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String serverAddress = "192.168.1.5"; // Địa chỉ IP của máy chủ
        int serverPort = 12345;

        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Bắt đầu phiên live stream
            out.println("START_LIVE Live1");
            System.out.println(in.readLine());

            // Gửi luồng video tới các client
            DatagramSocket udpSocket = new DatagramSocket();
            VideoCapture capture = new VideoCapture(1); // Mở webcam
            Mat frame = new Mat();

            if (!capture.isOpened()) {
                System.out.println("Không thể mở webcam!");
                return;
            }

            while (capture.read(frame)) {
                MatOfByte mob = new MatOfByte();
                Imgcodecs.imencode(".jpg", frame, mob);
                byte[] imageData = mob.toArray();

                // Hiển thị hình ảnh từ webcam cục bộ
                HighGui.imshow("Live Stream (Local View)", frame);
                HighGui.waitKey(1); // Để có thể cập nhật hình ảnh trên cửa sổ

                // Kích thước tối đa của gói tin UDP
                int maxPacketSize = 60000;
                int totalPackets = (int) Math.ceil((double) imageData.length / maxPacketSize);

                for (int i = 0; i < totalPackets; i++) {
                    int start = i * maxPacketSize;
                    int length = Math.min(imageData.length - start, maxPacketSize);
                    byte[] packetData = new byte[length + 4]; // 4 bytes để đánh số thứ tự gói tin

                    // Thêm số thứ tự gói tin
                    ByteBuffer.wrap(packetData, 0, 4).putInt(i);

                    // Sao chép dữ liệu vào gói tin
                    System.arraycopy(imageData, start, packetData, 4, length);

                    InetAddress clientIp = InetAddress.getByName("255.255.255.255"); // Gửi broadcast
                    DatagramPacket packet = new DatagramPacket(packetData, packetData.length, clientIp, 9876);
                    udpSocket.send(packet);
                }

                Thread.sleep(30); // Điều chỉnh tốc độ gửi khung hình (FPS)
            }

            udpSocket.close();
            capture.release();
            HighGui.destroyAllWindows(); // Đóng cửa sổ hiển thị
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
