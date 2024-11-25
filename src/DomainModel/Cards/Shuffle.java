package DomainModel.Cards;

import DomainModel.Card;
import DomainModel.Game.Game.Player;

public class Shuffle extends Card {
    public Shuffle(){
        this.cardID = 0;
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
