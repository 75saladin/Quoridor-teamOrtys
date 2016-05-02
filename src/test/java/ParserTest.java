import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 *
 * @author jed_lechner
 * Edited by josh_naar on 3/27/16 
 */

public class ParserTest {
  private final String move1 = "(0, 0)";
  private final String move2 = "(0, 1)";
  private final String move3 = "(8, 8)";
  private final String move4 = "(3, 7)";
  private final String move5 = "(2, 1)";

  private final String invalidMove1 = "(-1, 1)";
  private final String invalidMove12 = "(9, 1)";
  private final String invalidMove13= "(5, 11)";


  private final String wall1 = "[(0, 0), V]";
  private final String wall2 = "[(1, 0), H]";
  private final String wall3 = "[(2, 0), H]";

  private final String invalidWall1 = "[(8, 0), D]";
  private final String invalidWall2 = "[(2, 8), H]";
  private final String invalidWall3 = "[(8, 8), H]";
  private final String invalidWall4 = "[(9, 8), H]";

  // Test stripBrackets()
  @Test
  public void testStripBrackets() {
    // Assert.assertArrayEquals("this move", p.arr, p2.arr);
    assertNotEquals("Check if move string different after method call.", 
        move1, Parser.stripBrackets(move1));
    assertNotEquals("Check if move string different after method call.", 
        move2, Parser.stripBrackets(move2));
    assertNotEquals("Check if move string different after method call.", 
        wall1, Parser.stripBrackets(wall2));
    assertEquals("Test equality of move strings", Parser.stripBrackets(move3), 
        Parser.stripBrackets(move3));
    assertEquals("Test equality of wall strings", Parser.stripBrackets(wall2), 
        Parser.stripBrackets(wall2));
    assertEquals("Actual output equals expected output",
        Parser.stripBrackets(move2), "0 1");       
  }

  // Test parse()
  @Test
  public void testParse() {
    assertEquals("Test case: Tesuji", "0 0", Parser.parse("TESUJI " + move1));
    assertEquals("Test incorrect message input", "Error", Parser.parse("TRUMP (20, 16)"));  
  }


  // Test handleTesuji()
  // I'll fix this when I edit the method - Nick
  //Fixed this and the method - Eric
  @Test
  public void testHandleTesuji() {
    assertNotNull(Parser.formatMove(Parser.stripBrackets(move1)));
    assertNotNull(Parser.formatMove("TESUJI (0)"));
    assertNotNull(Parser.formatMove("TESUJI " + invalidMove1));
    assertNotNull(Parser.formatMove("TESUJI (hello, 0)"));
    assertNotNull(Parser.formatMove("TESUJI " + invalidWall1));
    assertNotNull(Parser.formatMove("TESUJI " + invalidWall4));
    assertNotNull(Parser.formatMove("TESUJI [(3, 5) K]"));
  }
  
}

