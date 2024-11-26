package DomainModel.Cards;

import DomainModel.C_Type;
import DomainModel.Card;
import DomainModel.Game.Player;

public class ScryThree extends Card {
    public ScryThree(){
        this.type = C_Type.SCRY;
    }
    public ScryThree(String path){
        this.imagePath = path;
    }

    @Override
    public void cardAction(Player p1, Player p2) {
        // Should be manage in server ????

//        int numberOfCardsToScry = 3;
//        cardsToReveal = new ArrayList<>();// top card of deck is at
//        // index 0.
//        MainDeck mainDeck = MainDeck.getInstance();
//        if (mainDeck.getCardCount() < numberOfCardsToScry) {
//            numberOfCardsToScry = mainDeck.getCardCount();
//        }
//        for (int i = 0; i < numberOfCardsToScry; i++) {
//            cardsToReveal.add(mainDeck.getCards().get(i));
//        }
//        // do something with cardsToReveal later, once GUI has been integrated.
    }

    @Override
    public Card clone() {
        return null;
    }
}
