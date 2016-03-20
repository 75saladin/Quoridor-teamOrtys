/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 *
 * @author jed_lechner
 */
@RunWith(value=BlockJUnit4ClassRunner.class)
public class PlayerTest {
    
    private Player player1;
    private Player player2;
    private Player player3;

    
    @BeforeClass
    public static void setUpClass() {
        
    }

    
    @Before
    public void setUp() {
        player1 = new Player(2);
        player2 = new Player(4);
    }
    
    @After
    public void tearDown() {
        player1 = null;
        player2 = null;
    }

    /**
     * Test Constructor
     */
    @Test
    public void testConstructor(){
        assertNotNull(player1);
        assertNotNull(player2);
    }
    
    /* expected exception file */
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * test if the constructor throws an indexoutofboundsexception
     */
    @Test
    public void testConstructorIndexOutOfBoundsException() {
        
        exception.expect(IndexOutOfBoundsException.class);
        Player player = new Player(3);
    }
    
    /**
     * Test get player count
     */
   @Test 
    public void testGetPlayerCount() {
        int two = 2;
        int four = 4;
        assertEquals("Testing two player: ", player1.getPlayerCount(), two);
        assertEquals("Testing four players: ", player2.getPlayerCount(), four);
    }
    
    /**
     * Test set player turn and get player turn
     */
    @Test
    public void testSetPlayerTurn_getPlayerTurn() {
        int one = 1;
        int two = 2;
        int three = 3; 
        int four = 4;
        
        assertEquals("Test player turn: ", player1.getPlayerTurn(), one);
        player1.setPlayerTurn();
        assertEquals("Test player turn: ", player1.getPlayerTurn(), two);
        player1.setPlayerTurn();
        assertEquals("Test player turn: ", player1.getPlayerTurn(), one);
        player1.setPlayerTurn();
        assertEquals("Test player turn: ", player1.getPlayerTurn(), two);
        
        assertEquals("Test player turn: ", player2.getPlayerTurn(), one);
        player2.setPlayerTurn();
        assertEquals("Test player turn: ", player2.getPlayerTurn(), two);
        player2.setPlayerTurn();
        assertEquals("Test player turn: ", player2.getPlayerTurn(), three);
        player2.setPlayerTurn();
        assertEquals("Test player turn: ", player2.getPlayerTurn(), four);
        player2.setPlayerTurn();
        assertEquals("Test player turn: ", player2.getPlayerTurn(), one);
        player2.setPlayerTurn();
        
    }
}
