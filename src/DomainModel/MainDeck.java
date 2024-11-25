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

    @Override
    public void setCards(List<Card> cards) {
        this.cards = cards; // Puedes personalizar esta lógica según sea necesario
    }

    @Override
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
            instance.insertCard(factory.createCard(CardFactory.FAVOR_CARD), true);
        }
    }

    private void initShuffleCards() {
        for (int i = 0; i < 4; i++) {
            instance.insertCard(factory.createCard(CardFactory.SHUFFLE_CARD), true);
        }
    }

    private void initSkipCards() {
        for (int i = 0; i < 4; i++) {
            instance.insertCard(factory.createCard(CardFactory.SKIP_CARD), true);
        }
    }

    private void initAttackCards() {
        for (int i = 0; i < 4; i++) {
            instance.insertCard(factory.createCard(CardFactory.ATTACK_CARD), true);
        }
    }

    private void initNormalCards() {
        for (int i = 0; i < 20; i++) {
            instance.insertCard(((Cat) factory.createCard(CardFactory.CAT_CARD)).setCardIcon(i % 4), true);
        }
    }

    private void initNopeCards() {
        for (int i = 0; i < 5; i++) {
            instance.insertCard(factory.createCard(CardFactory.NOPE_CARD), true);
        }
    }

    private void initScryCards() {
        for (int i = 0; i < 5; i++) {
            instance.insertCard(factory.createCard(CardFactory.SCRY_CARD), true);
        }
    }

    public void populateDeck(int numPlayers) {
        initExplodingKittenCards(numPlayers);
        initDefuseCards(numPlayers);
        shuffleDeck();
    }

    private void initExplodingKittenCards(int numPlayers) {
        for (int i = 0; i < numPlayers - 1; i++) {
            instance.insertCard(factory.createCard(CardFactory.EXPLODING_KITTEN_CARD), 0);
        }
    }

    private void initDefuseCards(int numPlayers) {
        for (int i = 0; i < 6 - numPlayers; i++) {
            instance.insertCard(factory.createCard(CardFactory.DEFUSE_CARD), 0);
        }
    }

}
