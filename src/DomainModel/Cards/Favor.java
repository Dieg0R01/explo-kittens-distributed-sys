package DomainModel.Cards;

import DomainModel.C_Type;
import DomainModel.Card;
import DomainModel.Player;

public class Favor extends Card {
    public Favor(){
        this.type = C_Type.FAVOR;
    }
    public Favor(String path){
        this.imagePath = path;
    }

    @Override
    public void cardAction(Player p1, Player p2) {
        // Should be manage in server ????

//        Hand hand1 = p1.getHandManager();
//        Hand hand2 = p2.getHandManager();
//        List<Card> cards = hand2.getSelectedCards();
//        hand1.addCards(cards);
//        hand1.clearSelectedCards();
//        hand2.clearSelectedCards();
    }

    @Override
    public Card clone() {
        return null;
    }
}
