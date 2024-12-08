package Server.GameLogic;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import BusinessLogic.Game;
import DomainModel.Player;

public class GameSession implements Runnable{
   private Room room;
   private ExecutorService pool;
   private List<Player> players;
   private CountDownLatch startLatch; // To synchronize when all players are ready
   private CyclicBarrier turnsBarrier; // To syncrhonize all player turns 

    public GameSession(Room room){
        this.room = room;
        this.players = room.getPlayersList();
        this.pool = Executors.newFixedThreadPool(players.size());
    }

    @Override
    public void run() {
       
        preparePlayers();

        // Creates the MAIN Game instance
        Game game = new Game();
        game.start(room);
        
       //
    }

    public void preparePlayers(){
        startLatch = new CountDownLatch(players.size());

        for(Player p : players){
            p.setStartLatch(startLatch);
            pool.execute(p);
        }
    }
}
