import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.util.Scanner;

public class ServerNetworkTest {
  
    private ServerNetwork network;
    private Socket testSocket;

    
    @Test
    public void testStartup() {
	network = ServerNetwork.serverFactory(ServerNetwork.DEFAULT_PORT);
	assertNotNull("Problem with serverFactory", network);
	network.close();
    }

    
    @Test
	public void testNetworkInit(){
	network = ServerNetwork.serverFactory(ServerNetwork.DEFAULT_PORT);
	String holder = "";
	try{
	    Socket testSocket = new Socket("localhost", ServerNetwork.DEFAULT_PORT);
	    Scanner reader = new Scanner(testSocket.getInputStream());
	    PrintStream writer = new PrintStream(testSocket.getOutputStream());
	    writer.println("HELLO");
	    writer.flush();
	    holder = reader.nextLine();	    
	}catch(Exception e){
	    e.printStackTrace();
	}finally{
	    assertEquals("Wrong init message!", "IAM ORT", holder);
	    network.close();
	}
    }
    
    
    @Test
    public void testSendMessage(){
	network = ServerNetwork.serverFactory(ServerNetwork.DEFAULT_PORT);
	network.sendMessage("Hello!");
	assertEquals("Problem with sending message", "Hello!", network.test(ServerNetwork.TEST_SEND_MSG_QUEUE));
	network.close();
    }

    //Here there be dragons
    @Ignore
    @Test
    public void testConnection(){
	network = ServerNetwork.serverFactory(ServerNetwork.DEFAULT_PORT);
	try{
	    testSocket = new Socket("localhost", ServerNetwork.DEFAULT_PORT);
	    PrintWriter writer = new PrintWriter(testSocket.getOutputStream());
	    writer.println("HELLO");
	    writer.flush();
	    writer.println("Hello world!");
	    writer.flush();
	    while(!network.hasMessage()){}
	}catch(Exception e){
	    e.printStackTrace();
	}finally{
	    assertEquals("Problem with sending over network!", "Hello world!", network.getMessage());
	}
	network.close();
	try{
	    testSocket.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
}
