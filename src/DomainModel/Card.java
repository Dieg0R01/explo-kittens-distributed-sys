package DomainModel;

// Essential implements Cloneable to cast clone() without Exceptions
public abstract class Card implements Cloneable {
    protected C_Type type;
    protected String imagePath;

    public C_Type getType() {
        return type;
    }
    public String getImagePath() {
        return imagePath;
    }
    public String toString() {
        return this.getClass().getName();
    }

    public abstract void cardAction(Player p1, Player p2);
    public abstract Card clone();
}
