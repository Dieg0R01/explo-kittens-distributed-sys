package DomainModel.Cards;

import DomainModel.C_Type;
import DomainModel.Card;
import DomainModel.Game.Player;

public class ExplodingKitten extends Card {
    public ExplodingKitten(){
        this.type = C_Type.EXPLODINGKITTEN;
    }
    public ExplodingKitten(String path){
        this.imagePath = path;
    }

    @Override
    public void cardAction(Player p1, Player p2) {
        // Should be manage in server ????

        //turnManager.makeCurrentPlayerLose();
    }

    @Override
    public Card clone() {
        return null;
    }
}
