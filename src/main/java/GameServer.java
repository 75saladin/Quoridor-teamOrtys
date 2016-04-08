import java.io.*;
import java.net.*;
import java.util.*;


public class GameServer extends Server {

	int playerNum;
	RandomAI AI;
	
	public GameServer(int port) {
		super(port);
		
	}	

	@Override 
	public void handle(Socket socket) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		String msg = "";
		while(true) {
			msg = in.readLine();
			System.out.println("Message from client " + msg);
			handleMessage(msg, out, socket);
		}

	}

	public void handleMessage(String msg, PrintWriter out, Socket socket) {
		msg = Parser.stripBrackets(msg);
		String [] s = msg.split(" ");
		if(msg.startsWith("HELLO")) {
			out.println("IAM ORT");
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
			String move = AI.getMove();
			out.println("TESUJU " + move);
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
		Scanner sc = new Scanner(System.in);
		String in = sc.next();
		String[] arr = in.split(" ");
		for(int i = 1; i < arr.length; i++) {
			if(arr[i-1].equals("--port")){
				port = Integer.parseInt(arr[i]);
			}
		}
		GameServer s = new GameServer(port);
		s.connect();
	}
}