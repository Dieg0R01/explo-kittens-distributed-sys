package Server.GameLogic;

import DomainModel.Player;
import java.util.HashMap;
import java.util.Map;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private final String id;
    private final int maxPlayers;
    private Player creator;
    private final Map<Player, PrintStream> playerStreamMap = new HashMap<>();  // Players-Stream lists

    public Room(String id, int maxPlayers, Player creator) {
        this.id = id;
        this.maxPlayers = maxPlayers;
        this.creator = creator;
        playerStreamMap.put(creator, null); // Creator joins automatically
    }

    public String getId() {
        return id;
    }

    public List<Player> getPlayersList() {
        return (List<Player>)playerStreamMap.keySet();
    }

    public Player getPlayer(String playerName) {
        for (Player player : playerStreamMap.keySet()) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    public Map<Player, PrintStream> getPlayerStreams() {
        return playerStreamMap;
    }

    public void addPlayer(Player player, PrintStream playerStream) {
        if (!isFull()) {
            playerStreamMap.put(player, playerStream); // Asocia el jugador con su PrintStream
        }
    }

    public boolean isFull() {
        return playerStreamMap.size() >= maxPlayers;
    }

    public boolean isReadyToStart() {
        return isFull();
    }

    public void removePlayer(Player player){
        playerStreamMap.remove(player);
        if (player.equals(creator)) {
            if (!playerStreamMap.isEmpty()) {
                creator = playerStreamMap.keySet().iterator().next();
            } else {
                creator = null;
            }
        }
    }
}
