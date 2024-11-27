package DomainModel;

import BusinessLogic.HandManager;
import BusinessLogic.PlayerManager;

public class Player{
    private String name;
    private HandManager hand;
    private PlayerManager playerManager;
    private boolean hasPriority;

    public Player() {
		this.name = "default";
		hand = new HandManager();
	}

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
    public Hand getHandManager() {
        return hand;
    }

    /**
     * @param handManager the handManager to set
     */
    public void setHandManager(HandManager handManager) {
        this.handManager = handManager;
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



}