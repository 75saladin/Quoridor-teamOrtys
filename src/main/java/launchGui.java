

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
        
        RandomAI ai = new RandomAI(gui);
       
         
        while(true) {
            System.out.println("Player # and turn " + gui.getController().getPlayerTurn());
            Point p = ai.getRandomMove(gui.getController());
            
            if(ai.valid(p, gui.getController())) {
                System.out.println("Point " + p);
                System.out.println(gui.getController().getPlayerTurn());
                gui.movePlayer(p.x, p.y);
                Thread.sleep(5000);
            }
            
            
        }
        
        
    }
}
