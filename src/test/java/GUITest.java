/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.scene.layout.Region;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

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
    
    @Before
    public void setUpTest() {
//        player1 = new Controller(2);
//        player2 = new Controller(4);
//        gui = new GUI(player1);
//        gui2 = new GUI(player2);
//        region = new Region();
    }
    
    @After
    public void tearDown() {
        gui = null;
        gui2 = null;
        region = null;
        player1 = null;
        player2 = null;
    }

    @Test
    public void testGUISetUp() {
//        
//        assertNotNull(gui);
//        assertNotNull(gui2);
//        assertNotNull("Grab the gui borderpane ", gui.getRootRegion());
//        assertNotNull("Grab the gui borderpane ", gui2.getRootRegion());
        
        
    }
}
