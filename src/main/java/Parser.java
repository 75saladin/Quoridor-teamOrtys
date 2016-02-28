import java.util.*;

public class Parser {

    private String input;
    private String[] arr;

    public Parser(String s) {
        input = stripBrackets(s);
        arr = input.split(" ");
        handle(input);
    }

    public String handle(String s) { 
        String arg1 = arr[0];
        switch(arg1) {
            case "HELLO":
     //          handleHello();
            case "IAM":
     //          handleIam();
            case "GAME":
     //          handleGame();
            case "MYOUSHU":
     //          handleMyoushu();
            case "TESUJI":
               handleTesuji();
            case "ATARI":
     //          handleAtari();
            case "GOTE":
     //          handleGote();
            case "KIKASHI":
     //          handleKikashi();
	    default:
               return ("Error");
        }
    }

    public static String stripBrackets(String s) {
        s = s.replaceAll("[\\[()\\],]+", "");
	return s;
    }

    public void handleHello() {
        // Client: Send "IAM" to server
    }

    public void handleIam() {
        // Server: send preferred display name to client. Name cannot contain whitespace.
    }

    public void handleGame() {
        // Client: Game is ready to start. First argument is player number
        // for the server receiving the message. 
    }

    public void handleMyoushu() {
        // Client: Requests a move. Server should be expecting this.
    }

    public String handleTesuji() {
        // Server: Response to Myoushu, includes move made by player. 
        // Move is either target location for player's pawn or location to place a wall
        if (!((arr.length == 3) || (arr.length == 4))) {
            return "Invalid move";
        } 
        // Check if move is valid
        try {
            int column = Integer.parseInt(arr[1]);
            int row = Integer.parseInt(arr[2]);
        } catch (Exception e){
            System.out.println("Integer parsing error" + e);
            e.printStackTrace();
            return ("Invalid move" + input);
        }
            
        if (!(0 <= column && 9 > column)) {
            return "Invalid move";
        }
        
        if (!(0 <= row && 9 > row)) {
            return "Invalid move";
        }
        if (arr.length == 4) {  // if wall placement
            String direction = arr[3];
            if (!(direction.equals("h") || direction.equals("v"))) {
                return "Invalid move";
            }
        }
    }

    public void handleAtari() {
        // Client: First arg is player's number. Second is move-string.
        // Communicates player's move to all players.
    }

    public void handleGote() {
        // First arg is a player's number. Message sent by client to all move-servers
        // informing them that <p> made an illegal move and has been removed from the game.
        // This will be the last message sent to the offending server.
    }

    public void handleKikashi() {
        // Message sent by client to all servers. First arg is player number.
        // Informs all servers that game is over and <p> won.
        // This is last message sent to any server.
    }

}
        
