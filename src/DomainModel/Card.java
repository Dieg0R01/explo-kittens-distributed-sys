package DomainModel;

import DomainModel.Game.Player;

// Essential implements Cloneable to cast clone() without Exceptions
public abstract class Card implements Cloneable {
    protected int cardID;
    protected String imagePath;

    public int getCardID() {
        return cardID;
    }
    public String getImagePath() {
        return imagePath;
    }
    public String toString() {
        return this.getClass().getName();
    }

    abstract public void cardAction(Player p1, Player p2);
    abstract public Card clone();
}
