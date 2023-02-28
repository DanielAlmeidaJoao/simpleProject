import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class LargeFileReaderInputStream {

    public static void main(String[] args) {
        String fileName = "largefile.txt";
        int bufferSize = 8192; // 8KB buffer size
        
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName), bufferSize)) {
            byte[] buffer = new byte[bufferSize];
            int bytesRead = 0;
            while ((bytesRead = bis.read(buffer)) != -1) {
                // process buffer
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
