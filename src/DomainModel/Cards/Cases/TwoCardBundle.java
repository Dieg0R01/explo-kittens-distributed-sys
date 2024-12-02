package DomainModel.Cards.Cases;

import DomainModel.Card;
import DomainModel.Cards.Cat;
import DomainModel.Player;

import java.util.ArrayList;
import java.util.List;


public class TwoCardBundle extends CasesAbstract{
    private static final int BUNDLE_SIZE = 2;

    public TwoCardBundle(List<Card> cards) {
        super(cards, BUNDLE_SIZE);
    }

    @Override
    public void cardAction(Player active, Player target) {
        // Somehow, prompt active player to pick a card from target's hand.
        Card targetCard = target.getHand().remove(super.getTargetCardIndex());
        active.getHand().add(targetCard);
    }

    @Override
    public TwoCardBundle clone() {
        return new TwoCardBundle(new ArrayList<Card>(getCards()));
    }

    /***
     * It can be called without the need to create an instance, to verify before constructing any case, only using a list of cards
     * @param cards
     * @return true if it is a valid bundle of cards for a case, or false if it is not
     */
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
