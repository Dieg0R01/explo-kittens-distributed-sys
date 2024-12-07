package Server.GameLogic;

import DomainModel.Player;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RoomManager {
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final List<Player> Lobby = Collections.synchronizedList(new ArrayList<Player>());

    // ------------------- ROOMS --------------------------
    public synchronized String createRoom(int maxPlayers, Player creator) {
        String roomId = UUID.randomUUID().toString(); // Generates a unique id
        rooms.put(roomId, new Room(roomId, maxPlayers, creator));
        return roomId;
    }

    public synchronized Room joinRoom(String roomId, Player playerName, PrintStream stream) {
        Room room = rooms.get(roomId);
        if (room == null || room.isFull()) {
            return null; // Room not found or full
        }
        room.addPlayer(playerName, stream);
        return room;
    }

    public synchronized void removeRoom(String roomId) {
        rooms.remove(roomId);
    }

    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }

    //----------------------------- LOBBY -----------------------

    public synchronized void enterLobby(Player pl){
        Lobby.add(pl);
    }

    public synchronized void exitLobby(Player pl){
        Lobby.remove(pl);
    }

}
