import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 * GameClient class controls game logic,
 * sets up server connections and sends updates to board. 
 *
 * @author Nicholas Marasco
 */
public class GameClient{

  private static final String USAGE = "usage: GameClient <machine1>:<port1> "
    + "<machine2>:<port2> \\\n[<machine3>:<port3> "
    + "<machine4>:<port4>]";

  private static final String HELLO = "HELLO";
  private static final String GAME = "GAME";
  private static final String MOVE_REQUEST = "MYOUSHU";
  private static final String MOVE_MADE = "ATARI";
  private static final String PLAYER_WON = "KIKASHI";
  private static final String PLAYER_KICKED = "GOTE";

  /**
   * Start the game logic.
   * Goes through the initial contact, then executes turn order game.
   *
   * @param players array of Socket open to servers 
   */
  public static void runGame(Socket[] players){
    String[] playerNames = contactServers(players);
    GUI gui = startGUI(playerNames); 
    try{ Thread.sleep(200); }
    catch(InterruptedException ie){}
    LogicalBoard gameBoard = new LogicalBoard(players.length); 
    // Start asking for moves
    boolean running = true;
    int index = 0;
    int pNum = 1;
    int winner = 0;
    while(running){
      if (players[index].isClosed()) {
        if (players.length == 2) {
          index ^= 1;
          pNum = (pNum % 2) + 1;
        } 
        else {
          index = (index + 1) % 4;
          pNum = updateNumber(pNum);
        }
        continue;
      }
      System.out.println("REQUESTING MOVE: Player " + (pNum));
      String move = null;
      try{
        move = requestMove(players[index]);
      } 
      catch(NoSuchElementException e){
        System.out.println("PLAYER " + pNum + " HAS CROAKED");
        kickPlayer(players,pNum,index);
        gameBoard.kick(pNum);
        gui.removePlayer(pNum);
        continue;
      }
      System.out.println("GOT MOVE: " + move);
      if(gameBoard.checkValid(pNum,move)){
        broadcastMove(players,pNum,move);
        gui.update(move);
        try{ Thread.sleep(200); }
        catch(Exception e){}
      }
      else{
        //System.out.println("INDEX: " + index);
        System.out.println("CRAIG: THAT MOVE WAS INVALID");
        kickPlayer(players,pNum,index);
        gameBoard.kick(pNum);
        gui.removePlayer(pNum);

      }
      winner = checkWinner(gameBoard,players.length);
      if(winner != 0){
        System.out.println("WINNER: " + winner);
        gui.winGame(winner);
        broadcastWinner(players,winner);
        running = false;
      }
      if(players.length == 2){
        index ^= 1;
        pNum = (pNum % 2) + 1;
      }
      else{
        index = (index + 1) % 4;
        pNum = updateNumber(pNum);
      }
    }
    
    try{ Thread.sleep(5000); 

    } catch(InterruptedException ie){ ie.printStackTrace(); }
    gui.stopApplication();
  }

  /**
   * Start a 2 player game.
   * Sets up connections for two move servers and executes game function.
   *
   * @param pairs String array of machine names and port numbers
   */
  public static void setup2Player(String[] pairs){
    String machine1 = pairs[0];
    String machine2 = pairs[2];
    int port1 = 0;
    int port2 = 0;

    try{
      port1 = Integer.parseInt(pairs[1]);
      port2 = Integer.parseInt(pairs[3]);
    }
    catch(NumberFormatException nfe){
      System.out.println("Error: Invalid port number found.\nCroaking...");
      System.exit(1);
    }

    Socket[] players = new Socket[2];

    players[0] = socketSetup(machine1,port1);
    players[1] = socketSetup(machine2,port2);

    runGame(players);

    closeConnection(players);

  }

  /**
   * Start a 4 player game.
   * Sets up connections for four move servers and executes game function.
   *
   * @param pairs String array of machine names and port numbers
   */
  // Currently not implemented
  public static void setup4Player(String[] pairs){
    String machine1 = pairs[0];
    String machine2 = pairs[2];
    String machine3 = pairs[4];
    String machine4 = pairs[6];
    int port1 = 0;
    int port2 = 0;
    int port3 = 0;
    int port4 = 0;
    try{
      port1 = Integer.parseInt(pairs[1]);
      port2 = Integer.parseInt(pairs[3]);
      port3 = Integer.parseInt(pairs[5]);
      port4 = Integer.parseInt(pairs[7]);
    }
    catch(NumberFormatException nfe){
      System.out.println("Error: Invalid port number found.\nCroaking...");
      System.exit(1);
    }

    Socket[] players = new Socket[4];

    players[0] = socketSetup(machine1,port1);
    players[1] = socketSetup(machine4,port4);
    players[2] = socketSetup(machine2,port2);
    players[3] = socketSetup(machine3,port3);

    runGame(players);

    closeConnection(players);
  }

  /**
   * Handles the broadcasting of valid moves to players.
   *
   * @param players Array of Sockets open to servers/players.
   * @param pNum Number of player that made the move.
   * @param move String of the move made
   */
  public static void broadcastMove(Socket[] players, int pNum, String move){
    String fMove = Parser.formatMove(move);
    for(int i = 0; i < players.length; i++){
      if(!players[i].isClosed())
        getOut(players[i]).println(MOVE_MADE + " " + pNum + " " + fMove);
    }
  }

  /**
   * Handles broadcasting winner to all players.
   *
   * @param players Array of sockets
   * @param winner Number of player winner
   */
  public static void broadcastWinner(Socket[] players, int winner){
    for(int i = 0; i < players.length; i++){
      if(!players[i].isClosed())
        getOut(players[i]).println(PLAYER_WON + " " + winner);
    }
  }

  /**
   * Handles the requesting of a move from the server.
   *
   * @param player Socket of player to request move from
   *
   * @return move made by player
   */
  public static String requestMove(Socket player){
    getOut(player).println(MOVE_REQUEST);
    String mesg = getIn(player).nextLine();
    if(mesg.startsWith("TESUJI"))
      return Parser.parse(mesg);
    return "Error";
  }

  /**
   * Handles checking for a winner.
   *
   * @param board LogicalGameBoard
   * @param players number of player in game
   *
   * @return number of the winning player, 0 if none.
   */
  public static int checkWinner(LogicalBoard board, int players){
    int win = 0;
    for(int i = 1; i <= players; i++){
      if(board.hasWon(i))
        win = i;
    }
    return win;
  }

  /**
   * Handles starting the GUI.
   * Nothing more. Nothing less.
   *
   * @param pNum number of players in game
   */
  public static GUI startGUI(String[] playerNames){
    Thread t = new Thread() {
      @Override
      public void run() {
        javafx.application.Application.launch(GUI.class);
      }
    };

    t.setDaemon(true);
    t.start();

    GUI gui = GUI.waitForGUIStartUpTest();
    Controller player = null;
    if(playerNames.length == 2)
      player = new Controller(2);
    else
      player = new Controller(4);
    gui.setPlayer(player);
    gui.setPlayerArray(playerNames);
    return gui;
  }

  /**
   * Handle initial server contact.
   * Send HELLO and GAME messages.
   *
   * @param players Array of Sockets open to all servers.
   *
   */ //@return String array of player names.
  public static String[] contactServers(Socket[] players){
    String[] playerNames = new String[2];
    if(players.length == 4)
      playerNames = new String[4];

    PrintStream p1out = getOut(players[0]);
    Scanner p1in = getIn(players[0]);
    PrintStream p2out = getOut(players[1]);
    Scanner p2in = getIn(players[1]);
    PrintStream p3out = null;
    Scanner p3in = null;
    PrintStream p4out = null;
    Scanner p4in = null;

    if(players.length == 4){
      p3out = getOut(players[2]);
      p3in = getIn(players[2]);
      p4out = getOut(players[3]);
      p4in = getIn(players[3]);
    }

    p1out.println(HELLO);
    String p1Name = Parser.parse(p1in.nextLine());
    //System.out.println("Player 1 Name: " + p1Name);
    p2out.println(HELLO);
    String p2Name = Parser.parse(p2in.nextLine());
    //System.out.println("Player 2 Name: " + p2Name);
    String p3Name = null;
    String p4Name = null;
    if(players.length == 4){
      p3out.println(HELLO);
      p3Name = Parser.parse(p3in.nextLine());
      //System.out.println("Player 3 Name: " + p3Name);
      p4out.println(HELLO);
      p4Name = Parser.parse(p4in.nextLine());
      //System.out.println("Player 4 Name: " + p4Name);
    }
    if(players.length == 2){
      playerNames[0] = p1Name;
      playerNames[1] = p2Name;
      p1out.println(GAME + " 1 " + p1Name + " " + p2Name);
      p2out.println(GAME + " 2 " + p1Name + " " + p2Name);
    }
    else{
      playerNames[0] = p1Name;
      playerNames[1] = p2Name;
      playerNames[2] = p3Name;
      playerNames[3] = p4Name;
      p1out.println(GAME + " 1 " + p1Name + " " + p3Name + " " + p4Name + " " + p2Name); 
      p2out.println(GAME + " 4 " + p1Name + " " + p3Name + " " + p4Name + " " + p2Name); 
      p3out.println(GAME + " 2 " + p1Name + " " + p3Name + " " + p4Name + " " + p2Name); 
      p4out.println(GAME + " 3 " + p1Name + " " + p3Name + " " + p4Name + " " + p2Name); 
    }
    return playerNames;
  }

  /**
   * Get PrintStream wrapping OutputStream of Socket.
   * Takes care of gross try-catch block stuff.
   *
   * @param player Socket open to a server
   *
   * @return PrintStream wrapper for OutputStream
   */
  public static PrintStream getOut(Socket player){
    try{
      return new PrintStream(player.getOutputStream());
    }
    catch(IOException ioe){
      System.out.println("IOException: " + ioe);
      ioe.printStackTrace();
    }
    return null;
  }

  /**
   * Get Scanner wrapping InputStream of Socket.
   * Takes care of gross try-catch block stuff.
   *
   * @param player Socket open to a server.
   *
   * @return Scanner wrapper for InputStream
   */
  public static Scanner getIn(Socket player){
    try{
      return new Scanner(player.getInputStream());
    }
    catch(IOException ioe){
      System.out.println("IOException: " + ioe);
      ioe.printStackTrace();
    }
    return null;
  }


  /**
   * Handle creation of socket.
   * Creates socket from machine name and port.
   *
   * @param host machine name to connect to
   * @param port port number to connect to
   *
   * @return Socket open to the machine/port pair given or dies
   */
  public static Socket socketSetup(String host, int port) {
    try{
      return new Socket(host, port);
    } catch(UnknownHostException uhe) {
      System.out.println("Unknown host: " + host);
      uhe.printStackTrace();
    } catch(IOException ioe) {
      System.out.println("IOException: " + ioe);
      ioe.printStackTrace();
    }
    return null;
  }

  /**
   * Closes connections to all servers at end of game.
   * 
   * @param players Socket array of connections to close
   */
  public static void closeConnection(Socket[] players){
    for(int i = 0; i < players.length; i++){
      try{
        if(!players[i].isClosed())
          players[i].close();
      }
      catch(IOException ioe){
        System.out.println("IOException: " + ioe);
        ioe.printStackTrace();
      }
    }
  }

  /**
   * Handles the closing of a specific player connection.
   * i.e. when a player is kicked for an invalid move
   *
   * @param players Array of players
   * @param pNum number of player kicked
   */
  public static void kickPlayer(Socket[] players,int pNum,int index){
    for(int i = 0; i < players.length; i++){
      //System.out.println("PLAYER " + i + " CLOSED: " + players[i].isClosed());
      if(!players[i].isClosed())
        getOut(players[i]).println(PLAYER_KICKED + " " + pNum);
    }
    try{
      players[index].close();
    }
    catch(IOException ioe){
      System.out.println("IOException: " + ioe);
      ioe.printStackTrace();
    }
  }

  /**
   * Updates player number.
   *
   * @param p Current player number
   *
   * @return new player number
   */
  public static int updateNumber(int p){
    switch(p){
      case 1:
        return 4;
      case 2:
        return 3;
      case 3:
        return 1;
      case 4:
        return 2;
    }
    return 0;
  }


  /**
   * Handles checks for initial parameters.
   * Checks for proper number of arguments and splits
   * machine/port pairs into an array for easy access
   *
   * @param args String array of arguments to process
   * @return String array of separated machine names and port numbers
   */
  public static String[] processParams(String[] args){
    if(args.length != 2 && args.length != 4){
      System.out.println(USAGE);
      System.out.println("Error: Invalid number of players");
      System.exit(1);
    }
    int index = 0;
    String[] data = new String[args.length * 2];
    for(int i = 0; i < args.length; i++){
      String[] pair = args[i].split(":");
      data[index] = pair[0];
      data[index+1] = pair[1];
      index += 2;
      //System.out.println(Arrays.toString(data)); 
    }
    return data;
  }

  public static void main(String[] args){
    String[] pairs = processParams(args);
    if(pairs.length == 4)
      setup2Player(pairs);
    else
      setup4Player(pairs); 
  }
}
