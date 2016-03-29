

import java.util.Scanner;
import javafx.application.Application;

/*
 * This is how you launch the application. 
 */

/**
 *
 * @author jed_lechner
 */
public class launchGui {
    public static void main(String[] args) {
        /**
         * How to launch the application thread in order to be
         * able to update the player move
         */
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(GUI.class);
            }
        }.start();
        /**
         * Called after launching the UI
         */
        GUI gui = GUI.waitForGUIStartUpTest();
        gui.printSomething();
         
        // you can set for four player by saying 
        // gui.setPlayer(new Controller(4));
        //gui.setPlayer(new Controller(4));
        /**
         * For testing purposes
         */
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("column and row");
            int c = sc.nextInt();
            int r = sc.nextInt();
            if(c == -1) {
                break;
            }
            gui.movePlayer(c, r);
        }
        
        
    }
}
