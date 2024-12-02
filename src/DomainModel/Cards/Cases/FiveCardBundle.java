package DomainModel.Cards.Cases;

import DomainModel.Card;
import DomainModel.Cards.Cat;
import DomainModel.DiscardDeck;
import DomainModel.Player;

import java.util.ArrayList;
import java.util.List;

public class FiveCardBundle extends CasesAbstract{
    public static final int BUNDLE_SIZE = 5;
    private Class<?> targetCardClass;

    public FiveCardBundle(List<Card> cards) {
        super(cards, BUNDLE_SIZE);
        targetCardClass = null;
    }

    @Override
    public void cardAction(Player active, Player target) {
        if (targetCardClass != null) {
            DiscardDeck discardDeck = DiscardDeck.getInstance();

            if (discardDeck.getCardCount() > 0) {
                active.getHand().add(discardDeck.removeCard(targetCardClass));
            }
        } else {
            System.out.println("First set a Card class on "+ this.getClass() +".setTargetCardClass()");
        }

    }

    public void setDiscardDeckType(Class<?> classType) {
        targetCardClass = classType;
    }

    @Override
    public FiveCardBundle clone() {
        return new FiveCardBundle(new ArrayList<>(getCards()));
    }

    public static boolean isValidBundle(List<Card> cards) {
        if (cards == null || cards.size() != BUNDLE_SIZE)
            return false;

        for (Card card : cards) {
            if (!(card instanceof Cat)) {
                return false;
            }
        }

        for (int i = 1; i < cards.size(); i++) {
            Cat firstCard = (Cat) cards.get(i - 1);
            Cat secondCard = (Cat) cards.get(i);
            if (firstCard.getCatIcon() != secondCard.getCatIcon()) {
                return false;
            }
        }

        return true;
    }

    public List<Card> getCardsInBundle() {
        return getCards();
    }
}
