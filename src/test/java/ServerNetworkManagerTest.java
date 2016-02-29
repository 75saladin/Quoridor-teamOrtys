import org.junit.Test;

//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.PrintStream;

public class ServerNetworkManagerTest{

    private ServerNetworkManager toTest;
    private Socket testSocket;

    @Test
	public void testStartup(){
	toTest = new ServerNetworkManager(ServerNetwork.DEFAULT_PORT);
	assertNotNull("Problem with constructor!", toTest);
	toTest.close();
    }
    
    @Test
	public void testSendPawnMove(){
    	ServerNetworkManager toTest = new ServerNetworkManager(ServerNetwork.DEFAULT_PORT);
	String holder = "";
	try{
	    Socket testSocket = new Socket("localhost", ServerNetwork.DEFAULT_PORT);
	    Scanner reader = new Scanner(testSocket.getInputStream());
	    PrintStream writer = new PrintStream(testSocket.getOutputStream());
	    writer.println("HELLO");
	    writer.flush();
	    holder = reader.nextLine();
	    toTest.sendPawnMove(1,1);
	    holder = reader.nextLine();
	    reader.close();
	    toTest.close();
	    testSocket.close();
	}catch(Exception e){
	    e.printStackTrace();
	}finally{
	    assertEquals("Problem formatting move!", "TESUJI (1, 1)", holder);
	}
    }

    @Test
	public void testWallMove(){
    	ServerNetworkManager toTest = new ServerNetworkManager(ServerNetwork.DEFAULT_PORT);
	String holder = "";
	try{
	    Socket testSocket = new Socket("localhost", ServerNetwork.DEFAULT_PORT);
	    Scanner reader = new Scanner(testSocket.getInputStream());
	    PrintStream writer = new PrintStream(testSocket.getOutputStream());
	    writer.println("HELLO");
	    writer.flush();
	    holder = reader.nextLine();
	    toTest.sendWallMove(1,1, true);
	    holder = reader.nextLine();
	    reader.close();
	    toTest.close();
	    testSocket.close();
	}catch(Exception e){
	    e.printStackTrace();
	}finally{
	    assertEquals("Problem formatting move!", "TESUJI [(1, 1), h]", holder);
	}
    }
    /* THIS TEST IS BROKEN
    @Test
	public void testRecieve(){
    	ServerNetworkManager toTest = new ServerNetworkManager(ServerNetwork.DEFAULT_PORT);
	String holder = "";
	try{
	    Socket testSocket = new Socket("localhost", ServerNetwork.DEFAULT_PORT);
	    Scanner reader = new Scanner(testSocket.getInputStream());
	    PrintStream writer = new PrintStream(testSocket.getOutputStream());
	    writer.println("HELLO");
	    writer.flush();
	    holder = reader.nextLine();
	    writer.println("KIKASHI 1");
	    writer.flush();
	    reader.close();
	    testSocket.close();
	}catch(Exception e){
	    e.printStackTrace();
	}finally{
	    assertEquals("Problem formatting move!", 1, toTest.hasPlayerWon());
	    toTest.close();
	}	
    }
    */
}