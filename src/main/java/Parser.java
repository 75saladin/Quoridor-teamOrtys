import java.util.*;

public class Parser {

  public static String parse(String message) {
    
    String[] arrayCharles = stripBrackets(message).split(" ");

    String opCode = arrayCharles[0];

    switch(opCode) {
      case "HELLO":
        //          handleHello();
      case "IAM":
        //          handleIam();
      case "GAME":
        //          handleGame();
      case "MYOUSHU":
        //          handleMyoushu();
      case "TESUJI":
        return handleTesuji(arrayCharles,message);
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

  // Strips brackets and commas from move-string.
  // Ex. Pawn movement (0, 3) --> 0 3
  //     Wall placement [(0, 3), h] --> 0 3 h
  public static String stripBrackets(String s) {
    s = s.replaceAll("[\\[()\\],]+", "");
    return s;
  }

  public void handleHello() {
    // Server: Send "IAM" to client
  }

  public void handleIam() {
    // Server: send preferred display name to client. Name cannot contain whitespace.
  }

  public String handleGame(String[] arr,String input) {
    // Client: Game is ready to start. First argument is player number
    // for the server receiving the message. 
    if (!((arr.length == 4) || (arr.length == 6))) {
      return "Invalid message";
    }   
    return input;
  }

  public void handleMyoushu() {
    // Client: Requests a move. Server should be expecting this.
  }


  // Currently determines if move is valid, without considering previous moves
  // In future, will consider previous moves
  // Currently returns input, this will be changed as protocol is implemented
  public static String handleTesuji(String[] arr,String input) {
    // Server: Response to Myoushu, includes move made by player. 
    // Move is either target location for player's pawn or location to place a wall

    // Check if arr length is correct for this category of message
    if (!((arr.length == 3) || (arr.length == 4))) {
      return "Invalid move";
    } 

    // Check if column and row numbers are valid and properly placed in arrayCharlesay
    int column = 0;
    int row = 0;
    try {
      column = Integer.parseInt(arr[1]);
      row = Integer.parseInt(arr[2]);
    } catch (Exception e){
      System.out.println("Integer parsing error" + e);
      e.printStackTrace();
      return ("Invalid move " + input);
    }
    if (!(0 <= column && 9 > column)) {
      return "Invalid move";
    } 
    if (!(0 <= row && 9 > row)) {
      return "Invalid move";
    }

    if (arr.length == 4) {  // if wall placement
      String direction = arr[3].toLowerCase();
      if (!(direction.equals("h") || direction.equals("v"))) {
        return "Invalid move";
      }
      if ((direction.equals("h") && row == 8) || (direction.equals("v") && column == 8)) {
        return "Invalid move";
      }     
    }

    // Check if direction is correct for wall placements



    return input;
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

  /*    
        public static void main(String[] args) {
        String move1 = "(0, 0)";
        String invalidMove1 = "(-1, 1)";
        String wall1 = "[(0, 0), H]";
        System.out.println(Parser.stripBrackets(wall1));

        String invalidWall1 = "[(8, 0), V]";
        Parser p = new Parser("TESUJI " + move1);
        System.out.println(p.stripBrackets(invalidMove1));
        System.out.println(p.handle());
        p = new Parser("TESUJI " + invalidMove1);
        System.out.println(p.handle());
        p = new Parser("TESUJI " + wall1);
        System.out.println(p.handle());
        p = new Parser("TESUJI " + invalidWall1);
        System.out.println(p.handle());
        }
   */
}

