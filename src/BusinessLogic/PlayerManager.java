package BusinessLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DomainModel.Player;
import DomainModel.Card;

public class PlayerManager{
    private static final int INITIAL_NUMBER_OF_CARDS_PER_PLAYER = 6;
    private List<Player> players;
    private List<Player> deadPlayers;


    /**
     * Constructor for a PlayerManager
     */
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
     * @param numPlayers number of players to set 
     * When it is called by a superior class, it will validate if
     * the number of players are between 2 and 5
     */
    public void addPlayers(int numPlayers) {
        for(int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }
    }

    public Map<Player, List<Card>> getHands() {
		Map<Player, List<Card>> hands = new HashMap<Player, List<Card>>();
		for (int a = 0; a < players.size(); a++) {
			hands.put(players.get(a), players.get(a).getHand());
		}
		return hands;
	}
    /**
     * @return List<Player> return the deadPlayers
     */
    public List<Player> getDeadPlayers() {
        return deadPlayers;
    }

    public void distributePlayersInitialHand(){
        for(int i = 0; i < players.size(); i++){
            for(int j = 0; j < INITIAL_NUMBER_OF_CARDS_PER_PLAYER; j++){
                players.get(i).drawCard();
            }
            players.get(i).addDefuseCardToHand();
        }
    }
    /**
     * @param deadPlayers the deadPlayers to set
     */
    public void setDeadPlayers(List<Player> deadPlayers) {
        this.deadPlayers = deadPlayers;
    }

    public void removePlayerFromGame(Player player){
        players.remove(player);
    }

    
}