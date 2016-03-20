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
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testConstructorIndexOutOfBoundsException() {
        
        exception.expect(IndexOutOfBoundsException.class);
        Player player = new Player(3);
    }
}
