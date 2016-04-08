/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Point;
import javafx.scene.shape.Circle;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.Ignore;

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
     * Test setting the controller count to a new number.
     */
    @Test
    public void testSetPlayerCount() {
        int one = 1;
        int two = 2;
        int three = 3; 
        int four = 4;
        controller1.setPlayerCount(3);

        assertEquals("Test set controller count", controller1.getPlayerCount(), 3);
        controller2.setPlayerCount(1);
 
        assertEquals("Test set controller count", controller2.getPlayerCount(), 1);
        
        controller2.setPlayerCount(4);
        assertEquals("Test set controller count", controller2.getPlayerCount(), 4);
    }
    
    /**
     * Tests getting the controller node
     */
    @Test
    public void testGetPlayerNode() {
        assertNull(new Controller(2).getPlayerNode(4));
        
    }
    
    /**
     * tests getting the player position
     */
    @Test
    public void testGetPlayerPosition() {
        Point p1 = new Point(4, 0);
        Point p2 = new Point(4, 8);
        
        assertEquals(p1, controller1.getPlayerPosition(1));
        assertEquals(p2, controller2.getPlayerPosition(2));
        
        assertEquals(p1.x, controller1.getPlayerPosition(1).x);
    }
    
    @Test 
    public void testSetPlayerPosition() {
        Point p1 = new Point(5, 3);
        Point p2 = new Point(2, 6);
        controller1.setPlayerPosition(2, 5, 3);
        controller2.setPlayerPosition(4, 2, 6);
        assertEquals(p1, controller1.getPlayerPosition(2));
        assertEquals(p2, controller2.getPlayerPosition(4));

    }

}
