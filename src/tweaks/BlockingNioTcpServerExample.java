package tweaks;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class BlockingNioTcpServerExample {

    public static void main(String[] args) {
        int port = 8081;

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("Server started on port " + port);

            while (true) {
                try (SocketChannel clientChannel = serverSocketChannel.accept()) {
                    System.out.println("New client connected: " + clientChannel.getRemoteAddress());

                    // Read incoming data from the client
                    ByteBuffer buffer = ByteBuffer.allocate(8192);
                    int bytesRead = -1;
                    int total = 0;
                    while ( (bytesRead = clientChannel.read(buffer)) != -1) {
                        total +=bytesRead;
                        buffer.flip();
                        buffer.clear();
                        // The client has disconnected
                        //System.out.println("total - got : " + total +" "+bytesRead);
                    }
                    System.out.println("TOTAL "+total);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
