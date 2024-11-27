package DomainModel;
public class TurnPriorityManager{
    private PlayerManager playerManager;
    private Player currentPlayer;
    private Player activePlayer;
    private List<Player> turns;
    private CardStack stack;

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
     * @return Player return the currentPlayer
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @param currentPlayer the currentPlayer to set
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * @return Player return the activePlayer
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * @param activePlayer the activePlayer to set
     */
    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * @return List<Player> return the turns
     */
    public List<Player> getTurns() {
        return turns;
    }

    /**
     * @param turns the turns to set
     */
    public void setTurns(List<Player> turns) {
        this.turns = turns;
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