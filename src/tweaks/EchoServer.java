package tweaks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class EchoServer {

    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            System.out.println( serverSocket.getReceiveBufferSize() );
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

            String pathURL = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
            System.out.println(pathURL);

            Path path = Paths.get(pathURL);

            int bufferSize = 8192; // 8KB buffer size

            FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);

            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            int read;
            while ( (read = channel.read(buffer) ) != -1) {
                buffer.flip();
                // process buffer
                System.out.println("READ THIS NUMBER OF BYTES: "+read);
                buffer.clear();
            }
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
