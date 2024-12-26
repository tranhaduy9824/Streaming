
package org.example.client.UI.components.Helper;



import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageHelper {
    // Phương thức để thay đổi kích thước ảnh
    public static Image resize(Image originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        return resultingImage;
    }
    
    public static byte[] toByteArray(byte[] imageBytes, String type) throws IOException {
    BufferedImage bimage = ImageIO.read(new ByteArrayInputStream(imageBytes));

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bimage, type, baos);
    byte[] imageInByte = baos.toByteArray();

    return imageInByte;
}
    // Chuyển đổi ảnh thành mảng byte với định dạng hình ảnh cụ thể
    public static byte[] toByteArray(Image img, String type) throws IOException {
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bimage, type, baos);
        byte[] imageInByte = baos.toByteArray();

        return imageInByte;
    }
    
    // Chuyển đổi ảnh thành mảng byte với định dạng hình ảnh cụ thể
    private static byte[] toBytearray(Image img, String type) throws IOException {
        // Tạo một đối tượng BufferedImage với kích thước và kiểu ảnh tương tự
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        // Tạo một ByteArrayOutputStream để ghi ảnh vào
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Ghi ảnh vào ByteArrayOutputStream với định dạng hình ảnh cụ thể
        ImageIO.write(bimage, type, baos);
        // Chuyển đổi ByteArrayOutputStream thành mảng byte
        byte[] imageInByte = baos.toByteArray();

        return imageInByte;
    }

    // Tạo ảnh từ mảng byte với định dạng hình ảnh cụ thể
    public static Image createImageFromByteArray(byte[] data, String type) throws IOException {
        // Tạo một ByteArrayInputStream từ mảng byte
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        // Đọc ByteArrayInputStream và tạo một đối tượng BufferedImage
        BufferedImage bImage2 = ImageIO.read(bis);
        // Thay đổi kích thước ảnh để có được Image
        Image img = bImage2.getScaledInstance(bImage2.getWidth(), bImage2.getHeight(), Image.SCALE_SMOOTH);
        return img;
    }


}
