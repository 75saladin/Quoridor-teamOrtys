import java.io.*;
import java.net.*;

public abstract class Server {

	private int port;
	
	public Server(int port) {
		System.out.println("Starting GamerServer on port " + port);
		this.port = port;
	}	

	public void connect() {
		try(ServerSocket socket = new ServerSocket(port)) {
			Socket client;
			while(true) {
				System.out.println("Waiting for a connection");
				client = socket.accept();
				handle(client);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void handle(Socket client) throws IOException;
}