/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Point;
import javafx.scene.layout.Region;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
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
    
    @Before
    public void setUpClass() {
        player1 = new Controller(2);
        player2 = new Controller(4);
        
        Thread t = new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(GUI.class);
            }
        };
        
        t.setDaemon(true);
        t.start();
        gui = GUI.waitForGUIStartUpTest();
        gui2 = GUI.waitForGUIStartUpTest();
        gui.setPlayer(player1);
        gui2.setPlayer(player2);
        region = new Region();
    }
    
    @After
    public void tearDown() {
        gui.stopApplication();
        gui2.stopApplication();
        gui = null;
        gui2 = null;
        region = null;
        player1 = null;
        player2 = null;
    }

    /**
     * Test the set up and check for null gui. 
     */
    @Test
    public void testGUISetUp() {
        assertNotNull(gui);
        assertNotNull(gui2);
    }
    
}
