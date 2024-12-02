package Server.GameLogic;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private final String id;
    private final int maxPlayers;
    private String creator;
    private final List<String> players = new ArrayList<>();
    private final List<PrintStream> playerStreams = new ArrayList<>();  // Players stream lists

    public Room(String id, int maxPlayers, String creator) {
        this.id = id;
        this.maxPlayers = maxPlayers;
        this.creator = creator;
        players.add(creator); // Creator joins automatically
        //<WARNING> Add the creator PrintStream???
    }

    public String getId() {
        return id;
    }

    public List<String> getPlayers() {
        return players;
    }

    public List<PrintStream> getPlayerStreams() {
        return playerStreams;
    }

    public void addPlayer(String playerName, PrintStream playerStream) {
        if (!isFull()) {
            players.add(playerName);
            playerStreams.add(playerStream);  // Save player PrintStream
        }
    }

    public boolean isFull() {
        return players.size() >= maxPlayers;
    }

    public boolean isReadyToStart() {
        return isFull();
    }

    public void removePlayer(String playerName){
        players.remove(playerName);
        //<WARNING> remove player PrintStream
        if(playerName.equalsIgnoreCase(creator)){
            try{
                creator = players.getFirst();
            }catch(IndexOutOfBoundsException e){
                e.printStackTrace();
                creator = null;
            }
            
        }
        
    }
}
