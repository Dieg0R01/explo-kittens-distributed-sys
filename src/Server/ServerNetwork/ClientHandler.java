import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import Server.GameLogic.Room;

public class ClientHandler implements Runnable{

    private final Socket client;
    private BufferedReader in;
    private PrintStream out;


    public ClientHandler(Socket c) {
        this.client = c;
    }

    @Override
    public void run(){

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintStream out = new PrintStream(client.getOutputStream());

            String message;
            while(( message = in.readLine()) != null){
                processMessage(message);
            }
           
            
        }
    }

    private void processMessage(String message){
        // Messages to proccess example: 
        // - create_room:4
        // - join_room:23333
        // - exit_room:23333
        if(message.startsWith("create_room:")){
            String[] parts = message.split(":");
            int maxPlayers = Integer.parseInt(parts[1]);
            String roomId = ServerNet.roomManager.createRoom(maxPlayers, client.getInetAddress().getHostAddress());
            out.println("room_created:" + roomId);
        }else if(message.startsWith("join_room:")){
            String[] parts = message.split(":");
            String roomId = parts[1];
            Room room = ServerNet.roomManager.joinRoom(roomId, client.getInetAddress().getHostAddress());
            if(room != null){
                out.println("joined_room:" + roomId);
                if(room.isReadyToStart()){
                    ServerNet.pool.execute(startGame(room));
                }
            }else{
                out.println("error:room_full_or_not_found");
            }
        }else if(message.startsWith("exit_room")){
            String[] parts = message.split(":");
            String roomId = parts[1];
            ServerNet.roomManager.getRoom(roomId).removePlayer(client.getInetAddress().getHostAddress());
            System.out.println("exited_room");
        }
    }

    private void startGame(Room room){
        gameThreadPool.submit(())
    }

}