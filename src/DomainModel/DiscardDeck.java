package DomainModel;

import java.util.ArrayList;

public class DiscardDeck extends Deck {
    private static DiscardDeck instance;

    private DiscardDeck() {
        super(new ArrayList<Card>());
    }
    public static DiscardDeck getInstance() {
        if (instance == null) {
            instance = new DiscardDeck();
        }
        return instance;
    }
    public static void tearDown() {
        instance = null;
    }

    /***
     * Removes the first occurrence of the specified type of Card from this list, if it is present
     * @param cardType
     * @return the card if it was removed successfully
     */
    public Card removeCard(Class<?> cardType) {
        for (Card card : instance.getCards()) {
            if (card.getClass().equals(cardType)) {
                instance.removeCard(card);
                return card;
            }
        }
        return null;
    }

}
