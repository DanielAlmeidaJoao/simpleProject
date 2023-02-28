import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

    public static void main(String[] args) {
        String serverHostname = "localhost";
        int serverPort = 12345;

        try (Socket socket = new Socket(serverHostname,serverPort)) {
            System.out.println("Connected to server " + serverHostname + ":" + serverPort);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("Received from server: " + in.readLine());
            }

            in.close();
            out.close();
            stdIn.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverHostname);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + serverHostname + ":" + serverPort);
            e.printStackTrace();
        }
    }
}
