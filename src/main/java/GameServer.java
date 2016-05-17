

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
    private String name = "otr:morty"; // The name of the player. 
    private static String usage = "java GameServer --port <port number> [--name <ai name>]";
    private PrintWriter out;
    private BufferedReader in;
    private Socket client;


  /**
   * 
   * @param port: Port to connect to.
   * @param name: Name of the server.
   * Constructor
   */
  public GameServer(int port, String name) {
    super(port);
    this.name = "ort:" + name;

  } 

    /**
    * 
    * @param socket: The socket the client connects to.
    * @throws IOException 
    * Handles incoming IO. Listens for a message then passes to handle message.
    */
    @Override 
    public void handle(Socket socket) throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        client = socket;

        String msg = "";
        while(true) {
            if(socket == null)
                System.exit(0);
            msg = in.readLine();
            
            System.out.println("Message from client " + msg);
            handleMessage(msg);
        }
  }

  /**
   * 
   * @param msg: The message from the client.
   * Handle the specific protocol messages. Denoted by capital words. 
   */
    private void handleMessage(String msg) {
        msg = Parser.stripBrackets(msg); // strip the brackets from the message
        String [] s = msg.split(" "); // splits the string by spaces
    
        switch(s[0]) {
            case "HELLO":
                handleHello();
                break;
            case "GAME":
                handleGame(s);
                break;
            case "MYOUSHU":
                handleMyoushu();
                break;
            case "ATARI":
                handleAtari(s);
                break;
            case "KIKASHI":
                handleKikashi();
                break;
            case "GOTE":
                handleGote(s);
                break;
        }
    }
    
    /**
     * Handles the hello message from the client.
     */
    private void handleHello(){
        out.println("IAM " + name);
    }
    
    /**
     * 
     * @param s The message from the client in a string array.
     * Handles game message.
     */
    private void handleGame(String[] s) {
        this.playerNum = Integer.parseInt(s[1]);
        if(s.length == 4) {
            AI = new RandomAI(2, playerNum); // set the random AI
        }else {
            AI = new RandomAI(4, playerNum);
        }
    }

    /**
     * handles get move message
     */
    private void handleMyoushu(){
        String move = AI.getMove();
        move = Parser.formatMove(move);
        //try {
            //Thread.sleep(250);
        //} catch(Exception e) {
            //System.out.println(e);
        //}
        out.println("TESUJI " + move);
    }
    
    /**
     * 
     * @param s The message from the client in a string array.
     * Updates the AI
     */
    private void handleAtari(String[] s) {
        int player = Integer.parseInt(s[1]);
        String move = s[2] +" "+ s[3];
        if(s.length==5){      
            move = move + " " + s[4];
        }
        AI.update(player,move);
    } 

    /**
     * handles end of game clean up
     */
    private void handleKikashi() {
        try {
            out.close();
            client.close();
            in.close();
            AI = null;
            System.exit(0);
      }catch(IOException e) {
        //e.printStackTrace();
      }
    }
    
    /**
     * 
     * @param s The message split into a string array.
     * Kicks player in AI.
     * Closes server if that person being kicked is you.
     */
    private void handleGote(String[] s) {
        AI.kick(Integer.parseInt(s[1]));
        if(Integer.parseInt(s[1]) == playerNum) {
            try {
                client.close();
                out.close();
                in.close();
                AI = null;
                //
            }catch(IOException e) {
                //e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param args
     */
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
