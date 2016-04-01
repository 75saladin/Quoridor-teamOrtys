/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.scene.layout.Region;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Ignore;

/**
 *
 * @author jed_lechner
 */
public class GUITest {
    
    private GUI gui;
    private GUI gui2;
    private Region region;
    private Controller player1;
    private Controller player2;
    
    @Ignore
    @Before
    public void setUpClass() {
        player1 = new Controller(2);
        player2 = new Controller(4);
        
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(GUI.class);
            }
        }.start();
        gui = GUI.waitForGUIStartUpTest();
        gui2 = GUI.waitForGUIStartUpTest();
        gui.setPlayer(player1);
        gui2.setPlayer(player2);
        region = new Region();
    }
    
    @Ignore
    @After
    public void tearDown() {
        gui = null;
        gui2 = null;
        region = null;
        player1 = null;
        player2 = null;
    }

    /**
     * Test the set up and check for null gui. 
     */
    @Ignore
    @Test
    public void testGUISetUp() {
        assertNotNull(gui);
        assertNotNull(gui2);
    }
    
    /**
     * Test getRootRegion
     */
    @Ignore
    @Test
    public void testGetRootRegion() {
        assertNotNull("Grab the gui borderpane ", gui.getRootRegion());
        assertNotNull("Grab the gui borderpane ", gui2.getRootRegion());
    }
    
    /**
     * Tests getting the player position
     */
    @Ignore
    @Test
    public void testGetPlayerPosition() {
        String p = "Player:1 c:4 r:0";
        String p2 = "Player:2 c:4 r:8";
        String p3 = "Player:3 c:0 r:4";
        String p4 = "Player:4 c:8 r:4";
//        assertEquals("Test player position", p, gui2.getPlayerPosition(1));
//        assertEquals("Test player position", p2, gui2.getPlayerPosition(2));
//        assertEquals("Test player position", p3, gui2.getPlayerPosition(3));
//        assertEquals("Test player position", p4, gui2.getPlayerPosition(4));
    }
}
