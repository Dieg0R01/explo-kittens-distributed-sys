package Server.ServerNetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Server.GameLogic.RoomManager;

public class ServerNet {
    private static final int DEFAULT_PORT = 55555; // Server Port
    public static ExecutorService pool; // Pool of threads to manage all clients and movements in the game
    public static RoomManager roomManager;

    private int port;
    
    // private final GameLogic gameLogic = new GameLogic(); // Game global logic

    public ServerNet(int port){
        this.port = port;
        pool = Executors.newCachedThreadPool();
        roomManager = new RoomManager();
    }

    /**
     * Players connect to Server, but the Clients are not in a Room yet
     */
    public void start(){
        System.out.println("Starting Exploding Kittens server\nServer port : " + port);

        try(ServerSocket ss = new ServerSocket(port)){
            System.out.println("Server started on port: " + port);

            while(!Thread.interrupted()){
                Socket client = ss.accept();
                System.out.println("New client connected: " + client.getInetAddress());

                // We should run a Thread for each client.
                ClientHandler clientHandler = new ClientHandler(client);
                pool.execute(clientHandler); // Add other parameter(¿player o gameLogic?))
                
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            pool.shutdown();
        }
    }


    public void createRoom(){
        
    }

    public static void main(String[] args) {
        ServerNet server = new ServerNet(DEFAULT_PORT);
        server.start();
    }
}
