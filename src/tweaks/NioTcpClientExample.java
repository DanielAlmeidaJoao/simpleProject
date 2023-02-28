package tweaks;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioTcpClientExample {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8081;
        int totalSent = 0;

        try (SocketChannel socketChannel = SocketChannel.open()) {
            // Connect to the server
            socketChannel.connect(new InetSocketAddress(host, port));

            Path filePath = Paths.get("Lecture 10.5 - Transport Protocols and the End-to-End argument.pdf");
            FileChannel channel = FileChannel.open(filePath, StandardOpenOption.READ);
            int bufferSize = 8192; // 8KB buffer size

            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            int read = 0;
            buffer.clear();
            while ( ( read =  channel.read(buffer) ) != -1) {
                buffer.flip();
                // process buffer
                // Write data to the server
                //String message = "Hello, server!";
                //ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                totalSent += read;
                socketChannel.write(buffer);

                buffer.clear();
            }
            System.out.println("Data sent to server: " + totalSent);
            //Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
