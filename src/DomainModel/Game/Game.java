package DomainModel.Game;

import DomainModel.DiscardDeck;
import DomainModel.MainDeck;

public class Game{

    private MainDeck mainDeck;
    private DiscardDeck discardDeck;
    private PlayerManager playerManager;
    private TurnPriorityManager turnPriorityManager;
    private CardStack stack;

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

    /**
     * @return TurnPriorityManager return the turnPriorityManager
     */
    public TurnPriorityManager getTurnPriorityManager() {
        return turnPriorityManager;
    }

    /**
     * @param turnPriorityManager the turnPriorityManager to set
     */
    public void setTurnPriorityManager(TurnPriorityManager turnPriorityManager) {
        this.turnPriorityManager = turnPriorityManager;
    }

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

}