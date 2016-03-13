import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.ButtonGroup;

/* The layout class is the GUI for the game. The board is drawn and the features
 * for the game are implemented.
 */
public class Layout extends Application {
    private final Circle player1 = new Circle(25.0); // temporary circle for player
    private final Circle player2 = new Circle(25.0); 
    private final Circle player3 = new Circle(25.0);
    private final Circle player4 = new Circle(25.0);
    
    // create a gridpane and draw the rectangular board
    private GridPane gridPane = drawBoard(2); 
    
    // create a border pane to utilize the top, left, middle, and right parts
    // of the layout
    private BorderPane root = setBorderPane(gridPane);
    
    
    // @param stage: The stage to draw the GUI on. 
    // The start method is required from the application class to start the GUI.
    @Override
    public void start(Stage stage) {
        
        //GridPane gridPane = drawBoard(2);
        
        
        //BorderPane root = setBorderPane(gridPane);
        setOnClicked();
        setWallEvent();
        // create a scene and add the gridPane node to it and set the backgorund
        // color to blue
        Scene scene = new Scene(root, 1000, 1000, Color.BLUE);
        stage.setTitle("QUORIDOR");
        stage.setScene(scene);
        stage.show();
    }
    
    
    // @param gridPane: The gridPane used to draw the board. 
    // returns: the fully constructred board with rectangles. 
    public GridPane drawBoard(int numOfPlayers) {
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(2.0);
        gp.setVgap(2.0);
        
        for(int i = 0; i < 17; i++) {
            for(int j = 0; j < 17; j++) {
                if(i % 2 == 0 && j%2==0) {
                    gp.add(new Rectangle(50, 50, Color.BROWN), i , j);
                    gp.add(new Text("(" + i + ", " + j + ")"), i, j);
                }
            }
        } 
        if(numOfPlayers == 2) {
            gp.add(player1, 8, 0);
            gp.add(player2, 8, 16);
        } else {
            gp.add(player3, 0, 8);
            gp.add(player4, 16, 8);
        }

        gp.setPadding(new Insets(5, 5, 5, 5));
        return gp;
    }
    
    private void setWallEvent() {
        List <Node> childrens = gridPane.getChildren();
        childrens.stream().forEach((node) -> {
            node.setOnMousePressed((MouseEvent event) -> {
                int row = GridPane.getRowIndex(node);
                int column = GridPane.getColumnIndex(node);
                row++;
                gridPane.add(new Rectangle(50, 5.0, Color.GOLD), column, row);
                column++;
                gridPane.add(new Rectangle(50, 5.0, Color.GOLD), column, row);
            });
        });
    }
    
    // @param gridPane: The board for the game of Quoridor
    // returns a border pane to separate the page into top, bottom, middle, left
    // and right. The Left will be for a description of the Game. The right will
    // be for user controls. Top the title. And middle for the game. The bottom
    // is currently not being used. 
    public BorderPane setBorderPane(GridPane gridPane) {
        // place the board in the center
        // the controls on the right, description left, and label top
        BorderPane bp = new BorderPane();
        bp.setCenter(gridPane);
        bp.setLeft(setDescription());
        bp.setTop(setTitle());
        bp.setBottom(setBottom());
        
        return bp;
    }
    
    // pre: none
    // returns the left region of the borderpane
    private Region setDescription() {
        VBox vb = new VBox();
        vb.setPadding(new Insets(20,20,20,20));
        vb.setAlignment(Pos.CENTER);
        Label label = new Label("    Description     ");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        label.setAlignment(Pos.CENTER);
        vb.setStyle("-fx-background-color: #008000ff");
        vb.getChildren().add(label);
        return vb;
    }
    
    
    // returns the title region
    private Region setTitle() {
        Text text = new Text("Quoridor");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        
        StackPane stackPane = new StackPane();
        Insets inset = new Insets(20, 20, 20, 20);
        stackPane.setPadding(inset);
        
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setStyle("-fx-background-color: #008000ff");
        stackPane.getChildren().add(text);
        // set the max width 
        stackPane.setMinHeight(100);
        return stackPane;
    }
    
        private Region setBottom() {
        Text text = new Text("Team Morty's");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        
        StackPane stackPane = new StackPane();
        Insets inset = new Insets(20, 20, 20, 20);
        stackPane.setPadding(inset);
        
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setStyle("-fx-background-color: #008000ff");
        stackPane.getChildren().add(text);
        // set the max width 
        stackPane.setMinHeight(200);
        return stackPane;
    }
        
    // used to set on mouseClick for game
    // note the board can only be clicked if it is your turn
    // and there isn't a wall there
    protected void setOnClicked() {
        Button up = new Button("UP");       
        Button down = new Button("DOWN");      
        Button left = new Button("LEFT");
        Button right = new Button("RIGHT");
        
        // set the minimum width of the buttons
        up.setMinWidth(100.0);
        down.setMinWidth(100.0);
        left.setMinWidth(100.0);
        right.setMinWidth(100.0);
        
        // up moves the player node up two rows
        up.setOnAction((ActionEvent e) -> {
            int row = GridPane.getRowIndex(player2)-2;
            int column = GridPane.getColumnIndex(player2);
            gridPane.getChildren().remove(player2);
            gridPane.add(player2, column, row);
        });
        
        // down moves the player node down two rows
        down.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                
                int row = GridPane.getRowIndex(player2) + 2;
                int column = GridPane.getColumnIndex(player2);
                gridPane.getChildren().remove(player2);
                gridPane.add(player2, column, row);
            }
        });
        
        // left moves the player node left two columns
        left.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                
                int row = GridPane.getRowIndex(player2);
                int column = GridPane.getColumnIndex(player2) - 2;
                gridPane.getChildren().remove(player2);
                gridPane.add(player2, column, row);
            }
        });
        
        // right moves the player node right two columns
        right.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                
                int row = GridPane.getRowIndex(player2);
                int column = GridPane.getColumnIndex(player2) + 2;
                gridPane.getChildren().remove(player2);
                gridPane.add(player2, column, row);
            }
        });
        
        // create a label for the controls called controls
        Label label = new Label("      Controls       ");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        VBox buttons = new VBox(10, label, up, down, left, right);
        buttons.setAlignment(Pos.CENTER);
        buttons.setStyle("-fx-background-color: #008000ff");
        root.setRight(buttons);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
