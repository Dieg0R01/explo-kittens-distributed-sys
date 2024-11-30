package Server.GameLogic;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final String id;
    private final int maxPlayers;
    private String creator;
    private final List<String> players = new ArrayList<>();

    public Room(String id, int maxPlayers, String creator) {
        this.id = id;
        this.maxPlayers = maxPlayers;
        this.creator = creator;
        players.add(creator); // Creator joins automatically
    }

    public String getId() {
        return id;
    }

    public boolean isFull() {
        return players.size() >= maxPlayers;
    }

    public void addPlayer(String playerName) {
        if (!isFull()) {
            players.add(playerName);
        }
    }

    public void removePlayer(String playerName){
        players.remove(playerName);
        
        if(playerName.equalsIgnoreCase(creator)){
            
            try{
                creator = players.get(0);
            }catch(IndexOutOfBoundsException e){
                e.printStackTrace();
                creator = null;
            }
            
        }
        
    }

    public List<String> getPlayers() {
        return players;
    }

    public boolean isReadyToStart() {
        return isFull();
    }
}
