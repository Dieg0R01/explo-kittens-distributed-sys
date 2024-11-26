package DomainModel;

import DomainModel.Game.Player;

import java.util.List;
import java.util.Stack;

public class CardStack {
    private static CardStack cardStack;
    private Stack<Card> stack;

    MainDeck mainDeck = MainDeck.getInstance();
    DiscardDeck discardDeck = DiscardDeck.getinstance();

    private CardStack() {
        stack = new Stack<>();
    }
    public static CardStack getInstance() {
        if (cardStack == null) {
            cardStack = new CardStack();
        }
        return cardStack;
    }
    public static void tearDown() {
        cardStack = null;
    }

    public void addCard(Card card) {
        stack.add(card);
    }

    public void resolveTopCard() {
        stack.pop().cardAction(null, null);
    }

    public void resolveTopCard(Player player1, Player player2) {
        stack.pop().cardAction(player1, player2);
    }

    public void moveCardsToDiscardDeck() {
        discardDeck.addAll(stack);
        stack.clear();
    }

    public Stack<Card> getStack() {
        return (Stack<Card>) stack.clone();
    }

    public Card peek() {
        return stack.peek().clone();
    }

    public void setStack(Stack<Card> stack) {
        this.stack = stack;
    }

    public void moveCardsToStack(List<Card> cardsToMove) {
        stack.addAll(cardsToMove);
    }

    public void counterTopCard() {
        if (stack.isEmpty() && stack.peek().getType() != C_Type.EXPLODINGKITTEN
                && stack.peek().getType() != C_Type.DEFUSE) {
            stack.pop();
        }
        System.out.println("Invalid Nope Target");
    }
}
