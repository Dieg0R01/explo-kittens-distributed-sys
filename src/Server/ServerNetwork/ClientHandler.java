package Server.ServerNetwork;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import BusinessLogic.Game;
import DomainModel.Player;
import Server.GameLogic.Room;
import Server.GameLogic.RoomManager;

public class ClientHandler implements Runnable{
    private final Socket client;
    private String currentRoomId; // Storage the room id associated to the client when JOIN
    private Player playerHandled;
    private RoomManager roomManager;
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
       // AQUÍ SE DEBERÍA METER QUE UN PLAYER SE AÑADA A LA LOBBY EN CUANTO SE CREA
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
        }else if(message.startsWith("START")){
            handleStartGame(out);
        }else if(message.startsWith("EXIT")){
            handleExitRoom(out);
        }else if(message.startsWith("DISCONNECT")){
            // Manage disconnect casewwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
        }
    }

    private void handleCreateRoom(String message, PrintStream out) {
        String[] parts = message.split("\\s"); // Example: "CREATE 4"
        int maxPlayers = Integer.parseInt(parts[1]); // Extract the maximum number of players

        // The client executing this method is the creator/host of the room
        String roomId = new RoomManager().createRoom(maxPlayers, playerHandled);

        out.println("room_created:" + roomId);
        currentRoomId = roomId; // Save the ID of the current room in the client handler
    }


    private void handleJoinRoom(String message, PrintStream out) {
        // Check if roomManager is initialized
        if (roomManager == null) {
            out.println("error:no_room_manager_available");
            return; // Exit early
        }

        // Parse the message to get the room ID
        String[] parts = message.split("\\s"); // Example: "JOIN f47ac10b-58cc-4372-a567-0e02b2c3d479"
        String roomId = parts[1]; // Extract the room ID

        // Attempt to join the room
        boolean isJoined = roomManager.joinRoom(roomId, playerHandled, out);
        if (isJoined) {
            out.println("joined_room:" + roomId);
            currentRoomId = roomId;  // Save the ID of the current room in the client handler
        }
    }

    private void handleStartGame(PrintStream out) {
        Room room = roomManager.getRoom(currentRoomId);
        if (room.isReadyToStart()) {
            gameExecutor.submit(() -> startGame(room, out));
        }
    }

    private void handleExitRoom(PrintStream out) {
        // Check if a room was attached to client
        if (currentRoomId != null) {
            // Remove player from room
            Room room = roomManager.getRoom(currentRoomId);
            room.removePlayer(playerHandled);

            out.println("exited_room:" + currentRoomId);
            currentRoomId = null;
        } else {
            out.println("error:not_in_room");
        }
    }


    private Runnable startGame(Room room, PrintStream out){
        if (!room.isHost(playerHandled)) {
            out.println("error:player_not_host");
            return null; // If not the host, do not allow the game to start
        }
        // Check if there are enough players to start the game
        if (room.getPlayersList().size() < 2) {
            System.out.println("Not enough players to start the game.");
            return null;
        }
        System.out.println("Game has started in room: " + room.getId());
        for (PrintStream playerStream : room.getPlayerStreams().values()) {
            playerStream.println("Game has started in room: " + room.getId());
        }

        //<WARNING> game flow

        Game game = new Game();
        game.start(room.getMaxPlayers());

        //Game game = new Game(room);
        //game.start();

        return null;
    }
}