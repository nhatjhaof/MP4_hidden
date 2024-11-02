import java.io.*;

public class DecodeMessageMP4 {

    public static void main(String[] args) {
        String steganographedFilePath = "steganographed.mp4"; // Tệp MP4 đã chứa thông điệp giấu
        String decodedMessageFilePath = "decoded_message.txt"; // Tệp văn bản để lưu thông điệp giải mã

        try {
            decodeMessage(steganographedFilePath, decodedMessageFilePath);
            System.out.println("Decoded message saved to " + decodedMessageFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decodeMessage(String inputFilePath, String outputFilePath) throws IOException {
        StringBuilder decodedMessage = new StringBuilder();
        
        try (FileInputStream fis = new FileInputStream(inputFilePath);
             FileWriter writer = new FileWriter(outputFilePath)) {
             
            byte[] buffer = new byte[1024];
            int bytesRead;
            int bitIndex = 0;
            byte currentChar = 0;

            while ((bytesRead = fis.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    // Lấy từng bit từ byte để tái tạo thông điệp
                    currentChar = (byte) ((currentChar << 1) | (buffer[i] & 1));
                    bitIndex++;
                    if (bitIndex == 8) {  // Tạo thành một ký tự đầy đủ
                        decodedMessage.append((char) currentChar);
                        bitIndex = 0;
                        currentChar = 0;
                    }
                }
            }
            
            // Ghi thông điệp giải mã vào tệp văn bản
            writer.write(decodedMessage.toString());
        }
    }
}