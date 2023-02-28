import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LargeFileReader {

    public static void main(String[] args) {
        Path filePath = Paths.get("largefile.txt");
        int bufferSize = 8192; // 8KB buffer size
        
        try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            while (channel.read(buffer) != -1) {
                buffer.flip();
                // process buffer
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
