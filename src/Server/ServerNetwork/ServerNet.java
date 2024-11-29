package Server.ServerNetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerNet {
    private static final int DEFAULT_PORT = 55555; // Server Port
    
    private int port;
    private ExecutorService pool ; // Pool of threads to manage all clients
    // private final GameLogic gameLogic = new GameLogic(); // Game global logic

    public ServerNet(int port){
        this.port = port;
        pool = Executors.newCachedThreadPool();
    }

    public void start(){
        try(ServerSocket ss = new ServerSocket(port)){
            System.out.println("Server started on port: " + port);

            while(!Thread.interrupted()){
                Socket client = ss.accept();
                System.out.println("New client connected: " + client.getInetAddress());
                // Here with the data of GameLogic, we should run a pool.execute for each client.
                // Create pool.execute(ClientHandler(client, pool, (¿player o gameLogic?)))
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerNet server = new ServerNet(DEFAULT_PORT);
        server.start();
    }
}
