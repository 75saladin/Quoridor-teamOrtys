import java.net.*;
import java.io.*;
import java.util.Arrays;

/**
  * GameClient class controls game logic,
  * sets up server connections and sends updates to board. 
  *
  * @author Nicholas Marasco
  */
public class GameClient{

  private static final String usage = "usage: GameClient <machine1>:<port number1> "
                                    + "<machine2>:<port number2> \\\n[<machine3>:<port number3> "
                                    + "<machine4>:<port number4>]";

  /**
    * Start the game logic.
    * Goes through the initial contact, then executes turn order game.
    *
    * @param mod number of players and number to mod turn counter by to get player
    * @param players array of <code>Socket</code> open to servers 
    */
  public static void runGame(int mod, Socket[] players){
    // TODO: Initial contact with servers and play game
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

    runGame(2,players);

    closeConnections(players);

  }

  /**
    * Start a 4 player game.
    * Sets up connections for four move servers and executes game function.
    *
    * @param pairs String array of machine names and port numbers
    */
  // Currently not implemented
  public static void setup4Player(String[] pairs){
  
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
    try(Socket client = new Socket(host, port)) {
      return client;
    } catch(UnknownHostException uhe) {
      System.out.println("Unknown host: " + host);
      uhe.printStackTrace();
    } catch(IOException ioe) {
      System.out.println("IOException: " + ioe);
      ioe.printStackTrace();
    }
    return null;
  }

  public static void closeConnections(Socket[] players){
    for(Socket p : players){
      try{
        p.close();
      }
      catch(IOException ioe){
        System.out.println("IOException: " + ioe);
        ioe.printStackTrace();
      }
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
