package DomainModel.Cards;

import DomainModel.C_Type;
import DomainModel.Card;
import DomainModel.Game.Player;

public class Attack extends Card {
    public Attack() {
        this.type = C_Type.ATTACK;
    }
    public Attack(String path){
        this();
        this.imagePath = path;
    }

    @Override
    public void cardAction(Player p1, Player p2) {
        // Should be manage in server ????

//        TurnManager.getInstance().endTurnWithoutDrawForAttacks();
//        TurnManager.getInstance().addTurnForCurrentPlayer();
    }

    @Override
    public Card clone() {
        return null;
    }
}
