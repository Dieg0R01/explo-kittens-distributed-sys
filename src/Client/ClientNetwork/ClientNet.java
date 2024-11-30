package Client.ClientNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientNet {

    private static final String DEFAULT_SERVER_ADDRESS = "localhost";
    private final int DEFAULT_SERVER_PORT = 55555;

    private String serverAddress;                                       
    private int serverPort;
    private Socket socket;
    private BufferedReader in;
    private PrintStream out;

    public ClientNet(String serverAddress, int serverPort){

        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void start(){
        try(Socket socket = new Socket(InetAddress.getLocalHost(), DEFAULT_SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream out = new PrintStream(socket.getOutputStream());
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))){

            System.out.println("Connected to server.");

            String input;



        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Gets a message sent by the server.
     *
     * @return message sent by the server
     */

     public String getServerMessage() {
        String serverMessage = null;
        while (serverMessage == null) {
            try {
                serverMessage = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return serverMessage;
    }

    public static void main(String[] args) {
        
    }
    
}