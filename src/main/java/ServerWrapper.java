import java.util.Queue;
import java.util.LinkedList;
import java.lang.Runnable;
import java.util.concurrent.Semaphore;
import java.lang.InterruptedException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerWrapper implements Runnable{

  private ServerNetwork network;
  private volatile Queue<String> msgQueue = new LinkedList();
  private AtomicBoolean hasMessage = new AtomicBoolean(false);
  private AtomicBoolean running = new AtomicBoolean(true);
  private Semaphore inSem = new Semaphore(1, true);
  private Semaphore outSem = new Semaphore(1, true);
  public static final String SEND_MOVE_FLAG = "TESUJI ";
  private RandomAI AI;
  private Thread t;

  /**
   * This constructor takes a port to open the server on.
   * The constructor then starts up a ServerNetwork object,
   * and starts its own thread.
   *
   * @param port The port number to start the server on
   */
  public ServerWrapper(int port){
    network = ServerNetwork.serverFactory(port);
    t = new Thread(this);
    t.setDaemon(true);
    t.start();
  }

  /**
   * This constructor then starts up a ServerNetwork object,
   * and starts its own thread. This is equivalent to calling
   * ServerNetworkManager(ServerNetwork.DEFAULT_PORT).
   */
  public ServerWrapper(){
    network = ServerNetwork.serverFactory();
    t = new Thread(this);
    t.setDaemon(true);
    t.start();
  }

  /**
   * Takes a row and column, formats them into the proper String,
   * and then sends the formatted String to the client to move a pawn. 
   * This method assumes a standard 9x9 grid board. 
   * This method provides no error checking.
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
    System.out.println("Added to queue");
    return sendStringLiteral(toSend);
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
    return sendStringLiteral(toSend);
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
      outSem.acquire();
      toReturn = network.sendMessage(toSend);
      outSem.release();
    }catch(InterruptedException e){
      e.printStackTrace();
    }
    return toReturn;
  }
    /*
  public boolean hasMessage(){
    return hasMessage.get();
  }

  public String getMessage(){
    String toReturn = null;
    if(hasMessage()){
      try{
        inSem.acquire();
        toReturn = msgQueue.poll();
        if(msgQueue.isEmpty()){
          hasMessage.set(false);
        }
        inSem.release();
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }
    return toReturn;
  }*/

  /**
   * Cleanly closes this server, and shuts down the thread.
   */
  public void close(){
    running.set(false);
    network.close();
    network = null; 
  }

  public String toString(){
    String toReturn = network.toString() + " " + running;
    return toReturn;
  }

  public void run(){
    while(!network.isRunning());
    AI = new RandomAI(network.getNumPlayers(), network.getPlayerNum());
    while(running.get()){
      String tester = "";
      try{
        inSem.acquire();
        if(network.hasMessage()){
          tester = network.getMessage();
          hasMessage.set(true);
        }
        if(!network.isRunning()){
          running.set(false);
        }
        inSem.release();
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }
  }
  
  /**
   * A little mini parser build in to the server wrapper so that it can do
   * stuff with the AI.
   *
   * @param in The input String
   */
  private void miniParse(String in){
    if(in.startsWith("MYOUSHU")){
      String[] arrHold = AI.getMove.split(" ");
      if(arrHold[2].equals("0")){
        sendPawnMove(Integer.parseInt(arrHold[0]), 
                     Integer.parseInt(arrHold[1]));
      }else{
        sendWallMove(Integer.parseInt(arrHold[0]), 
                     Integer.parseInt(arrHold[1]), 
                     Integer.parseInt(arrHold[3]) == 1);
      }
    }else if(in.startsWith("GOTE")){
      if(Character.getNumericValue(in.charAt(5)) == network.getPlayerNum()){
        this.close();
      }else{
        AI.kick(Character.getNumericValue(in.charAt(5)));
      }
    }else if(in.startsWith("KIKASHI")){
      this.close();
    }else if(in.startsWith("ATARI")){
      AI.update(Parser.parse(in));
    }
  }

}