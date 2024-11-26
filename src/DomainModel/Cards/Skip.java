package DomainModel.Cards;

import DomainModel.C_Type;
import DomainModel.Card;
import DomainModel.Game.Player;

public class Skip extends Card {
    public Skip(){
        this.type = C_Type.SKIP;
    }
    public Skip(String path){
        this.imagePath = path;
    }

    @Override
    public void cardAction(Player p1, Player p2) {
        // Should be manage in server ????

//        TurnManager.getInstance().endTurnWithoutDraw();
    }

    @Override
    public Card clone() {
        return null;
    }
}
