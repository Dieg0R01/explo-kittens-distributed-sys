package DomainModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import BusinessLogic.HandManager;
import BusinessLogic.PlayerManager;

public class Player implements Serializable, Runnable {
    private String name;
    private Socket socket;
    private HandManager hand;
    private PlayerManager playerManager;
    private boolean hasPriority;
    private boolean continuePlaying = false;

    private CountDownLatch startLatch;


    private BufferedReader in;
    private PrintStream out;


    /**
     * Constructor for a Player without a name
     */
    public Player() {
		this.name = "default";
		hand = new HandManager();
        this.socket = null;
        
            try {
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());     // input stream reader from socket
                in = new BufferedReader(isr);
                out = new PrintStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }  
        
	}

    /**
     * Constructor for a Player without name "name"
     */
	public Player(String name, Socket s) {
		this.name = name;
        hand = new HandManager();
        this.socket = s;

        try {
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());     // input stream reader from socket
            in = new BufferedReader(isr);
            out = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }  
	}

    @Override
    public void run() {
        // This Player Runnable executes when a Player starts a Game
        out.println("Hello_server");
        do{
            playTurn();
        }while(continuePlaying);

        // Se acaba la partida
    }


    public void playTurn(){
        setUpPlayer();
        
        
            
        
    }

    public void setUpPlayer(){
        
    }
    public void startLatchCountDown(){
        startLatch.countDown();
    }
    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return HandManager return the handManager
     */
    public HandManager getHandManager() {
        return hand;
    }

    public List<Card> getHand(){
        return hand.getHand();
    }

    /**
     * @param handManager the handManager to set
     */
    public void setHandManager(HandManager handManager) {
        this.hand = handManager;
    }

    /**
     * @return PlayerManager return the playerManager
     */
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    /**
     * @param playerManager the playerManager to set
     */
    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    
    public Socket getSocket(){
        return socket;
    }

    public void setSocket(Socket s){
        this.socket = s;
    }

    /**
     * @return boolean return the hasPriority
     */
    public boolean isHasPriority() {
        return hasPriority;
    }

    /**
     * @param hasPriority the hasPriority to set
     */
    public void setHasPriority(boolean hasPriority) {
        this.hasPriority = hasPriority;
    }

    public Card drawCard(){
        return hand.draw();
    }

    public void addDefuseCardToHand(){
        this.hand.addDefuseCard();
    }

    public void setStartLatch(CountDownLatch latch){
        this.startLatch = latch;
    }

    
    
}