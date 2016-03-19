import java.net.*;
import java.io.*;
import java.util.Arrays;

/**
  * GameClient class extends the NetworkClient abstract class
  * The brain of the game. 
  * Controls game logic, tells the gui and board to update.
  * Currently 
  *
  * @author Nicholas Marasco
  */
public class GameClient extends NetworkClient {

  private static final String usage = "usage: GameClient <machine1>:<port number1> "
                                    + "<machine2>:<port number2> \\\n[<machine3>:<port number3> "
                                    + "<machine4>:<port number4>]";

  /**
    * Default constructor
    * Sets machince name and port to default values of localhost and 5555
    */
  public GameClient(){
    super("localhost",5555);
  }

  /**
    * Constructor for machine name/port number pairs.
    * Calls super constructor with given parameters.
    *
    * @param machine the name of the machine to connect to
    * @param port the port number to connect to
    */
  public GameClient(String machine, int port){
    super(machine,port);
  }

  /**
    * Start a 2 player game.
    * Sets up connections for two move servers and executes game function.
    *
    * @param pairs String array of machine names and port numbers
    */
  // Currently not implemented
  private static void run2Player(String[] pairs){
  
  }

  /**
    * Start a 4 player game.
    * Sets up connections for four move servers and executes game function.
    *
    * @param pairs String array of machine names and port numbers
    */
  // Currently not implemented
  private static void run4Player(String[] pairs){
  
  }

  public void handleConnection(Socket client) throws IOException{
    try{
      PrintWriter cout = new PrintWriter(client.getOutputStream(), true);
      BufferedReader cin = new BufferedReader(new InputStreamReader(client.getInputStream()));

      String message = "Hello";
      cout.println(message);

      String receive = cin.readLine();

      System.out.println(receive);

      client.close();
    }
    catch(IOException e){
      e.printStackTrace();
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
  private static String[] processParams(String[] args){
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
     run2Player(pairs);
    else
     run4Player(pairs); 
  }
}
