import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectHandler extends Thread implements Runnable{

    private static ArrayList<ConnectHandler> connections = new ArrayList<>();
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private String username;

    public ConnectHandler(Socket socket) {
        try {
            this.socket = socket;
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.pw = new PrintWriter(socket.getOutputStream(), true);
            this.username = br.readLine(); // nick clienta
            System.out.println(username);
            connections.add(this); // dodaje clienta do ArrayListy
            sendMessage("SERVER: " + username + " has joined the chat.");
        } catch (IOException e) {
            System.out.println("Error in ClientHandler constructor");
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                String messageFromClient = br.readLine();
                sendMessage(username + " : " + messageFromClient);
            } catch (IOException e) {
               System.out.println("Error in run method.");
                close();
                break;
            }
        }
    }

    public void sendMessage(String message){
        for (ConnectHandler ch : connections){
            ch.pw.println(message);
        }
    }

    public void removeClient(){
        connections.remove(this);
        sendMessage("SERVER: " + username + " has left the chat! ");
    }
    public void close(){
        removeClient();
            try {
                if (br != null) {
                    br.close();
                }
                if (pw != null) {
                    pw.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
