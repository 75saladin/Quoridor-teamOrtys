

import java.io.*;
import java.net.*;

/**
 * 
 * @author jed_lechner
 * Abstract class to extend for connection to server. 
 */

public abstract class Server {

	private int port; // port to connect to 
        private Socket client;
	
        /**
         * 
         * @param port the port to broadcast on
         * constructor
         */
	public Server(int port) {
		this.port = port;
	}	

        /**
         * Connect method for clients to connect to. Loop till a client connects.
         * Server socket closes.
         */
	public void connect() {
		try(ServerSocket socket = new ServerSocket(port)) {
			while(true) {
				System.out.println("Waiting for a connection");
				client = socket.accept();
				handle(client);
				client.close();
			}
		} catch(IOException e) {
			//e.printStackTrace();
		}
	}

        /**
         * 
         * @param client Socket the client connects to.
         * @throws IOException 
         * 
         * handles incoming IO
         */
	public abstract void handle(Socket client) throws IOException;
}
