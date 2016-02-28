import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * @author jed_lechner
 */
public class Layout2 extends Application {
    
    @Override
    public void start(Stage stage) {
        //Group root = new Group();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        //gridPane.setGridLinesVisible(true);
        
        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setAlignment(Pos.CENTER);

       
        for(int i = 0; i < 17; i++) {
            for(int j = 0; j < 17; j++) {
                Rectangle r = new Rectangle(50, 50, Color.BROWN);
                
                if(i % 2 == 0 && j%2==0) {
                    gridPane.add(r, i, j);
                   
                } 
                    
            }
        }
        Rectangle rect = new Rectangle(120, 20, Color.BLACK);
        
        GridPane.setColumnSpan(rect, 2);
        gridPane.add(rect, 0, 1);
        gridPane.getChildren().addAll(rect);
        
        //gridPane.getColumnConstraints().add(new ColumnConstraints(10));
        //gridPane.getColumnConstraints().add(new ColumnConstraints(10));

        
        // adds a horizontal bar starting at 0, 1 and goes for 2 columns and 1 row
  

        //gridPane.add(new Rectangle(50, 20, Color.BLACK), 2, 1);

        //gp.add(new Rectangle(120, 20, Color.BLACK), 4, 20, 2, 1);

        gridPane.getChildren().stream().forEach((Node node) -> {
            GridPane.setHalignment((Node) node, HPos.CENTER);
            GridPane.setValignment((Node) node, VPos.CENTER);
        });

        
        
        Scene scene = new Scene(gridPane, 1000, 1000, Color.BLUE);
  
        stage.setTitle("QUORIDOR");
        stage.setScene(scene);
        
//        startAnimationTimer();
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
