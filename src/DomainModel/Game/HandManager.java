package DomainModel.Game;

import DomainModel.Card;
import DomainModel.MainDeck;

import java.util.List;

public class HandManager{
    private List<Card> hand;
    private List<Card> selectedCards;
    private MainDeck mainDeck;
    private CardStack cardStack;
    

    /**
     * @return List<Card> return the hand
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * @param hand the hand to set
     */
    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    /**
     * @return List<Card> return the selectedCards
     */
    public List<Card> getSelectedCards() {
        return selectedCards;
    }

    /**
     * @param selectedCards the selectedCards to set
     */
    public void setSelectedCards(List<Card> selectedCards) {
        this.selectedCards = selectedCards;
    }

    /**
     * @return MainDeck return the mainDeck
     */
    public MainDeck getMainDeck() {
        return mainDeck;
    }

    /**
     * @param mainDeck the mainDeck to set
     */
    public void setMainDeck(MainDeck mainDeck) {
        this.mainDeck = mainDeck;
    }

    /**
     * @return CardStack return the cardStack
     */
    public CardStack getCardStack() {
        return cardStack;
    }

    /**
     * @param cardStack the cardStack to set
     */
    public void setCardStack(CardStack cardStack) {
        this.cardStack = cardStack;
    }

}