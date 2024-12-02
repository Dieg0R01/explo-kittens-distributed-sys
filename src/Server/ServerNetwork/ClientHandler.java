package Server.ServerNetwork;

import java.io.*;
import java.net.Socket;

import BusinessLogic.Game;
import DomainModel.Player;
import Server.GameLogic.Room;
public class ClientHandler implements Runnable{
    private final Socket client;
    private ObjectInputStream in;
    private PrintStream out;
    private String currentRoomId; // Storage the room id associated to the client when JOIN
    private Player playerHandled;

    public ClientHandler(Socket c) {
        this.client = c;
    }

    @Override
    public void run(){
        try{
            // Input/Output flows
            in = new ObjectInputStream(client.getInputStream());
            out = new PrintStream(client.getOutputStream());

            // First message maybe the instance of Player
            playerHandled = (Player) in.readObject();
            processPlayer(playerHandled);

            // Other message instructions
            String message;
            while ((message = (String) in.readObject()) != null) {
                processMessage(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processPlayer(Player player) {
        System.out.println("Welcome player: " + player.getName());
        // Set id in case pre instance room (to no creator players)
        Room room = ServerNet.roomManager.getRoom(currentRoomId);
        if (room != null) {
            room.addPlayer(player, out);
        }
    }

    /* Messages to proccess example:
     * CREATE 4
     * JOIN f47ac10b-58cc-4372-a567-0e02b2c3d479
     * EXIT
     */
    private void processMessage(String message){
        if(message.startsWith("CREATE")){
            String[] parts = message.split("\\s");
            int maxPlayers = Integer.parseInt(parts[1]);
            String roomId = ServerNet.roomManager.createRoom(maxPlayers, playerHandled);
            out.println("room_created:" + roomId);
            currentRoomId = roomId; // storage id current room linked
        }else if(message.startsWith("JOIN")){
            String[] parts = message.split("\\s");
            String roomId = parts[1];
            Room room = ServerNet.roomManager.joinRoom(roomId, playerHandled, out);
            if(room != null){
                out.println("joined_room:" + roomId);
                ServerNet.roomManager.joinRoom(roomId, playerHandled, out);
                if(room.isReadyToStart()){
                    ServerNet.pool.execute(startGame(room));
                }
            }else{
                out.println("error:room_full_or_not_found");
            }
        }else if(message.startsWith("EXIT")){
            if (currentRoomId != null) {
                Room room = ServerNet.roomManager.getRoom(currentRoomId);
                Player player = room.getPlayer(client.getInetAddress().getHostAddress());
                room.removePlayer(player);
                out.println("exited_room:" + currentRoomId);
                currentRoomId = null;
            } else {
                out.println("error:not_in_room");
            }
        }
    }
    private Runnable startGame(Room room){
        if (room.isReadyToStart()){
            System.out.println("Game has started on room: " + room.getId());

            for (PrintStream playerStream : room.getPlayerStreams().values()){
                playerStream.println("Game has started on room: " + room.getId());
            }

            //<WARNING> game flow

            //Game game = new Game(room);
            //game.start();
        }
        return null;
    }
}