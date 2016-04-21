

public class Parser {

  public static String parse(String message) {
    
    String strip = stripBrackets(message);
    
    String[] arrayCharles = strip.split(" ");

    String noKey = null;
    if(strip.contains(" "))
      noKey = strip.substring(strip.indexOf(" ") + 1);

    String opCode = arrayCharles[0];

    switch(opCode) {
      case "IAM":
        return arrayCharles[1];
      case "GAME":
        return noKey;
      case "TESUJI":
        return noKey;
      case "ATARI":
         return handleAtari(arrayCharles);
      case "GOTE":
        //          handleGote();
      case "KIKASHI":
        //          handleKikashi();
      default:
        return ("Error");
    }
  }

  /**
    * Handles converting from simple move String to protocol move String.
    *
    * @param move String of simple move String
    *
    * @return Protocol formatted move String
    */
  public static String formatMove(String move){
    String[] spirit = move.split(" ");
    if(spirit.length == 2)
      return "(" + spirit[0] + ", " + spirit[1] + ")";
    else
      return "[(" + spirit[0] + ", " + spirit[1] + "), " + spirit[2] + "]";
  } 

  // Strips brackets and commas from move-string.
  // Ex. Pawn movement (0, 3) --> 0 3
  //     Wall placement [(0, 3), h] --> 0 3 h
  public static String stripBrackets(String s) {
    s = s.replaceAll("[\\[()\\],]+", "");
    return s;
  }


  public String[] handleGame(String[] arr,String input) {
    // Client: Game is ready to start. First argument is player number
    // for the server receiving the message. 

    return arr;
  }


  public static String handleAtari(String[] s) {
      if(s.length == 4) {
          return s[2] + " " + s[3];
      } 

      return s[2] + " " + s[3] + " " + s[4];
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

