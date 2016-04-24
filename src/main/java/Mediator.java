



import java.awt.Point;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/* This object handles the number of players in the game
 * 
 */

/**
 *
 * @author jed_lechner
 */
public class  Mediator{
    
    private static GUI gui;
    private static int playerCount;
    
    public Mediator(GUI g) {
        gui = g;
    }
    
    public static void setGui(GUI g) {
        gui = g;
    }
    
    public static GUI getGui() {
        return gui;
    }
    
    public static void setPlayerCount(int count) {
        playerCount = count;
    }
    
    public int getPlayerCount() {
        return playerCount;
    }

   
}

