

import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/* The layout class is the GUI for the game. The board is drawn and the features
 * for the game are implemented.
 */
public class Layout extends Application /*implements LayoutInterface*/ { 
    static Controller p = null;
    GUI gui = null;
    // @param stage: The stage to draw the GUI on. 
    // The start method is required from the application class to start the GUI.
    @Override
    public void start(Stage stage) {
        p = new Controller(4);
        gui = new GUI(p);
        Region root = new Region();
        root = gui.getRootRegion();
        System.out.println(gui.getPlayerPosition(4));
        
        Scene scene = new Scene(root, 1000, 1000, Color.BLUE);
        stage.setTitle("QUORIDOR");
        
        stage.setScene(scene);
        stage.show();
        String inp = "";
        Scanner sc = new Scanner(System.in);
        while(inp.equals("exit")) {
            int column = sc.nextInt();
            int row = sc.nextInt();
            //gui.buildWall(column, row);
            gui.movePlayer(column, row);
            
        }
    }
    
    public static void main(String[] args) {
        Application.launch(Layout.class);
        
    }


    
}
