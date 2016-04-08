

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
         * Called after launching the UI
         */
        GUI gui=null;
        gui = startGUI(4);
        RandomAI ai1 = new RandomAI(2, 1);
        RandomAI ai2 = new RandomAI(2, 2);
       
         
        while(true) {
            String move1 = ai1.getMove();
            String[] s = move1.split(" ");
            int one = Integer.parseInt(s[0]);
            int two = Integer.parseInt(s[1]);
            gui.movePlayer(one, two);
            
            ai1.update(1,move1);
            ai2.update(1,move1);
            
            Thread.sleep(2000);
            
            String move2 = ai2.getMove();
            s = move2.split(" ");
            one = Integer.parseInt(s[0]);
            two = Integer.parseInt(s[1]);
            
            gui.movePlayer(one, two);
            
            ai1.update(2,move2);
            ai2.update(2,move2);          
        }   
    }
      /**
    * Handles starting the GUI.
    * Nothing more. Nothing less.
    *
    * @param pNum number of players in game
    */
  public static GUI startGUI(int pNum){
    new Thread() {
      @Override
      public void run() {
        javafx.application.Application.launch(GUI.class);
      }
    }.start();
    GUI gui = GUI.waitForGUIStartUpTest();
    gui.setPlayer(new Controller(pNum));
    return gui;
  }
}
