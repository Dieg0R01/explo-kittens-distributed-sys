package Server.ServerNetwork;

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
    private String currentRoomId; // Storage the room id associated to the client when JOIN

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


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void processMessage(String message){
        // Messages to proccess example:
        // - CREATE 4
        // - JOIN f47ac10b-58cc-4372-a567-0e02b2c3d479
        // - EXIT
        if(message.startsWith("CREATE")){
            String[] parts = message.split("\\s");
            int maxPlayers = Integer.parseInt(parts[1]);
            String roomId = ServerNet.roomManager.createRoom(maxPlayers, client.getInetAddress().getHostAddress());
            out.println("room_created:" + roomId);
            currentRoomId = roomId; // storage id current room linked
        }else if(message.startsWith("JOIN")){
            String[] parts = message.split("\\s");
            String roomId = parts[1];
            Room room = ServerNet.roomManager.joinRoom(roomId, client.getInetAddress().getHostAddress());
            if(room != null){
                out.println("joined_room:" + roomId);
                currentRoomId = roomId; // storage id current room linked
                if(room.isReadyToStart()){
                    ServerNet.pool.execute(startGame(room));
                }
            }else{
                out.println("error:room_full_or_not_found");
            }
        }else if(message.startsWith("EXIT")){
            if (currentRoomId != null) {
                ServerNet.roomManager.getRoom(currentRoomId).removePlayer(client.getInetAddress().getHostAddress());
                out.println("exited_room:" + currentRoomId);
                currentRoomId = null;  // Limpia el ID de la sala
            } else {
                out.println("error:not_in_room");
            }
        }
    }
    private void startGame(Room room){
        gameThreadPool.submit(())
    }
}