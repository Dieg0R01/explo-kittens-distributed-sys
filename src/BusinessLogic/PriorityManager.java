package BusinessLogic;

import java.util.ArrayList;
import java.util.List;

import DomainModel.CardStack;
import DomainModel.Player;

public class PriorityManager {
    private static PriorityManager instance;
    private ArrayList<Player> playerList;
    private Player activePlayer;
    private int cycleCount; // Check with server implementation

    private PriorityManager() {
        this.playerList = new ArrayList<Player>();
    }
    public static PriorityManager getInstance() {
        if (instance == null) {
            instance = new PriorityManager();
        }
        return instance;
    }
    public static void tearDown() {
        instance = null;
    }

    public Player getActivePlayer() {
        return this.activePlayer;
    }

    public int getPlayerCount() {
        return this.playerList.size();
    }

    public void addPlayers(List<Player> players) {
        this.playerList.addAll(players);
        if (this.activePlayer == null) {
            this.activePlayer = this.playerList.getFirst();
        }
    }

    public void removePlayer(Player player) {
        this.playerList.remove(player);
    }

    public void nextPlayer() {
        this.activePlayer = this.playerList.get((this.playerList.indexOf(getActivePlayer()) + 1) % getPlayerCount());
    }

    public void resolveCard() {
        Player startingPlayer = getActivePlayer();
        do {
            nextPlayer();
            setCycleCount(getCycleCount() + 1);
        } while (!startingPlayer.equals(getActivePlayer()));

        CardStack.getInstance().resolveTopCard();
    }

    public void setCycleCount(int i) {
        cycleCount = i;

    }

    public int getCycleCount() {
        return cycleCount;
    }

}
