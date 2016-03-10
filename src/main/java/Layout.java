import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/* The layout class is the GUI for the game. The board is drawn and the features
 * for the game are implemented.
 */
public class Layout extends Application {
    
    // @param stage: The stage to draw the GUI on. 
    // The start method is required from the application class to start the GUI.
    @Override
    public void start(Stage stage) {
        GridPane gridPane = new GridPane();
        
        // draw the board 
        gridPane = drawBoard(gridPane);
      
        
        //center each node
        gridPane.getChildren().stream().forEach((Node node) -> {
            GridPane.setHalignment((Node) node, HPos.CENTER);
            GridPane.setValignment((Node) node, VPos.CENTER);
        });
        
     
        // create a scene and add the gridPane node to it and set the backgorund
        // color to blue
        Scene scene = new Scene(gridPane, 1000, 1000, Color.BLUE);
        stage.setTitle("QUORIDOR");
        stage.setScene(scene);
        stage.show();
    }
    
    // @param gridPane: The gridPane used to draw the board. 
    // returns: the fully constructred board with rectangles. 
    public GridPane drawBoard(GridPane gridPane) {
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setGridLinesVisible(true);
        
        for(int i = 0; i < 17; i++) {
            for(int j = 0; j < 17; j++) {
                if(i % 2 == 0 && j%2==0)
                    gridPane.add(new Rectangle(50, 50, Color.BROWN), i, j);
            }
        }  
        return gridPane;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
