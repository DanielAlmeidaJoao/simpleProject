package tweaks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class EchoClient {

    public static void main(String[] args) {
        String serverHostname = "localhost";
        int serverPort = 12345;

        try (Socket socket = new Socket(serverHostname,serverPort)) {
            System.out.println("Connected to server " + serverHostname + ":" + serverPort);

            Path filePath = Paths.get("Lecture 10.5 - Transport Protocols and the End-to-End argument.pdf");
            Path serverPath = Paths.get(socket.getLocalAddress().getHostAddress());

            int bufferSize = 8192; // 8KB buffer size

            try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.READ);
                 FileChannel serverChannel = FileChannel.open(serverPath, StandardOpenOption.WRITE)
            ) {
                ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
                while (channel.read(buffer) != -1) {
                    buffer.flip();
                    // process buffer
                    serverChannel.write(buffer);
                    buffer.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverHostname);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + serverHostname + ":" + serverPort);
            e.printStackTrace();
        }
    }
}
