import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import java.net.Socket;
import java.io.PrintWriter;

public class ServerNetworkTest {
  
    private ServerNetwork network;
    private Socket testSocket;

    @Test
    public void testStartup() {
	network = ServerNetwork.serverFactory(1337);
	assertNotNull("Problem with serverFactory", network);
    }
    
    
    @Test
    public void testSendMessage(){
	network = ServerNetwork.serverFactory(1337);
	network.sendMessage("Hello!");
	assertEquals("Problem with sending message", "Hello!", network.test(ServerNetwork.TEST_SEND_MSG_QUEUE));
    }

    @Test
    public void testConnection(){
	network = ServerNetwork.serverFactory(1337);
	try{
	    testSocket = new Socket("localhost", 1337);
	    PrintWriter writer = new PrintWriter(testSocket.getOutputStream());
	    writer.println("Hello world!");
	    writer.flush();
	    while(!network.hasMessage()){}
	}catch(Exception e){
	    e.printStackTrace();
	}finally{
	    assertEquals("Problem with sending over network!", "Hello world!", network.getMessage());
	}
    }
}
