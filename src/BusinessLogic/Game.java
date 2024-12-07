package BusinessLogic;

import java.util.List;
import java.util.Map;

import DomainModel.Card;
import DomainModel.CardStack;
import DomainModel.DiscardDeck;
import DomainModel.MainDeck;
import DomainModel.Player;
import Server.GameLogic.Room;

public class Game{

    private MainDeck mainDeck;
    private DiscardDeck discardDeck;
    private PlayerManager playerManager;
    private TurnManager turnManager;
    private PriorityManager priorityManager;
    private CardStack stack;

    public Game(){
        MainDeck.tearDown();
		mainDeck = MainDeck.getInstance();
		priorityManager = PriorityManager.getInstance();
		// TurnManager.InstantiateLogger(); !OJO¡ QUE HACEMOS CON LOS LOGGERS
		turnManager = TurnManager.getInstance();
    }
    /**
     * @return MainDeck return the mainDeck
     */
    public MainDeck getMainDeck() {
        return mainDeck;
    }

    /**
     * @param mainDeck the mainDeck to set
     */
    public void setMainDeck(MainDeck mainDeck) {
        this.mainDeck = mainDeck;
    }

    /**
     * @return DiscardDeck return the discardDeck
     */
    public DiscardDeck getDiscardDeck() {
        return discardDeck;
    }

    /**
     * @param discardDeck the discardDeck to set
     */
    public void setDiscardDeck(DiscardDeck discardDeck) {
        this.discardDeck = discardDeck;
    }

    /**
     * @return PlayerManager return the playerManager
     */
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    /**
     * @param playerManager the playerManager to set
     */
    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public TurnManager getTurnManager() {
		return turnManager;
	}

    
    /**
     * NO SE USA LA CLASE TURNPRIORITYMANAGER - OJOOO
     * @return TurnPriorityManager return the turnPriorityManager
     
    public TurnManager getTurnPriorityManager() {
        return turnManager;
    }

    /**
     * @param turnPriorityManager the turnPriorityManager to set
     
    public void setTurnPriorityManager(TurnManager turnManager) {
        this.turnManager = turnManager;
    }*/
    
    /**
     * @return CardStack return the stack
     */
    public CardStack getStack() {
        return stack;
    }

    /**
     * @param stack the stack to set
     */
    public void setStack(CardStack stack) {
        this.stack = stack;
    }

    /* 
    NOT USED
    
    public void start(int numPlayers) {
		if (numPlayers > 2 || numPlayers < 5) {

			mainDeck.initStartingDeck();
            playerManager = new PlayerManager();
            playerManager.addPlayers(numPlayers);
            priorityManager.addPlayers(playerManager.getPlayers());
            playerManager.distributePlayersInitialHand();
            mainDeck.populateDeck(numPlayers);
            turnManager.setPlayerManager(playerManager);
		}
		
	}
    */

    public void start(Room room) {
        int numPlayers = room.getPlayersList().size();
		if (numPlayers > 2 || numPlayers < 5) {

			mainDeck.initStartingDeck();
            playerManager = new PlayerManager();
            playerManager.setPlayers(room.getPlayersList());
            priorityManager.addPlayers(playerManager.getPlayers());
            playerManager.distributePlayersInitialHand();
            mainDeck.populateDeck(numPlayers);
            turnManager.setPlayerManager(playerManager);
		}
		
	}

    public void start(List<Player> players){
        
    }

    public Map<Player, List<Card>> getPlayerHands() {
		return playerManager.getHands();
	}

    public boolean isMainDeckEmpty() {
		return (mainDeck.getCardCount() == 0);
	}

    public void nextTurn() {
		turnManager.endTurnAndDraw();
		priorityManager.nextPlayer();
	}

    public Player getActivePlayer() {
		return priorityManager.getActivePlayer();
	}
    public Player getCurrentPlayer() {
		return turnManager.getCurrentPlayer();
	}

	public List<Player> getPlayers() {
		return playerManager.getPlayers();
	}

	
}