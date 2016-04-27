/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author jed_lechner
 */
public class 
PlayerServer extends Server {

    private String name = "ort:rick"; // The name of the player. 
    private static String usage = "java GameServer --port <port number> [--name <ai name>]";
    private PrintWriter out;
    private BufferedReader in;
    private Socket client;
    private Scanner sc;
    private int playerNum;


  /**
   * 
   * @param port: Port to connect to.
   * @param name: Name of the server.
   * Constructor
   */
  public PlayerServer(int port, String name) {
    super(port);
    this.name = "ort:" + name;
    sc = new Scanner(System.in);

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
    public void handleHello(){
        out.println("IAM " + name);
    }
    
    /**
     * 
     * @param s The message from the client in a string array.
     * Handles game message.
     */
    public void handleGame(String[] s) {
        playerNum = Integer.parseInt(s[1]);
    }

    /**
     * handles get move message
     */
    public void handleMyoushu(){
        System.out.println("Type in your move");
        System.out.println("(c, r) or [(c, r), v]");
        String move = sc.nextLine();
        Parser.formatMove(move);
        out.println("TESUJI " + move);
    }
    
    /**
     * 
     * @param s The message from the client in a string array.
     * Updates the AI
     */
    public void handleAtari(String[] s) {
 
    } 

    /**
     * handles end of game clean up
     */
    public void handleKikashi() {
        try {
            out.close();
            client.close();
            in.close();
            sc.close();
      }catch(IOException e) {
        e.printStackTrace();
      }
    }
    
    /**
     * 
     * @param s The message split into a string array.
     * Kicks player in AI.
     * Closes server if that person being kicked is you.
     */
    public void handleGote(String[] s) {
        if(Integer.parseInt(s[1]) == playerNum) {
            try {
                client.close();
                out.close();
                in.close();
                System.exit(0);
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
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

    PlayerServer fp = new PlayerServer(port, name);
    fp.connect();
  }
}
