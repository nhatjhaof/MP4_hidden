import java.io.*;

public class HideMessageMP4 {
    private static final String SECRET_MESSAGE = "This is a secret!"; // Thông điệp cần giấu

    public static void main(String[] args) {
        String inputFilePath = "input.mp4";       // Đường dẫn tới tệp MP4 đầu vào
        String outputFilePath = "steganographed.mp4"; // Đường dẫn tới tệp MP4 đầu ra

        try {
            hideMessage(inputFilePath, outputFilePath);
            System.out.println("Message hidden successfully in " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void hideMessage(String inputFilePath, String outputFilePath) throws IOException {
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);
        
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            int messageIndex = 0;
            int bitIndex = 0;
            
            // Chuyển thông điệp thành mảng byte để giấu từng bit
            byte[] messageBytes = SECRET_MESSAGE.getBytes();

            while ((bytesRead = fis.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    if (messageIndex < messageBytes.length) {
                        // Giấu từng bit của thông điệp vào byte
                        buffer[i] = (byte) ((buffer[i] & 0xFE) | ((messageBytes[messageIndex] >> (7 - bitIndex)) & 1));
                        bitIndex++;
                        if (bitIndex == 8) {  // Chuyển sang byte tiếp theo của thông điệp
                            bitIndex = 0;
                            messageIndex++;
                        }
                    }
                }
                fos.write(buffer, 0, bytesRead);
            }
        }
    }
}