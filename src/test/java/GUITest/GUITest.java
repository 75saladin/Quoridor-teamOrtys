/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUITest;

import GUI.Controller;
import GUI.GUI;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
    public void setUp() {
        gui = new GUI();
        gui2 = new GUI();
        region = new Region();
        player1 = new Controller(2);
        player2 = new Controller(4);
    }
    
    @After
    public void tearDown() {
        gui = null;
        gui2 = null;
        region = null;
        player1 = null;
        player2 = null;
    }
//
//    @Test
//    public void testGUISetUp() {
//        gui.setUp(player1);
//        assertNotNull(gui);
//        region = gui.getRootRegion();
//        assertNotNull("Grab the gui borderpane ", region);
//        assertNull(gui2);
//        gui2.setUp(player2);
//        
//    }
}
