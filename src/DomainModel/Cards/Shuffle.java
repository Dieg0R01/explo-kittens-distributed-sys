package DomainModel.Cards;

import DomainModel.C_Type;
import DomainModel.Card;
import DomainModel.Game.Player;

public class Shuffle extends Card {
    public Shuffle(){
        this.type = C_Type.SHUFFLE;
    }
    public Shuffle(String path){
        this.imagePath = path;
    }

    @Override
    public void cardAction(Player p1, Player p2) {
        // Should be manage in server ????

//        MainDeck mainDeck = MainDeck.getInstance();
//        mainDeck.shuffleDeck();
    }

    @Override
    public Card clone() {
        return null;
    }
}
