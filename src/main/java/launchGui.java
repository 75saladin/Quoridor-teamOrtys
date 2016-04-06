

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
        gui.setPlayer(new Controller(4));

         
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
            String s = sc.next();
            if(c == -1) {
                break;
            }
            if(s.equals("stop")){
                gui.stopApplication();
                break;
            }
                
            
            gui.buildWall(c, r, s);
           
        }
        
        
    }
}
