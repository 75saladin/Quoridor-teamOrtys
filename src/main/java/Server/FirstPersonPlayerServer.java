/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Client.Parser;
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
public class FirstPersonPlayerServer extends Server {
private int playerNum; // player Number given to this server by the client
  private String name = ""; // The name of the player. 
  private static String usage = "java GameServer --port <port number> [--name <ai name>]";
  private PlayerGUI gui;


  /**
   * 
   * @param port: Port to connect to.
   * @param name: Name of the server.
   * Constructor
   */
  public FirstPersonPlayerServer(int port, String name) {
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
      System.out.println(playerNum);
      
      Thread t = new Thread() {
      @Override
      public void run() {
        javafx.application.Application.launch(PlayerGUI.class);
      }
      };

      t.setDaemon(true);
      t.start();
      gui = gui.waitForGUIStartUpTest();
      try {
        Thread.sleep(1000);
      } catch(Exception e) {
        
      }
      gui.setPlayer(new FPController(playerNum));

      return;
    } else if(msg.startsWith("MYOUSHU")) { // get a move

        String move = null;
        while(move == null) {
          move = gui.getMove();
        }
        
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
      gui.update(move);
      System.out.println("Saw ATARI");
    } else if(msg.startsWith("KIKASHI")) { // game is over guy won
      try {
        out.close();
        socket.close();
        in.close();
      }catch(IOException e) {
        e.printStackTrace();
      }
    } else if(msg.startsWith("GOTE")) {
      System.out.println("Person kicked");
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

    FirstPersonPlayerServer fp = new FirstPersonPlayerServer(port, name);
    fp.connect();
  }
}
