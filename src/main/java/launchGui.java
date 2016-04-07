

import java.awt.Point;
import java.util.Scanner;

/*
 * This is how you launch the application. 
 */

/**
 *
 * @author jed_lechner
 */
public class launchGui {
    public static void main(String[] args) throws Exception {
        /**
         * How to launch the application thread in order to be
         * able to update the player move
         * 
         */

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
        Controller player = new Controller(2);
        gui.setPlayer(player);
        
        RandomAI ai1 = new RandomAI(2, 1);
        RandomAI ai2 = new RandomAI(2, 2);
       
         
        while(true) {
            String move1 = ai1.getMove();
            String[] s = move1.split(" ");
            int one = Integer.parseInt(s[0]);
            int two = Integer.parseInt(s[1]);
            gui.movePlayer(one, two);
            
            ai1.update(1, move1);
            ai2.update(1, move1);
            
            Thread.sleep(2000);
            
            String move2 = ai2.getMove();
            s = move2.split(" ");
            one = Integer.parseInt(s[0]);
            two = Integer.parseInt(s[1]);
            
            gui.movePlayer(one, two);
            
            ai1.update(2, move2);
            ai2.update(2, move2);          
        }   
    }
}
