import java.util.Queue;
import java.util.LinkedList;
import java.lang.Runnable;
import java.util.concurrent.Semaphore;
import java.lang.InterruptedException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

public class ServerNetworkManager implements Runnable{

    private ServerNetwork network;
    private Semaphore networkSemaphore = new Semaphore(1, true);
    private volatile Queue<String> moveQueue = new LinkedList();
    private AtomicBoolean moveRequested = new AtomicBoolean(false);
    private AtomicInteger winner = new AtomicInteger(0);
    private AtomicInteger kickedPlayers = new AtomicInteger(0);
    private AtomicInteger iDNumber;
    private boolean running = true;

    public static final String SEND_MOVE_FLAG = "TESUJI ";
    public static final String REQUEST_FOR_MOVE = "MYOUSHU ";
    public static final String PLAYER_KICKED = "GOTE ";
    public static final String PLAYER_WON = "KIKASHI ";
    public static final String RECIEVE_MOVE_FLAG = "ATARI ";    

    /**
     * This constructor takes a port to open the server on.
     * The constructor then starts up a ServerNetwork object,
     * and starts its own thread.
     *
     * @param port The port number to start the server on
     */
    public ServerNetworkManager(int port){
	network = ServerNetwork.serverFactory(port);
	new Thread(this).start();
    }

    /**
     * This constructor then starts up a ServerNetwork object,
     * and starts its own thread. This is equivalent to calling
     * ServerNetworkManager(ServerNetwork.DEFAULT_PORT).
     */
    public ServerNetworkManager(){
	network = ServerNetwork.serverFactory();
	new Thread(this).start();
    }

    /**
     * Takes a row and column, formats them into the proper String,
     * and then sends the formatted String to the client to move a pawn. 
     * This method assumes a standard 9x9 grid board. This method 
     * assumes no error checking.
     *
     * @param col The column where the pawn is moving to.
     * @param row The row where the pawn is moving to.
     * @return true if it was successfully added to the message queue, 
     *         false otherwise. 
     */
    public boolean sendPawnMove(int col, int row){
	String toSend = SEND_MOVE_FLAG;
	toSend = toSend + "(" + col + ", " + row + ")";
	System.out.println("Formatted: " + toSend);
	// This line has no purpose anymore, but I'm keeping it in
	// For its beauty.
	//return toReturn ? sendStringLiteral(toSend) : toReturn;
	moveRequested.set(!sendStringLiteral(toSend));
	System.out.println("Added to queue");
	return !moveRequested.get();
    }

    /**
     * Takes a row, column and orientation, then formats them into the 
     * proper String, and then sends the formatted String to the client. 
     * This method assumes a standard 9x9 grid board. 
     * This method provides no error checking.
     *
     * @param col The column where the wall is being placed.
     * @param row The row where the wall is being placed.
     * @return true if it was successfully added to the message queue, 
     *         false otherwise. 
     */
    public boolean sendWallMove(int col, int row, boolean isHorizontal){
	String toSend = SEND_MOVE_FLAG;
	toSend = toSend + "[(" + col + ", " + row + "), ";
	toSend = isHorizontal ? toSend + "h]" : toSend + "v]";
	moveRequested.set(!sendStringLiteral(toSend));
	return !moveRequested.get();
    }

    /**
     * Sends a literal String to the Server's message queue.
     *
     * @param toSend a literal String to send to the Server's 
     *               message queue
     * @return true if it was successfully added to the message queue, 
     *         false otherwise. 
     */
    public boolean sendStringLiteral(String toSend){
	boolean toReturn = false;
	try{
	    networkSemaphore.acquire();
	    toReturn = network.sendMessage(toSend);
	    networkSemaphore.release();
	}catch(InterruptedException e){
	    e.printStackTrace();
	}
	return toReturn;
    }

    /**
     * Tells whether or not a move has been requested of this server.
     *
     * @return true if a move has been requested, false otherwise.
     */
    public boolean isMoveRequested(){
	return moveRequested.get();
    }

    /**
     * Returns the numhber of the winning player, or 0 if no one has
     * won yet.
     *
     * @return 0 if no one has won, or the player's number if 
     *         they have won.
     */
    public int hasPlayerWon(){
	return winner.get();
    }

    /**
     * returns a number of players kicked (e.g. if players 
     * 1 and 3 have been kicked, it will return 13, or if 2, 
     * 3, and 4 have been kicked, it will return 234.) The 
     * position of the digits occurs in order of kick, that
     * is, if 3, then 1 got kicked, then the result would be 31.
     *
     * @return the numbers of players that have been kicked
     */
    public int playersKicked(){
	return kickedPlayers.get();
    }

    /**
     * Returns true if the server has recieved a new move. 
     * False otherwise.
     *
     *@return True if the server has recieved a new move. 
     *        False otherwise.
     */
    public boolean hasAMove(){
	return moveQueue.peek() != null;
    }

    /**
     * Returns the String of the next move. See the specifications
     * of the move strings for an explanation. Will return both
     * pawn and wall moves.
     *
     * @return A move String.
     */
    public String getNextMove(){
	return moveQueue.poll();
    }

    /**
     * Cleanly closes this server, and shuts down the thread.
     */
    public void close(){
	running = false;
	network.close();
	network = null;	
    }

    public void run(){
	while (running){
	    String tester = "";
	    boolean hasAThing = false;
	    try{
		networkSemaphore.acquire();
		if(network.hasMessage()){
		    tester = network.getMessage();
		    hasAThing = true;
		}
		networkSemaphore.release();
	    }catch(InterruptedException e){
		e.printStackTrace();
	    }
	    if(hasAThing){
		if(tester.equals(REQUEST_FOR_MOVE)){
		    moveRequested.compareAndSet(false, true);
		}else if(tester.startsWith(PLAYER_KICKED)){
		    int playerNum = Integer.parseInt(Character.toString(tester.trim().charAt(tester.length() - 1)));
		kickedPlayers.set(kickedPlayers.accumulateAndGet(playerNum, new MultiplyByTenAndAdd()));
		}else if(tester.startsWith(PLAYER_WON)){
		    winner.set(Integer.parseInt(Character.toString(tester.trim().charAt(tester.length() - 1))));
		}else if(tester.startsWith(RECIEVE_MOVE_FLAG)){
		    moveQueue.add(tester.trim().substring(tester.indexOf(" ")));
		}

	    }

	}

    }

    private class MultiplyByTenAndAdd implements IntBinaryOperator{
	
	public int applyAsInt(int left, int right){
	    return (left*10 + right);
	}

    }

}