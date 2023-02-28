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
            System.out.println(serverSocketChannel.socket().getReceiveBufferSize());

            while (true) {
                try (SocketChannel clientChannel = serverSocketChannel.accept()) {
                    System.out.println("New client connected: " + clientChannel.getRemoteAddress());
                    long start = System.currentTimeMillis();
                    // Read incoming data from the client
                    ByteBuffer buffer = ByteBuffer.allocate(clientChannel.socket().getReceiveBufferSize());
                    int bytesRead = -1;
                    int total = 0;
                    while ( (bytesRead = clientChannel.read(buffer)) != -1) {
                        total +=bytesRead;
                        buffer.flip();
                        buffer.clear();
                        // The client has disconnected
                        //System.out.println("total - got : " + total +" "+bytesRead);
                    }
                    long end = System.currentTimeMillis();

                    System.out.println("TOTAL "+total+" ELAPSED: "+ (end - start));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
