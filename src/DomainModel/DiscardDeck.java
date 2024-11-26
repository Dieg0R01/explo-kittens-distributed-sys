package DomainModel;

import java.util.ArrayList;
import java.util.List;

public class DiscardDeck extends Deck {
    private static DiscardDeck instance;

    private DiscardDeck() {
        super(new ArrayList<Card>());
    }
    public static DiscardDeck getinstance() {
        if (instance == null) {
            instance = new DiscardDeck();
        }
        return instance;
    }
    public static void tearDown() {
        instance = null;
    }

}
