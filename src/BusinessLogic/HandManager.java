package BusinessLogic;

import DomainModel.*;
import DomainModel.Cards.Cat;
import DomainModel.Cards.Cases.FiveCardBundle;
import DomainModel.Cards.Cases.ThreeCardBundle;
import DomainModel.Cards.Cases.TwoCardBundle;

import java.util.ArrayList;
import java.util.List;

public class HandManager{
    private List<Card> hand;
    private List<Card> selectedCards;
    private MainDeck mainDeck;
    private CardStack cardStack;
    
    public HandManager(){
		hand = new ArrayList<Card>();
		selectedCards = new ArrayList<Card>();
		mainDeck = MainDeck.getInstance();
		cardStack = CardStack.getInstance();
	}

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

    public Card draw() {
		Card ret = mainDeck.draw();
		hand.add(ret);
		return ret;
	}

    public void selectCard(int i)  {

		Card toMove = null;
		try{
			toMove = this.hand.remove(i);
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		
		this.selectedCards.add(toMove);
	}

	public void moveSelectedToStack() {
		if (this.selectedCards.size() != 0) {
			if (this.allNormalCards()) {
				if (TwoCardBundle.isValidBundle(selectedCards)) {
					this.cardStack.moveCardsToStack(this.makeBundle(2));
				} else if (ThreeCardBundle.isValidBundle(selectedCards)) {
					this.cardStack.moveCardsToStack(this.makeBundle(3));
				} else if (FiveCardBundle.isValidBundle(selectedCards)) {
					this.cardStack.moveCardsToStack(this.makeBundle(5));
				}
	
			} else {
				this.cardStack.moveCardsToStack(this.selectedCards);
			}
			this.selectedCards.clear();
		}

		
	}

	private List<Card> makeBundle(int sizeOfBundle) {
		ArrayList<Card> toSendToStack = new ArrayList<Card>();

		if (sizeOfBundle == 2) {
			toSendToStack.add(new TwoCardBundle(selectedCards));
		} else if (sizeOfBundle == 3) {
			toSendToStack.add(new ThreeCardBundle(selectedCards));
		} else {
			toSendToStack.add(new FiveCardBundle(selectedCards));
		}
		return toSendToStack;
	}

	private boolean allNormalCards() {
		for (Card card : selectedCards) {
			if (!(card instanceof Cat)) {
				return false;
			}
		}
		return true;
	}

	public void addDefuseCard() {
		this.hand.add(new CardFactory().createCard(C_Type.DEFUSE));
	}

	public void addCards(List<Card> cards) {
		this.hand.addAll(cards);
	}

	public void clearSelectedCards() {
		this.selectedCards.clear();
	}
}