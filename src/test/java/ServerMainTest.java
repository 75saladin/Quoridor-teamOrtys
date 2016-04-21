import Server.ServerMain;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.util.Scanner;

public class ServerMainTest {

    private ServerMain main;
    @Ignore
    @Test
    public void testNetworkStartup(){
	String[] toPass = new String[2];
	toPass[0] = "--port";
	toPass[1] = "4321";
	main.main(toPass);
	assertEquals("Main not initialized correctly!", "4321 IAM ORT true", main.getNetwork().toString());
	main.getNetwork().close();
    }

}
