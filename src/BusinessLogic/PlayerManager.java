package BusinessLogic;

import java.util.ArrayList;
import java.util.List;

import DomainModel.Player;

public class PlayerManager{
    private List<Player> players;
    private List<Player> deadPlayers;


    public PlayerManager(){
        players = new ArrayList<Player>();
    }
    
    /**
     * @return List<Player> return the players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * @return List<Player> return the deadPlayers
     */
    public List<Player> getDeadPlayers() {
        return deadPlayers;
    }

    /**
     * @param deadPlayers the deadPlayers to set
     */
    public void setDeadPlayers(List<Player> deadPlayers) {
        this.deadPlayers = deadPlayers;
    }

}