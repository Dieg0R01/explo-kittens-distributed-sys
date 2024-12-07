package Server.GameLogic;

import BusinessLogic.Game;

public class GameSession implements Runnable{
   private final Room room;
   
    public GameSession(Room room){
        this.room = room;
    }

    @Override
    public void run() {
        
    }
}
