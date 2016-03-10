import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/* The layout class is the GUI for the game. The board is drawn and the features
 * for the game are implemented.
 */
public class Layout extends Application {
    
    // @param stage: The stage to draw the GUI on. 
    // The start method is required from the application class to start the GUI.
    @Override
    public void start(Stage stage) {
        // create a gridpane for ease of use in the layout
        GridPane gridPane = new GridPane();
        
        // create a border pane to utilize the top, left, middle, and right parts
        // of the layout
        BorderPane root = new BorderPane();
        
        
        // draw the board 
        gridPane = drawBoard(gridPane);
      
        // place the board in the center
        // the controls on the right, description left, and label top
        root.setCenter(gridPane);
        root.setRight(drawRegion("Controls Here", Color.DARKSLATEGREY));
        root.setLeft(drawRegion("Description Here", Color.CHARTREUSE));
        root.setTop(drawRegion("QUORIDOR", Color.BLUEVIOLET));
        root.autosize();
        
        //center each node
//        gridPane.getChildren().stream().forEach((Node node) -> {
//            GridPane.setHalignment((Node) node, HPos.LEFT);
//            GridPane.setValignment((Node) node, VPos.TOP);
//        });
        
     
        // create a scene and add the gridPane node to it and set the backgorund
        // color to blue
        Scene scene = new Scene(root, 1000, 1000, Color.BLUE);
        stage.setTitle("QUORIDOR");
        stage.setScene(scene);
        stage.show();
    }
    
    // returns a node to put in the borderpane
    private Region drawRegion(String myText, Color bgColor) {
        Text text = new Text(myText);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        StackPane stackPane = new StackPane();
        if(myText.contains("Description")) {
            stackPane.setPadding(new Insets(10, 10, 10, 5));
        } else if(myText.contains("QUORIDOR")){
            stackPane.setPadding(new Insets(10, 5, 5, 5));
            text.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        } else if(myText.contains("Controls Here")) {
            stackPane.setPadding(new Insets(10, 5, 10, 5));
        }
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(text);
        // set the max width 
        stackPane.setMaxWidth(50);
        return stackPane;
    }
    
    // @param gridPane: The gridPane used to draw the board. 
    // returns: the fully constructred board with rectangles. 
    public GridPane drawBoard(GridPane gridPane) {
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setGridLinesVisible(true);
        
        for(int i = 0; i < 17; i++) {
            for(int j = 0; j < 17; j++) {
                if(i % 2 == 0 && j%2==0)
                    gridPane.add(new Rectangle(50, 50, Color.BROWN), i, j);
                    
            }
        } 
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        return gridPane;
    }
    
    // add method here for drag and drop of pawn
    // the pawn can only be moved if it is your turn
    // also the pawn can only be moved if there isn't a 
    // wall in front of you and there is an available 
    // path
    
    
    // used to set on mouseClick for game
    // note the board can only be clicked if it is your turn
    // and there isn't a wall there
    public GridPane setOnClicked(GridPane gridPane) {
        //gridPane.setOnMouseClicked(event());
        return gridPane;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
