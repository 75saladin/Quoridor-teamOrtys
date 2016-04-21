

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 
 * @author jed_lechner
 * Main server to listen for connects. Handles all incoming game messages for 
 * Quoridor. 
 */

public class GameServer extends Server {

  private int playerNum; // player Number given to this server by the client
  private RandomAI AI; // The AI to get a move from.
  private String name = ""; // The name of the player. 
  private static String usage = "java GameServer --port <port number> [--name <ai name>]";


  /**
   * 
   * @param port: Port to connect to.
   * @param name: Name of the server.
   * Constructor
   */
  public GameServer(int port, String name) {
    super(port);
    this.name = "ort:" + name;
    System.out.println("In the constructor");

  } 

  /**
   * 
   * @param socket: The socket the client connects to.
   * @throws IOException 
   * Handles incoming IO. Listens for a message then passes to handle message.
   */
  @Override 
  public void handle(Socket socket) throws IOException {
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    String msg = "";
    System.out.println("In handle");
    while(true) {
      msg = in.readLine();
      if(socket == null)
        System.exit(0);
      System.out.println("Message from client " + msg);
      handleMessage(msg, out, socket,in);
    }
  }

  /**
   * 
   * @param msg: The message from the client.
   * @param out: The printwriter to send messages back to the client. 
   * @param socket: The socket to close upon a certain message.
   * Handle the specific protocol messages. Denoted by capital words. 
   */
  private void handleMessage(String msg, PrintWriter out, Socket socket,BufferedReader in) {
    Scanner sc = new Scanner(System.in);
    msg = Parser.stripBrackets(msg); // strip the brackets from the message
    String [] s = msg.split(" "); // splits the string by spaces

    if(msg.startsWith("HELLO")) { // handle hello
      System.out.println("Sending IAM " + name + " to client");
      out.println("IAM " + name);
    } else if(msg.startsWith("GAME")) { // get the game message from client
      String[] temp = msg.split(" ");
      this.playerNum = Integer.parseInt(s[1]);
      if(temp.length == 4) {
        AI = new RandomAI(2, playerNum); // set the random AI
      }else {
        AI = new RandomAI(4, playerNum);
      }
      return;
    } else if(msg.startsWith("MYOUSHU")) { // get a move
      try{
        Thread.sleep(1000); // here temporarily for move
      } catch(Exception e) {
        e.printStackTrace();
      }
      String move = AI.getMove(); // get a random move from the AI
      move = Parser.formatMove(move);
      System.out.println("Sending TESUJI " + move);
      out.println("TESUJI " + move);
    } else if(msg.startsWith("ATARI")) {
      // only for reading move moves will not handle wall placement
      String temp[] = msg.split(" ");
      int player = Integer.parseInt(temp[1]);
      String move = temp[2] +" "+ temp[3];
      if(temp.length==5){
        System.out.println("here");
        move = move + " " + temp[4];
      }
      AI.update(player,move);
      System.out.println("Saw ATARI");
    } else if(msg.startsWith("KIKASHI")) { // game is over guy won
      try {
        out.close();
        socket.close();
        in.close();
        AI = null;
      }catch(IOException e) {
        e.printStackTrace();
      }
    } else if(msg.startsWith("GOTE")) {
      System.out.println("Person kicked");
      AI.kick(Integer.parseInt(s[1]));
      if(Integer.parseInt(s[1]) == playerNum) {
        try {
          socket.close();
          out.close();
        
          in.close();

          AI = null;
          System.exit(0);
      }catch(IOException e) {
        e.printStackTrace();
      }
      }
      return;
    } else 
      return;
  }


  public static void main(String[] args) {
    System.out.println(usage);
    int port = 6969;
    String name = "RICK";

    for(int i = 1; i < args.length; i++) {
      if(args[i-1].equals("--port")){
        port = Integer.parseInt(args[i]);
      } else if(args[i-1].equals("--name")){
        name = args[i];
      }
    }

    GameServer s = new GameServer(port, name);
    s.connect();
  }
}
