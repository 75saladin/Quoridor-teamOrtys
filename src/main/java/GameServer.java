import java.io.*;
import java.net.*;
import java.util.*;


public class GameServer extends Server {

	int playerNum;
	RandomAI AI;
	String name;
	
	public GameServer(int port, String name) {
		super(port);
		this.name = name;
        System.out.println("In the constructor");
		
	}	

	@Override 
	public void handle(Socket socket) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		String msg = "";
                System.out.println("In handle");
		while(true) {
			msg = in.readLine();
			System.out.println("Message from client " + msg);
			handleMessage(msg, out, socket);
		}

	}

	public void handleMessage(String msg, PrintWriter out, Socket socket) {
		Scanner sc = new Scanner(System.in);
		msg = Parser.stripBrackets(msg);
		String [] s = msg.split(" ");
		if(msg.startsWith("HELLO")) {
			System.out.println("Sending IAM " + name + "to client");
			out.println("IAM " + name);
		} else if(msg.startsWith("GAME")) {
			if(s.length == 4) {
				playerNum = Integer.parseInt(s[1]);
				AI = new RandomAI(2, playerNum);
			} else {
				playerNum = Integer.parseInt(s[1]);
				AI = new RandomAI(2, playerNum);
			}
			return;
			
		} else if(msg.startsWith("MYOUSHU")) {
			try{	

				Thread.sleep(2000);
			} catch(Exception e) {
				e.printStackTrace();
			}

			String move = sc.next();
			System.out.println("Sending TESUJI " + move);
			out.println("TESUJI " + move);
		} else if(msg.startsWith("ATARI")) {
			// update the AI
			return;
		} else if(msg.startsWith("KIKASHI")) { // game is over guy won
			try {
				out.close();
				socket.close();
				AI = null;
			}catch(IOException e) {
				e.printStackTrace();
			}
		} else if(msg.startsWith("GOTE")) {
			// update AI with kicked player
		} else {
			return;
		}
	}

	public static void main(String[] args) {
		int port = 6969;
		String name = "";
		Scanner sc = new Scanner(System.in);
		name = sc.next();
		for(int i = 1; i < args.length; i++) {
			if(args[i-1].equals("--port")){
				port = Integer.parseInt(args[i]);
			}
		}
                
		GameServer s = new GameServer(port, name);
		s.connect();
	}
}
