import javafx.scene.layout.Region;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.net.*;
import java.util.*;
import static org.junit.Assert.*;

public class GameServerTest {

	GameServer server1;
	GameServer server2;
	Socket client1;
	Socket client2;
	private PrintWriter out1;
    private BufferedReader in1;
    private PrintWriter out2;
    private BufferedReader in2;


	@Before
    public void setUp() throws Exception {
        server1 = new GameServer(2000, "jed");
        server2 = new GameServer(2001, "bob");

            
    }

    @Test
    public void testHandle() throws IOException {
    	final String msg1 = "";
    	String msg2 = "";
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
    				client1 = new Socket("localhost", 2000);
    				out1 = new PrintWriter(client1.getOutputStream(), true);
        			in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
    				out1.println("HELLO");
    				assertEquals(in1.readLine(), "IAM ort:jed");
    				out1.println("ATARI");
    			} catch(Exception e) {}
    		}
    	};
    	c1.setDaemon(true);
    	c1.start();


    	Thread t2 = new Thread() { 
    		public void handle() {
    			server2.connect();
    		}
    	};


    	t2.setDaemon(true);
    	t2.start();


    	Thread c2 = new Thread() {
    		public void handle() {
    			try {
    				client2 = new Socket("localhost", 2001);
    				out2 = new PrintWriter(client2.getOutputStream(), true);
       	 			in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
    				out2.println("MYOUSHU");
    				assertNotNull(in2.readLine());
    				out2.println("ATARI");
    			} catch(Exception e){}
    		}
    	};
    	c2.setDaemon(true);
    	c2.start();



    }


}