import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import java.net.*;
import java.util.*;
import java.io.*;

public class GameClientTest{
  
  GameServer server1;
  GameServer server2;
  LogicalBoard gameBoard;

  // set up a server for testing against
  @Before
  public void setup(){
    GameServer server1 = new GameServer(2000,"RICK");
    GameServer server2 = new GameServer(2001,"ALSORICK");
    LogicalBoard gameBoard = new LogicalBoard(2);
  }

  /*
  @After
  public void teardown(){
    String[] s = {"GOTE","1"};
    server1.handleGote(s);
  }
  */

  // testing GameClient parameter processing
  @Test
  public void GameClientParameterProcessingTest() throws Exception{

    String[] testArgs = {"localhost:5555","localhost:5554"};

    String[] testProcess = GameClient.processParams(testArgs);

    String[] expectedArray = {"localhost","5555","localhost","5554"};

    assertNotNull("Array should contain values",testProcess);

    assertArrayEquals("Failure - arrays do not match",expectedArray,testProcess);

  }

  //testing the set up of sockets open to servers
  @Test
  public void GamClientSocketSetupTest() throws Exception{

    Thread t = new Thread() { 
      public void handle() {
    	  server1.connect();
    	}
    };
    t.setDaemon(true);
    t.start();

    Thread c1 = new Thread() {
      public void handle() {
        try {
          Socket serverSocket = GameClient.socketSetup("localhost",2000);
          assertNotNull("Socket should exist",serverSocket);
        } catch(Exception e) {}
      }
    };
    c1.setDaemon(true);
    c1.start();
  
  }

  //test getting input stream for server
  @Test
  public void GameClientGetServerInputStreamTest() throws Exception{

    Thread t = new Thread() { 
      public void handle() {
    	  server1.connect();
    	}
    };
    t.setDaemon(true);
    t.start();

    Thread c = new Thread() {
      public void handle() {
        try {
          Socket playerSocket = GameClient.socketSetup("localhost",2000);
          Scanner playerIn = GameClient.getIn(playerSocket);

          assertNotNull("InputStream should exist",playerIn);
        } catch(Exception e) {}
      }
    };
    c.setDaemon(true);
    c.start();
  
  }

  //test getting output stream for player
  @Test
  public void GameClientGetServerOutputStreamTest() throws Exception{

    Thread t = new Thread() { 
      public void handle() {
    	  server1.connect();
    	}
    };
    t.setDaemon(true);
    t.start();

    Thread c = new Thread() {
      public void handle() {
        try {
          Socket playerSocket = GameClient.socketSetup("localhost",2000);
          PrintStream playerOut = GameClient.getOut(playerSocket);

          assertNotNull("InputStream should exist",playerOut);
        } catch(Exception e) {}
      }
    };
    c.setDaemon(true);
    c.start();
  } 

  //test initial contact with servers
  @Test
  public void GameClientInitialServerContactTest() throws Exception{

    Thread t = new Thread() { 
      public void handle() {
    	  server1.connect();
        server2.connect();
    	}
    };
    t.setDaemon(true);
    t.start();

    Thread c = new Thread() {
      public void handle() {
        try {
          Socket player1Socket = GameClient.socketSetup("localhost",2000);
          Socket player2Socket = GameClient.socketSetup("localhost",2001);
          Socket[] players = {player1Socket,player2Socket};
          GameClient.contactServers(players);

        } catch(Exception e) {}
      }
    };
    c.setDaemon(true);
    c.start();
  } 

  //test starting of GUI
  @Test
  @Ignore
  public void GameClientStartGUITest() throws Exception{
    
    int players = 2;
    String[] names = {"RICK","RICK"};
    GUI gui = GameClient.startGUI(names);
    assertNotNull("GUI should exist",gui);
  }

  //test requesting move from server
  @Test
  public void GameClientRequestMoveTest() throws Exception{

    Thread t = new Thread() { 
      public void handle() {
    	  server1.connect();
        server2.connect();
    	}
    };
    t.setDaemon(true);
    t.start();
    
    Thread c = new Thread() {
      public void handle() {
        try {
          Socket player1Socket = GameClient.socketSetup("localhost",2000);
          Socket player2Socket = GameClient.socketSetup("localhost",2001);
          Socket[] players = {player1Socket,player2Socket};
          GameClient.contactServers(players);
          
          String move = GameClient.requestMove(players[0]);

          assertNotNull("Player 1 should have made a move",move);
          assertEquals("Failure - move not expected","TESUJI (4,1)",move);

        } catch(Exception e) {}
      }
    };
    c.setDaemon(true);
    c.start();
  } 
  
  //test broadcasting a winner to players
  @Test
  public void GameClientBroadcastWinnerTest() throws Exception{

    Thread t = new Thread() { 
      public void handle() {
    	  server1.connect();
        server2.connect();
    	}
    };
    t.setDaemon(true);
    t.start();
    
    Thread c = new Thread() {
      public void handle() {
        try {
          Socket player1Socket = GameClient.socketSetup("localhost",2000);
          Socket player2Socket = GameClient.socketSetup("localhost",2001);
          Socket[] players = {player1Socket,player2Socket};
          GameClient.contactServers(players);
          
          GameClient.broadcastWinner(players,1);

          assertNull("Players should be closed",players[0]);
          assertNull("Players should be closed",players[1]);

        } catch(Exception e) {}
      }
    };
    c.setDaemon(true);
    c.start();
  }

  //test broadcasting move to players
  @Test
  public void GameClientBroadcastMove() throws Exception{

    Thread t = new Thread() { 
      public void handle() {
    	  server1.connect();
        server2.connect();
    	}
    };
    t.setDaemon(true);
    t.start();
    
    Thread c = new Thread() {
      public void handle() {
        try {
          Socket player1Socket = GameClient.socketSetup("localhost",2000);
          Socket player2Socket = GameClient.socketSetup("localhost",2001);
          Socket[] players = {player1Socket,player2Socket};
          GameClient.contactServers(players);
          
          String move = Parser.parse(GameClient.requestMove(players[0]));
          GameClient.broadcastMove(players, 1, move);

          assertNotNull("Players should still be open",players[0]);
          assertNotNull("Players should still be open",players[1]);

        } catch(Exception e) {}
      }
    };
    c.setDaemon(true);
    c.start();
  }


  //testing the updating of player turn number
  @Test
  public void GameClientUpdatePlayerNumberTest() throws Exception{

    int player = 1;

    player = GameClient.updateNumber(player);

    assertEquals("Failure - player should be 4",4,player);

    player = GameClient.updateNumber(player);

    assertEquals("Failure - player should be 2",2,player);

    player = GameClient.updateNumber(player);

    assertEquals("Failure - player should be 3",3,player);

    player = GameClient.updateNumber(player);

    assertEquals("Failure - player should be 1",1,player);
  }

}
