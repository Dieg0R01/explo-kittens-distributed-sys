package DomainModel.Cards;

import DomainModel.Card;
import DomainModel.Game.Player;

public class Defuse extends Card {

//    CardStack stack;
//    TurnManager tm;
//    MainDeck mDeck;
//    CardFactory factory;
//    DiscardDeck dDeck;

    public Defuse() {
        this.cardID = 1;
    }
    public Defuse(String path) {
        this();
        this.imagePath = path;
    }

    @Override
    public void cardAction(Player p1, Player p2) {
        // Should be manage in server ????

//        stack = CardStack.getInstance();
//        tm = TurnManager.getInstance();
//        mDeck = MainDeck.getInstance();
//        factory = new CardFactory();
//        dDeck = DiscardDeck.getInstance();
//
//        if (stack.getStack().isEmpty()) {
//            tm.getCurrentPlayer().addDefuseCardToHand();
//            return;
//        }
//
//        if (stack.getStack().elementAt(0).getID() == 5) {
//            stack.setStack(new Stack<Card>());
//            mDeck.insertCard(factory.createCard(CardFactory.EXPLODING_KITTEN_CARD), mDeck.getCardCount() - 1);
//            dDeck.addCard(factory.createCard(CardFactory.DEFUSE_CARD));
//        } else {
//            tm.getCurrentPlayer().addDefuseCardToHand();
//        }
    }

    @Override
    public Card clone() {
        return null;
    }
}
