import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
  * GameClient class controls game logic,
  * sets up server connections and sends updates to board. 
  *
  * @author Nicholas Marasco
  */
public class GameClient{

  private static final String usage = "usage: GameClient <machine1>:<port1> "
                                    + "<machine2>:<port2> \\\n[<machine3>:<port3> "
                                    + "<machine4>:<port4>]";

  /**
    * Start the game logic.
    * Goes through the initial contact, then executes turn order game.
    *
    * @param pNum number of players and number to mod turn counter by to get player
    * @param players array of Socket open to servers 
    */
  public static void runGame(Socket[] players){
    contactServers(players);
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
    //TODO: this
  }

  /**
    * Handle initial server contact.
    * Send HELLO and GAME messages.
    *
    * @param players Array of Sockets open to all servers.
    *
    * @return String array of player names.
    */
  public static void contactServers(Socket[] players){
    PrintStream p1out = getOut(players[0]);
    Scanner p1in = getIn(players[0]);
    PrintStream p2out = getOut(players[1]);
    Scanner p2in = getIn(players[1]);

    p1out.println("HELLO");
    String p1Name = Parser.parse(p1in.nextLine());
    System.out.println("TEST: P1Name: " + p1Name);
    p2out.println("HELLO");
    String p2Name = Parser.parse(p2in.nextLine());
    System.out.println("TEST: P2Name: " + p2Name);
    p1out.println("GAME 1 " + p1Name + " " + p2Name);
    p2out.println("GAME 2 " + p1Name + " " + p2Name);
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
        getOut(players[i]).println("KIKASHI " + (i+1));
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
    * @param player Socket of a player to close
    */
  public static void closeConnection(Socket player){
    try{
      player.close();
    }
    catch(IOException ioe){
      System.out.println("IOException: " + ioe);
      ioe.printStackTrace();
    }
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
      System.out.println(usage);
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
      System.out.println(Arrays.toString(data)); 
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
