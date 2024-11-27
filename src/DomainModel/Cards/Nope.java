package DomainModel.Cards;

import DomainModel.C_Type;
import DomainModel.Card;
import DomainModel.Player;

public class Nope extends Card {
    public Nope() {
        this.type = C_Type.NOPE;
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
