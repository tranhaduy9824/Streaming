package org.example.client.UI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class test extends JPanel {
    private JPanel videoListPanel; // Panel chứa danh sách phòng
    private List<String[]> roomList; // Danh sách phòng

    public test() {
        this.roomList = new ArrayList<>(); // Khởi tạo danh sách phòng
        initComponents(); // Khởi tạo giao diện
    }

    // Hàm khởi tạo giao diện
    private void initComponents() {
        // Tạo JScrollPane để cuộn danh sách video
        JScrollPane jScrollPaneHome = new JScrollPane();
        videoListPanel = new JPanel(); // Panel chứa các phòng

        // Sử dụng GridLayout với 4 cột, khoảng cách giữa các phần tử là 10px
        videoListPanel.setLayout(new GridLayout(0, 4, 10, 10));
        jScrollPaneHome.setViewportView(videoListPanel);

        // Thiết lập layout của HomePanel
        setLayout(new BorderLayout());
        add(jScrollPaneHome, BorderLayout.CENTER);
    }

    // Hàm cập nhật danh sách phòng
    public void updateRoomList(String roomListString) {
        // Xóa danh sách cũ
        roomList.clear();
        videoListPanel.removeAll();

        // Parse danh sách phòng từ chuỗi
        String[] rooms = roomListString.split(",");
        for (String room : rooms) {
            if (!room.trim().isEmpty()) {
                String[] roomDetails = room.split("\\|");
                if (roomDetails.length == 3) { // name|owner|participants
                    roomList.add(roomDetails);

                    // Tạo VideoItemPanel cho mỗi phòng
                    VideoItemPanel videoItem = new VideoItemPanel(
                        roomDetails[0], // Tên phòng
                        roomDetails[1], // Chủ sở hữu
                        new ImageIcon(getClass().getResource("/PhotoCover/1.png")), // Ảnh phòng tạm thời
                        new ImageIcon(getClass().getResource("/ICON/user32.png")) // Ảnh user tạm thời
                    );

                    // Thêm videoItem vào videoListPanel
                    videoListPanel.add(videoItem);
                }
            }
        }

        // Refresh UI
        videoListPanel.revalidate();
        videoListPanel.repaint();
    }

    // Hàm lấy danh sách phòng (nếu cần dùng ngoài HomePanel)
    public List<String[]> getRoomList() {
        return roomList;
    }

    // Test HomePanel độc lập
    public static void main(String[] args) {
        JFrame frame = new JFrame("Home Panel Test");
        test homePanel = new test();

        // Thêm HomePanel vào JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(homePanel);

        // Giả lập danh sách phòng và cập nhật giao diện
        String mockRoomList = "Room1|Alice|10,Room2|Bob|20,Room3|Charlie|30,Room4|David|15";
        homePanel.updateRoomList(mockRoomList);

        frame.setVisible(true);
    }
}

// Class đại diện cho từng phòng trong danh sách
class VideoItemPanel extends JPanel {
    public VideoItemPanel(String roomName, String owner, ImageIcon roomImage, ImageIcon userIcon) {
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.BLACK, 1)); // Viền đen cho từng phòng
        setPreferredSize(new Dimension(150, 200)); // Kích thước tạm thời

        // Ảnh phòng
        JLabel roomImageLabel = new JLabel(roomImage);
        roomImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(roomImageLabel, BorderLayout.CENTER);

        // Thông tin phòng
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(new JLabel("Room: " + roomName));
        infoPanel.add(new JLabel("Owner: " + owner));

        add(infoPanel, BorderLayout.NORTH);

        // Biểu tượng số lượng người tham gia
        JLabel participantsLabel = new JLabel(userIcon);
        participantsLabel.setText("10 Users"); // Ví dụ, cần thay thế bằng dữ liệu thực tế
        participantsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(participantsLabel, BorderLayout.SOUTH);
    }
}
