
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jed_lechner
 */
public class RandomAI {
    
    static final Controller player = new Controller(2);
    
    public static void main() {
        Thread t = new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(GUI.class);
            }
        };
        
        t.setDaemon(true);
        t.start();
        /**
         * Called after launching the UI
         */
        GUI gui = GUI.waitForGUIStartUpTest();

        gui.setPlayer(player);
        
        while(true) {
            
            
        }
    }
    
    
    public void getMoves() {
        // is there a player in the surrounding squares 
        
        
        
        
    }
    
    public void makeMove() {
        
    }
    
    // you can't build walls 8, 0 
    // you can't build walls a, 8
    
    
    
}
