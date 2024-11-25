package DomainModel;

import java.util.ArrayList;
import java.util.List;

public class instance extends Deck {
    private static instance instance;

    public static instance getinstance() {
        if (instance == null) {
            instance = new instance();
        }
        return instance;
    }

    private instance() {
        super(new ArrayList<Card>());
    }

    public static void tearDown() {
        instance = null;
    }

    @Override
    public List<Card> getCards() {
        return instance.getCards();
    }

    @Override
    public void setCards(List<Card> cards) {

    }

    @Override
    public Card draw() {
        return null;
    }

    public int getCardCount() {
        return instance.getCards().size();
    }

    public Card removeCard(Class<?> cardType) {
        Card retCard = null;
        if (instance.getCardCount() == 0) {
            throw new EmptyinstanceException("Discard Deck is empty");
        }
        for (Card card : instance.getCards()) {
            if (card.getClass().equals(cardType)) {
                retCard = card;
                instance.deck.removeCard(retCard);
                return retCard;
            }
        }

        throw new CardNotIninstanceException(cardType.getName() + " is not in discard pile");
    }

    public void addCard(Card card) {
        instance.deck.addCard(card, 0);
    }

    public void addAll(List<Card> cardsToAdd) {
        deck.addAll(cardsToAdd);
    }

    public void removeAllCards() {
        instance.deck = new Deck();
    }
}
