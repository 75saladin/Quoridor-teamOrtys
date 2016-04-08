import java.util.*;

public class Parser {

  public static String parse(String message) {
    
    String strip = stripBrackets(message);
    
    String[] arrayCharles = strip.split(" ");

    String noKey = null;
    if(strip.contains(" "))
      noKey = strip.substring(strip.indexOf(" ") + 1);

    String opCode = arrayCharles[0];

    switch(opCode) {
      case "TESUJI":
        return noKey;
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

}

