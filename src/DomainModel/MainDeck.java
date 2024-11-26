package DomainModel;

import DomainModel.Cards.Cat;

import java.util.ArrayList;
import java.util.List;

public class MainDeck extends Deck {
    private static MainDeck instance;
    public CardFactory factory;

    private MainDeck() {
        super(new ArrayList<Card>());
        factory = new CardFactory();
    }
    public static MainDeck getInstance() {
        if (instance == null) {
            instance = new MainDeck();
        }
        return instance;
    }
    public static void tearDown() {
        instance = null;
    }

    public Card draw() {
        return cards.isEmpty() ? null : cards.removeFirst();
    }

    public void initStartingDeck() {
        if (instance.getCards().isEmpty()) {
            initFavorCards();
            initShuffleCards();
            initSkipCards();
            initAttackCards();
            initNormalCards();
            initNopeCards();
            initScryCards();
        }
        shuffleDeck();
    }

    private void initFavorCards() {
        for (int i = 0; i < 4; i++) {
            instance.addCard(true, factory.createCard(C_Type.FAVOR));
        }
    }

    private void initShuffleCards() {
        for (int i = 0; i < 4; i++) {
            instance.addCard(true, factory.createCard(C_Type.SHUFFLE));
        }
    }

    private void initSkipCards() {
        for (int i = 0; i < 4; i++) {
            instance.addCard(true, factory.createCard(C_Type.SKIP));
        }
    }

    private void initAttackCards() {
        for (int i = 0; i < 4; i++) {
            instance.addCard(true, factory.createCard(C_Type.ATTACK));
        }
    }

    private void initNormalCards() {
        for (int i = 0; i < 20; i++) {
            instance.addCard(true, ((Cat) factory.createCard(C_Type.CAT)).setCardIcon(i % 4));
        }
    }

    private void initNopeCards() {
        for (int i = 0; i < 5; i++) {
            instance.addCard(true, factory.createCard(C_Type.NOPE));
        }
    }

    private void initScryCards() {
        for (int i = 0; i < 5; i++) {
            instance.addCard(true, factory.createCard(C_Type.SCRY));
        }
    }

    public void populateDeck(int numPlayers) {
        initExplodingKittenCards(numPlayers);
        initDefuseCards(numPlayers);
        shuffleDeck();
    }

    private void initExplodingKittenCards(int numPlayers) {
        for (int i = 0; i < numPlayers - 1; i++) {
            instance.addCard(0, factory.createCard(C_Type.EXPLODINGKITTEN));
        }
    }

    private void initDefuseCards(int numPlayers) {
        for (int i = 0; i < 6 - numPlayers; i++) {
            instance.addCard(0, factory.createCard(C_Type.DEFUSE));
        }
    }

}
