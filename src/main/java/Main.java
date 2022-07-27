import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(1234);
            Server server = new Server(ss);
            server.startServer();
        } catch (IOException e) {
            System.out.println("Error in main");
        }
    }
}
