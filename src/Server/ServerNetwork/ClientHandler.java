package Server.ServerNetwork;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import BusinessLogic.Game;
import DomainModel.Player;
import Server.GameLogic.GameSession;
import Server.GameLogic.Room;
import Server.GameLogic.RoomManager;

public class ClientHandler implements Runnable{
    private final Socket client;
    private String currentRoomId; // Storage the room id associated to the client when JOIN
    private Player playerHandled;
    private static final RoomManager roomManager = new RoomManager();
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
            String playerName = (String) in.readObject();
            (playerHandled = new Player()).setName(playerName);
            playerHandled.setSocket(client);
            processPlayer(playerHandled, out);

            
            // Other message instructions
            String message;
            while ((message = (String) in.readObject()) != null && !message.startsWith("DISCONNECT")) {
                String finalMessage = message;
                gameExecutor.submit(() -> processMessage(finalMessage, out));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            cerrarConexion();
        }
    }

    // business logic ///////////////////////////////////////////////////////
    private void processPlayer(Player player, PrintStream out) {
        System.out.println("Welcome player: " + player.getName());
        // Set id in case pre instance room (to no creator players)
       
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
            handleDisconnect(out);
        }
    }

    private void handleCreateRoom(String message, PrintStream out) {
        String[] parts = message.split("\\s"); // Example: "CREATE 4"
        int maxPlayers = Integer.parseInt(parts[1]); // Extract the maximum number of players

        // The client executing this method is the creator/host of the room
        String roomId = roomManager.createRoom(maxPlayers, playerHandled);

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
        
        if (!room.isHost(playerHandled)) {
            out.println("error:player_not_host");
            return; // If not the host, do not allow the game to start
        }
        // Check if there are enough players to start the game
        if (room.getPlayersList().size() < 2) {
            System.out.println("Not enough players to start the game.");
            return;
        } else if ((room.getMaxPlayers() > room.getPlayersList().size())) {
            System.out.println("Too many players to start the game.");
            return;
        }
        if (room.isReadyToStart()) {
           startGame(room);
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


    private void handleDisconnect(PrintStream out){
        System.out.println("Player disconnected: " + playerHandled.getName());

        if (currentRoomId != null) {
            Room room = roomManager.getRoom(currentRoomId);
            if(room != null){
                room.removePlayer(playerHandled);
            }
            out.println("exited_room: " + currentRoomId);
            currentRoomId = null;
        }
        out.println("disconnected_from_server");
       
    }

    public void startGame(Room room){
        System.out.println("Game has started in room: " + room.getId());

        for (PrintStream playerStream : room.getPlayerStreams().values()) {
            playerStream.println("Game has started in room: " + room.getId());
        }

        GameSession gameSession = new GameSession(room);
        gameExecutor.execute(gameSession);
    }

    private void cerrarConexion(){
    
        try{
            client.close();
            gameExecutor.shutdown();
            System.out.println("Closing connection with client: " + playerHandled.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}