

/**
 *
 * @author jed_lechner
 */
public class Parser {

  /**
   * This is the main parser method for this class. It takes in 
   * a string and processes it to a usable format for us.
   *
   * @param message the input message to this method.
   * @return a usable String for our methods.
   */
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
      case "KIKASHI":
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

  /** Strips brackets and commas from move-string.
   * Ex. Pawn movement (0, 3) to 0 3
   *     Wall placement [(0, 3), h] to 0 3 h
   *
   * @param s The string to have the brackets stripped from.
   * @return a String with all of its brackets stripped.
   */
  public static String stripBrackets(String s) {
    s = s.replaceAll("[\\[()\\],]+", "");
    return s;
  }


  private String[] handleGame(String[] arr,String input) {
    // Client: Game is ready to start. First argument is player number
    // for the server receiving the message. 

    return arr;
  }

  private static String handleAtari(String[] s) {
      if(s.length == 4) {
          return s[2] + " " + s[3];
      } 

      return s[2] + " " + s[3] + " " + s[4];
    // Client: First arg is player's number. Second is move-string.
    // Communicates player's move to all players.
  }
}

