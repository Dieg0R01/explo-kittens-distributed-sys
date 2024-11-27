package DomainModel.Cards;

import DomainModel.Card;
import DomainModel.Player;

public class Cat extends Card {
    private int cardIcon;

    public Cat() {
        this.cardIcon = 0;
    }
    public Cat(String path) {
        this();
        this.imagePath = path;
    }
    public int getCardIcon() {
        return cardIcon;
    }
    public Card setCardIcon(int cardIcon) {
        this.cardIcon = cardIcon;
        return this;
    }

    @Override
    public void cardAction(Player p1, Player p2) {

    }

    @Override
    public Card clone() {
        return new Cat();
    }
}
