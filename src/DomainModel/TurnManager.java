package DomainModel;

import DomainModel.Game.Player;
import DomainModel.Game.PlayerManager;

import java.util.ArrayList;
import java.util.List;

public class TurnManager {
    private static TurnManager turnManager;
    private Player currentPlayer;
    private PlayerManager playerManager;
    private ArrayList<Player> turnOrder;// current player is at index 0

    protected TurnManager() {
        turnOrder = new ArrayList<>();
    }

    protected TurnManager(int n) {// this should only be called when
        // Initializing TurnManagerLogger
    }
    public static TurnManager getInstance() {
        if (turnManager == null) {
            turnManager = new TurnManager();
        }
        return turnManager;
    }
    public static void tearDown() {
        turnManager = null;
    }

    public void setPlayerManager(PlayerManager pm) {
        Player temp = currentPlayer;
        playerManager = pm;
        List<Player> players = playerManager.getPlayers();
        turnOrder.addAll(players);
        if (temp == null) {
            currentPlayer = turnOrder.get(0);
        } else {
            currentPlayer = temp;
        }
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void endTurnAndDraw() {
        Player player = turnOrder.remove(0);
        Card drawnCard = player.drawCard();
        if (drawnCard.getType() == C_Type.EXPLODINGKITTEN) {
            CardStack.getInstance().addCard(drawnCard);
            player.getHandManager().selectCard(player.getHand().indexOf(drawnCard));
            for (Card card : player.getHand()) {
                if (card.getType() == C_Type.DEFUSE) {
                    player.getHandManager().selectCard(player.getHand().indexOf(card));

                    player.getHandManager().moveSelectedToStack();
                    break;
                }
            }
            PriorityManager.getInstance().resolveCard();
        }
        if (!turnOrder.isEmpty() && !turnOrder.getLast().equals(player)) {
            turnOrder.add(player);
        }
        if (turnOrder.isEmpty()) {
            System.out.println("Game over!");
        } else {
            currentPlayer = turnOrder.getFirst();
        }
    }

    public void endTurnWithoutDraw() {
        Player player = turnOrder.remove(0);
        boolean nextTurnIsSamePlayer = turnOrder.get(0).equals(player);
        if (!nextTurnIsSamePlayer) {
            turnOrder.add(player);
        }
        currentPlayer = turnOrder.get(0);
    }

    public void endTurnWithoutDrawForAttacks() {
        Player player = turnOrder.remove(0);
        boolean nextTurnIsSamePlayer = turnOrder.get(0).equals(player);
        if (turnOrder.get(0).equals(player)) {
            turnOrder.remove(0);
        }
        if (!nextTurnIsSamePlayer) {
            turnOrder.add(player);
        }
        currentPlayer = turnOrder.get(0);
    }

    public void addTurnForCurrentPlayer() {
        turnOrder.add(1, currentPlayer);
    }

    public void makeCurrentPlayerLose() {
        playerManager.removePlayerFromGame(currentPlayer);
        PriorityManager.getInstance().removePlayer(currentPlayer);
        removeAllInstancesFromTurnOrder();
    }

    private void removeAllInstancesFromTurnOrder() {
        for (int i = turnOrder.size() - 1; i >= 0; i--) {
            Player checkPlayer = turnOrder.get(i);
            if (checkPlayer.equals(currentPlayer)) {
                turnOrder.remove(checkPlayer);
            }
        }
    }

    public List<Player> getTurnOrder() {
        return turnOrder;
    }
}
