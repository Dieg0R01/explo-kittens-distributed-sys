package Server.ServerNetwork;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import BusinessLogic.Game;
import DomainModel.Player;
import Server.GameLogic.Room;

public class ClientHandler implements Runnable{
    private final Socket client;
    private String currentRoomId; // Storage the room id associated to the client when JOIN
    private Player playerHandled;
    private ExecutorService gameExecutor;

    public ClientHandler(Socket c) {
        this.client = c;
        gameExecutor = Executors.newSingleThreadExecutor(); // execute sequential way
    }

    // deliver logic /////////////////////////////////////////////////////////
    @Override
    public void run(){
        try{
            // Input/Output flows
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            PrintStream out = new PrintStream(client.getOutputStream());

            // First message maybe the instance of Player
            playerHandled = (Player) in.readObject();
            processPlayer(playerHandled, out);

            // Other message instructions
            String message;
            while ((message = (String) in.readObject()) != null) {
                String finalMessage = message;
                gameExecutor.submit(() -> processMessage(finalMessage, out));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // business logic ///////////////////////////////////////////////////////
    private void processPlayer(Player player, PrintStream out) {
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
    private void processMessage(String message, PrintStream out){
        if(message.startsWith("CREATE")){
            handleCreateRoom(message, out);
        }else if(message.startsWith("JOIN")){
            handleJoinRoom(message, out);
        }else if(message.startsWith("EXIT")){
            handleExitRoom(out);
        }
    }

    private void handleCreateRoom(String message, PrintStream out) {
        String[] parts = message.split("\\s");
        int maxPlayers = Integer.parseInt(parts[1]);
        String roomId = ServerNet.roomManager.createRoom(maxPlayers, playerHandled);
        out.println("room_created:" + roomId);
        currentRoomId = roomId; // storage id current room linked
    }

    private void handleJoinRoom(String message, PrintStream out) {
        String[] parts = message.split("\\s");
        String roomId = parts[1];
        Room room = ServerNet.roomManager.joinRoom(roomId, playerHandled, out);
        if (room != null) {
            out.println("joined_room:" + roomId);
            currentRoomId = roomId;

            if (room.isReadyToStart()) {
                gameExecutor.submit(() -> startGame(room));
            }
        } else {
            out.println("error:room_full_or_not_found");
        }
    }

    private void handleExitRoom(PrintStream out) {
        if (currentRoomId != null) {
            Room room = ServerNet.roomManager.getRoom(currentRoomId);
            room.removePlayer(playerHandled);
            out.println("exited_room:" + currentRoomId);
            currentRoomId = null;
        } else {
            out.println("error:not_in_room");
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