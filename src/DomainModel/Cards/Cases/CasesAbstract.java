package DomainModel.Cards.Cases;

import DomainModel.C_Type;
import DomainModel.Card;

import java.util.List;

public abstract class CasesAbstract extends Card implements Cloneable {
    private List<Card> cards;
    private int targetCardIndex;
    protected final int BUNDLE_SIZE;

    public CasesAbstract(List<Card> cards, int bundleSize) {
        this.cards = cards;
        BUNDLE_SIZE = bundleSize;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setTargetCardIndex(int index) {
        targetCardIndex = index;
    }

    public int getTargetCardIndex() {
        return targetCardIndex;
    }

}
