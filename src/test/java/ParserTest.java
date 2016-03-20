/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

*/

/**
 *
 * @author jed_lechner
 */
/*
public class ParserTest {
    private final String move1 = "(0, 0)";
    private final String move2 = "(0, 1)";
    private final String move3 = "(8, 8)";
    private final String move4 = "(3, 7)";
    private final String move5 = "(2, 1)";
    
    private final String invalidMove1 = "(-1, 1)";
    private final String invalidMove12 = "(9, 1)";
    private final String invalidMove13= "(5, 11)";

    
    private final String wall1 = "[(0, 0), D]";
    private final String wall2 = "[(1, 0), H]";
    private final String wall3 = "[(2, 0), H]";
    
    private final String invalidWall1 = "[(8, 0), V]";
    private final String invalidWall2 = "[(2, 8), H]";
    private final String invalidWall3 = "[(8, 8), H]";
    private final String invalidWall4 = "[(9, 8), H]";
*/            
    //test constructor
/*
    @Test
    public void testConstructor() {
        Parser parser = new Parser(move1);
        String s = "[(0, 8), H]";
        assertNotNull(parser);  
        
    }
*/
/*
    @Test
    public void testStripBrackets() {
        Parser p = new Parser(move2);
        Parser p2 = new Parser(move2);
        //Assert.assertArrayEquals("this move", p.arr, p2.arr);
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
        
    }
*/
/*    
    @Test
    public void testHandle() {
        Parser p = new Parser(move1);
        String s = "Testing handle with ";
        
        assertEquals(s + move1, p.handle(), "Error");
        
        
        String actualMove = "TESUJI " + move1;
        p = new Parser(actualMove);
        assertEquals(s + actualMove, "TESUJI 0 0", p.handle());
        p = new Parser("0, 0");
        assertEquals(s + actualMove, "Error", p.handle());
        p = new Parser("TESUJI (0)");
        assertEquals("Testing wrong length of string", "Invalid move", p.handle());
        p = new Parser("TESUJI (c, r)");
        assertEquals("Testing try catch", "Invalid move TESUJI c r", p.handle());
        p = new Parser("TESUJI (10, 0)");
        assertEquals("Testing move out of range", "Invalid move", p.handle());
        p = new Parser("TESUJI " + invalidWall1);
        assertEquals("Testing invalid wall", "Invalid move", p.handle());
        p = new Parser("TESUJI " + invalidWall4);
        assertEquals("Testing invalid wall out of range", "Invalid move", p.handle());
        
        
    }
    
}

*/
