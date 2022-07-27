import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket ss;
    private boolean isRunning;

    public Server(ServerSocket ss) {
        this.ss = ss;
    }

    public void startServer(){
        while (!ss.isClosed()){
            try {

                System.out.println("Waiting for connection...");
                // w obiekcie connectedClient przechowuje polaczonych uzytkowników, których przekierowuje
                // do wielowątkowej klasy
                Socket connectedClient = ss.accept();
                // wielowątkowa klasa dla użytkowników dołączających na serwer
                ConnectHandler clientHandler = new ConnectHandler(connectedClient);
                clientHandler.start();
            } catch (IOException e) {
                System.out.println("Error in startServer method.");
                break;
            }
        }
    }
}