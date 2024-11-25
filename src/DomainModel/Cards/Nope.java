package DomainModel.Cards;

import DomainModel.Card;
import DomainModel.Game.Game.Player;

public class Nope extends Card {
    public Nope() {
        this.cardID = 2;
    }
    public Nope(String path) {
        this.imagePath = path;
    }

    @Override
    public void cardAction(Player p1, Player p2) {
        // Should be manage in server ????
        // CardStack.getInstance().counterTopCard();
    }

    @Override
    public Card clone() {
        return new Nope(this.imagePath);
    }
}
