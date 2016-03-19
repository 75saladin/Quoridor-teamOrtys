import java.net.*;
import java.io.*;
import java.util.Arrays;

/**
  * GameClient class controls game logic,
  * sets up server connections and sends updates to board.
  * Currently 
  *
  * @author Nicholas Marasco
  */
public class GameClient{

  private static final String usage = "usage: GameClient <machine1>:<port number1> "
                                    + "<machine2>:<port number2> \\\n[<machine3>:<port number3> "
                                    + "<machine4>:<port number4>]";

  /**
    * Start a 2 player game.
    * Sets up connections for two move servers and executes game function.
    *
    * @param pairs String array of machine names and port numbers
    */
  // Currently not implemented
  public static void start2Player(String[] pairs){

  }

  /**
    * Start a 4 player game.
    * Sets up connections for four move servers and executes game function.
    *
    * @param pairs String array of machine names and port numbers
    */
  // Currently not implemented
  public static void start4Player(String[] pairs){
  
  }

  public void connect(String host, int port) {
    try(Socket client = new Socket(host, port)) {
      handleConnection(client);
    } catch(UnknownHostException uhe) {
      System.out.println("Unknown host: " + host);
      uhe.printStackTrace();
    } catch(IOException ioe) {
      System.out.println("IOException: " + ioe);
      ioe.printStackTrace();
    }
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
     start2Player(pairs);
    else
     start4Player(pairs); 
  }
}
