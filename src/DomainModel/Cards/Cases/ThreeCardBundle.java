package DomainModel.Cards.Cases;

import DomainModel.Card;
import DomainModel.Player;

import java.util.ArrayList;
import java.util.List;
import DomainModel.Cards.*;

public class ThreeCardBundle extends CasesAbstract {
    public static final int BUNDLE_SIZE = 3;
    private Class<?> targetCardClass;

    public ThreeCardBundle(List<Card> cards) {
        super(cards, BUNDLE_SIZE);
        targetCardClass = null;
    }

    @Override
    public void cardAction(Player active, Player target) {
        if (targetCardClass != null) {
            // Somehow, prompt active player to name a card from target's hand.
            // If it exists, give it to them. Otherwise, they get nothing.
            List<Card> targetHand = target.getHand();
            int indexOfCardPicked = -1;
            for (Card card : targetHand) {
                if (card.getClass().equals(targetCardClass)) {
                    indexOfCardPicked = targetHand.indexOf(card);
                    break;
                }
            }
            if (indexOfCardPicked > -1) {
                Card picked = targetHand.remove(indexOfCardPicked);
                active.getHand().add(picked);
            }
            // Otherwise, active won't get a card.
        } else {
            System.out.println("First set a Card class on "+ this.getClass() +".setTargetCardClass()");
        }
    }

    /***
     * set the aimed class to try to steal
     * @param cardClass
     */
    public void setTargetCardClass(Class<?> cardClass) {
        targetCardClass = cardClass;
    }

    @Override
    public ThreeCardBundle clone() {
        return new ThreeCardBundle(new ArrayList<Card>(getCards()));
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
