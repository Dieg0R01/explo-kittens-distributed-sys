package DomainModel;

import java.util.List;

import BusinessLogic.HandManager;
import BusinessLogic.PlayerManager;

public class Player{
    private String name;
    private HandManager hand;
    private PlayerManager playerManager;
    private boolean hasPriority;

    /**
     * Constructor for a Player without a name
     */
    public Player() {
		this.name = "default";
		hand = new HandManager();
	}

    /**
     * Constructor for a Player without name "name"
     */
	public Player(String name) {
		this.name = name;
	}

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return HandManager return the handManager
     */
    public HandManager getHandManager() {
        return hand;
    }

    public List<Card> getHand(){
        return hand.getHand();
    }

    /**
     * @param handManager the handManager to set
     */
    public void setHandManager(HandManager handManager) {
        this.hand = handManager;
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
     * @return boolean return the hasPriority
     */
    public boolean isHasPriority() {
        return hasPriority;
    }

    /**
     * @param hasPriority the hasPriority to set
     */
    public void setHasPriority(boolean hasPriority) {
        this.hasPriority = hasPriority;
    }

    public Card drawCard(){
        return hand.draw();
    }

    public void addDefuseCardToHand(){
        this.hand.addDefuseCard();
    }
}