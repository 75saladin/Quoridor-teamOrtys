import java.net.*;
import java.io.*;

/**
  * GameClient class extends the NetworkClient abstract class
  * Does little more than sets up a connection and sends a
  * hello message as of yet.
  */
public class GameClient extends NetworkClient {

  // Default constructor
  // Sets localhost and 12345 as machine and port respectively
  public GameClient(){
     super("localhost",12345);
  }

  public void handleConnection(Socket client) throws IOException{
    try{
      OutputStream cout = client.getOutputStream();
      String message = "Hello\n";
      cout.write(message.getBytes());
      client.close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }

  public static void main(String[] args){
    GameClient gameClient = new GameClient();
    gameClient.connect();
  }
}
