package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/* The layout class is the GUI for the game. The board is drawn and the features
 * for the game are implemented.
 */
public class Layout extends Application /*implements LayoutInterface*/ {        
    // @param stage: The stage to draw the GUI on. 
    // The start method is required from the application class to start the GUI.
    @Override
    public void start(Stage stage) {
        GUI gui = new GUI();
        Region root = new Region();
        gui.setUp(new Controller(4));
        root = gui.getRootRegion();
        
        Scene scene = new Scene(root, 1000, 1000, Color.BLUE);
        stage.setTitle("QUORIDOR");
        
        stage.setScene(scene);
        stage.show();
    }


    
}
