
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


/* This class is the GUI for the game. Implements GUIInterface and extends Application */

/**
 *
 * @author jed_lechner
 */
public class GUI implements GUIInterface {
    
    private BorderPane root; // the root node of the board
    private GridPane grid; // the grid where the game will be implemented
    
    /* Players in the Game. Right now they are represented by circles */
    private Circle player1; 
    private Circle player2;
    private Circle player3;
    private Circle player4; 
    
    
    /**
     * @param player: The Player object. See Player.java
     *  Sets up the border pane layout and grid pane layout.
     */
    @Override
    public void setUp(Player player) {
        // place the board in the center
        // the controls on the right, description left, and label top
        root = new BorderPane();
        root.getStylesheets().addAll(this.getClass().getResource("Layout.css").toExternalForm());
        root.setId("root");
        
        grid = drawGrid(player.getPlayerCount());
        root.setCenter(grid);
        
        root.setLeft(setDescription());
        //root.setRight();
        root.setTop(setTitle());
        //root.setBottom();        
    }

    @Override
    public void buildWall(String move) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void movePlayer(String move) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void launch() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @param numOfPlayers: The number of players in the game 2 or 4
     * @return The board with a 16 x 16 grid
     */
    private GridPane drawGrid(int numOfPlayers) {
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER); 
        gp.setId("board"); // set the css id of the gridpane
        

        // loop through and add rectangles to create the board
        for(int i = 0; i < 17; i++) {
            for(int j = 0; j < 17; j++) {
                if(i % 2 == 0 && j%2==0) {
                    gp.add(new Rectangle(50, 50, Color.BROWN), i , j);
                    gp.add(new Text("(" + i + ", " + j + ")"), i, j);
                } else if(i % 2 != 0 && j % 2 == 0) { // vertical rectangles
                    gp.add(new Rectangle(5.0, 50, Color.WHITE), i, j);
                }else if(i % 2 == 0 && j % 2 != 0) { // horizontal rectangles
                    gp.add(new Rectangle(50, 5.0, Color.WHITE), i, j);
                }         
            }
        } 
        
        // default is 2 players, add the other two if there are more
            gp.add(player1, 8, 0);
            gp.add(player2, 8, 16);
        if(numOfPlayers == 4) {
            gp.add(player3, 0, 8);
            gp.add(player4, 16, 8);
        }
        return gp;
    }
    
    /**
     * 
     * @return A Title for the game
     */
    private Region setTitle() {
        Text text = new Text("QUORIDOR");
        text.setStyle("-fx-background-color: gray");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        
        StackPane stackPane = new StackPane();
        Insets inset = new Insets(20, 20, 20, 20);
        stackPane.setPadding(inset);
        
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(text);
        // set the max width 
        stackPane.setMinHeight(100);
        stackPane.getStylesheets().addAll(this.getClass().getResource("Layout.css").toExternalForm());
        stackPane.setId("title");
        return stackPane;
    }
    
    /**
     * 
     * @return A vertical box with the description.
     * Linked to description id in CSS file. 
     */
    private Region setDescription() {
        VBox vb = new VBox();
        
        vb.setPadding(new Insets(20,20,20,20));
        vb.setAlignment(Pos.CENTER);
        
        Label label = new Label("    Description     ");
        label.setAlignment(Pos.CENTER);
        
        
        vb.getChildren().add(label);
        vb.getStylesheets().addAll(this.getClass().getResource("Layout.css").toExternalForm());
        vb.setId("description");
        
        return vb;
    }
    
    /**
     * 
     * @return The bottom label of the game
     */
    private Region setBottom() {
        Label text = new Label("Team Morty's");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        StackPane stackPane = new StackPane();
        Insets inset = new Insets(20, 20, 20, 20);
        stackPane.setPadding(inset);
        
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getStylesheets().addAll(this.getClass().getResource("Layout.css").toExternalForm());
        stackPane.setId("bottom");
        stackPane.getChildren().add(text);
        // set the max width 
        stackPane.setMinHeight(200);
        return stackPane;
    }
}

   
