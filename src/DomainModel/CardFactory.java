package DomainModel;

import DomainModel.Cards.*;

import java.util.ArrayList;
import java.util.List;

public class CardFactory {
    public int attackCount, attackMax = 4;
    public int defuseCount, defuseMax = 6;
    public int explodingKittenCount, explodingKittenMax = 4;
    public int favorCount, favorMax = 4;
    public int nopeCount, nopeMax = 5;
    public int catCount, catMax = 4;
    public int scryCount, scryMax = 5;
    public int shuffleCount, shuffleMax = 4;
    public int skipCount, skipMax = 4;

    public Card createCard(C_Type type) {
        Card card = null;
        switch (type) {
            // CONTEXT: wrapping -> https://stackoverflow.com/questions/48027393/how-to-use-modulus-to-wrap-number-between-1-and-7
            // Ej: catCount = (catCount) % catMax++;
            case CAT -> card = new Cat(sourceImage(C_Type.CAT, (catCount) % catMax++));
            case ATTACK -> card = new Attack(sourceImage(C_Type.ATTACK, (attackCount) % attackMax++));
            case DEFUSE -> card = new Defuse(sourceImage(C_Type.DEFUSE, (defuseCount) % defuseMax++));
            case EXPLODINGKITTEN -> card = new ExplodingKitten(sourceImage(C_Type.EXPLODINGKITTEN, (explodingKittenCount) % explodingKittenMax++));
            case FAVOR -> card = new Favor(sourceImage(C_Type.FAVOR, (favorCount) % favorMax++));
            case NOPE -> card = new Nope(sourceImage(C_Type.NOPE, (nopeCount) % nopeMax++));
            case SKIP -> card = new Skip(sourceImage(C_Type.SKIP, (skipCount) % skipMax++));
            case SHUFFLE -> card = new Shuffle(sourceImage(C_Type.SHUFFLE, (shuffleCount) % shuffleMax++));
            case SCRY -> card = new ScryThree(sourceImage(C_Type.SCRY, (scryCount) % scryMax++));
        }
        return card;
    }
    public List<Card> createCards(C_Type type, int numCards) {
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < numCards; i++) {
            cards.add(createCard(type));
        }
        return cards;
    }

    private String sourceImage(C_Type type, int count) {
        return "images\\"+type.toString().toLowerCase()+count+".png";
    }

}
