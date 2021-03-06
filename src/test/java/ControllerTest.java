/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Point;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 *
 * @author jed_lechner
 */
@RunWith(value=BlockJUnit4ClassRunner.class)
public class ControllerTest {
    
    private Controller controller1;
    private Controller controller2;
    private Controller controller3;
    private Controller controller4;
    
    
    @BeforeClass
    public static void setUpClass() {
        
    }

    
    @Before
    public void setUp() {
        controller1 = new Controller(2);
        controller2 = new Controller(4);
    }
    
    @After
    public void tearDown() {
        controller1 = null;
        controller2 = null;
    }

    /**
     * Test Constructor
     */
    @Test
    public void testConstructor(){
        assertNotNull(controller1);
        assertNotNull(controller2);
    }
    
    /* expected exception variable */
    //@Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * test if the constructor throws an indexoutofboundsexception
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorIndexOutOfBoundsException() {
        
        //exception.expect(IndexOutOfBoundsException.class);
        Controller controller = new Controller(3);
    }
    
    /**
     * Test get Walls and SetWalls
     */
     @Test
     public void testGetandSetWalls(){
        int n = controller1.getWalls(1);
        assertEquals("Testing get walls", 10, n);
        controller1.setWalls(1);
        n = controller1.getWalls(1);
        assertEquals("Testing get walls", 9, n);
        controller2.setWalls(4);
        controller2.setWalls(4);
        controller2.setWalls(4);
        controller2.setWalls(4);
        
        assertEquals("Testing get walls", 1, controller2.getWalls(4));
        assertEquals("Testing get walls", 5, controller2.getWalls(2));
     }
    /**
     * Test get controller count
     */
   @Test 
    public void testGetPlayerCount() {
        int two = 2;
        int four = 4;
        assertEquals("Testing two controller: ", controller1.getPlayerCount(), two);
        assertEquals("Testing four controllers: ", controller2.getPlayerCount(), four);
    }
    
    /**
     * Test set controller turn and get controller turn
     */
    @Test
    public void testSetPlayerTurn_getPlayerTurn() {
        int one = 1;
        int two = 2;
        int three = 3; 
        int four = 4;
        
        assertEquals("Test controller turn: ", controller1.getPlayerTurn(), one);
        controller1.setPlayerTurn();
        assertEquals("Test controller turn: ", controller1.getPlayerTurn(), two);
        controller1.setPlayerTurn();
        assertEquals("Test controller turn: ", controller1.getPlayerTurn(), one);
        controller1.setPlayerTurn();
        assertEquals("Test controller turn: ", controller1.getPlayerTurn(), two);
        
        assertEquals("Test controller turn: ", controller2.getPlayerTurn(), one);
        controller2.setPlayerTurn();
        assertEquals("Test controller turn: ", controller2.getPlayerTurn(), four);
        controller2.setPlayerTurn();
        assertEquals("Test controller turn: ", controller2.getPlayerTurn(), two);
        controller2.setPlayerTurn();
        assertEquals("Test controller turn: ", controller2.getPlayerTurn(), three);
        controller2.setPlayerTurn();
        assertEquals("Test controller turn: ", controller2.getPlayerTurn(), one);
        controller2.setPlayerTurn();
        
    }
    
    /**
     * Test remove player
     */
    public void testRemovePlayer() {
        controller1.removePlayer(2);
        controller1.setPlayerTurn();
        
        assertEquals("Test turn", 1, controller1.getPlayerTurn());
        controller1.setPlayerTurn();
        assertNotEquals(2, controller1.getPlayerTurn());
        
        controller2.removePlayer(1);
        assertEquals(4, controller2.getPlayerTurn());
        
        controller2.removePlayer(2);
        assertEquals(3, controller2.getPlayerTurn());
        
    }
    
    /**
     * Tests getting the controller node
     */
    @Test
    public void testGetPlayerNode() {
        assertNull(new Controller(2).getPlayerNode(4));
        assertNotNull(controller1.getPlayerNode(1));
        
    }

}
