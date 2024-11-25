package DomainModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Deck {
    protected List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<Card>();
    }
    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    /***
     * Inserts the specified element Card at the specified position in this list Deck
     * @param index int
     * @param card Card
     * @return true if the insert was successful, false if not
     */
    public boolean addCard(int index, Card card) {
        try{
            this.cards.add(index, card);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /***
     * Inserts the specified element Card at the top (true) or bottom (false) in this list Deck
     * @param atStart boolean
     * @param card Card
     * @return true if the insert was successful, false if not
     */
    public boolean addCard(boolean atStart, Card card) {
        return addCard(atStart ? 0 : cards.size(), card);
    }

//    public boolean addAll(List<Card> cardsToAdd) {
//        return this.cards.addAll(cardsToAdd);
//    }

    /***
     * Removes the first occurrence of the specified element Card from this list, if it is present
     * @param card Card
     * @return true if the card was contained and lately removed, false if wasn't successfully or wasn't contained
     */
    public boolean removeCard(Card card) {
        return this.cards.remove(card);
    }

    public int getCardCount() {
        return this.cards.size();
    }

    public abstract List<Card> getCards();

    public abstract void setCards(List<Card> cards);

    public void insertCard(Card card, int position) {
        addCard(position, card);
    }

    public abstract Card draw();

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }


}
