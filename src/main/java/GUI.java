
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
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
import javafx.stage.Stage;


/* This class is the GUI for the game. Implements GUIInterface and extends Application */

/**
 *
 * @author jed_lechner
 */
public class GUI extends Application implements GUIInterface {
    
    private BorderPane root; // the root node of the board
    private GridPane grid; // the grid where the game will be implemented
        
    /* Players in the Game. Right now they are represented by circles */
    private Controller player; 
    
    private TextArea output = null;
    
    // on off latch
    public static final CountDownLatch latch = new CountDownLatch(1);
    
    // instantiation of class
    public static GUI gui = null;
    
    /**
     * Constructor
     */
    public GUI() {
        guiStartUpTest(this);
        player = new Controller(2); 
    }
    
    // waiting for gui to set up
    public static GUI waitForGUIStartUpTest()  {
        try {
            latch.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return gui;

    }
    
    /**
     * 
     * @param g: Passing in parameter to initialize gui
     */
    public static void guiStartUpTest(GUI g) {
        gui = g;
        latch.countDown();
    }
    

    /**
     * tester method
     */
    public void printSomething() {
        System.out.println("you called a method on the application");
    }
    
    /**
     * 
     * @param c: The player object to set up with
     * Used for four player game
     */
    public void setPlayer(Controller c) {
        player = c;
    }
    
    /**
     * Sets up the initial configurations for the gui
     */
    @Override
    public void init() {
        // place the board in the center
        // the controls on the right, description left, and label top
        root = new BorderPane();
        
        //root.setId("root");
	//root.getStylesheets().addAll(this.getClass().getResource("Layout.css").toExternalForm());
        
        grid = drawGrid(player.getPlayerCount());
        
        root.setCenter(grid);
        
        
        root.setLeft(setDescriptionRegion());
        root.setRight(setRightRegion());
        root.setTop(setTitleRegion());
        root.setBottom(setBottomRegion()); 
        TRUMPwall();
    }
    
    /**
     * 
     * @param stage: Main stage to work off
     * Entry point for javaFX Applications
     */
    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(root, 1000, 1000, Color.BLUE);
        stage.setTitle("QUORIDOR");
        
        stage.setScene(scene);
        stage.show();
        
    }
    
    /**
     * 
     * @param num: The number of player
     * @return the player position of the player you asked for
     */
    @Override
    public String getPlayerPosition(int num) {
        assert(num < 4);
        int row = convert(grid.getRowIndex(player.getPlayerNode(num)));
        int column = convert(grid.getColumnIndex(player.getPlayerNode(num)));
        return "Player:" + num + " c:" + column + " r:" + row;
    }
    
    /**
     * 
     * @return the root region of the gridPane
     */
    @Override
    public Region getRootRegion() {
        return root;
    }

    /**
     * 
     * @param column: the column to move the player
     * @param row: the column to move the player
     */
    @Override
    public void buildWall(int column, int row) {
//        column = revert(column); row = revert(row);
//        grid.add(new Rectangle(5.0, 50.0, Color.LAWNGREEN), column, row);
//        row+=2;
//        grid.add(new Rectangle(5.0, 50.0, Color.LAWNGREEN), column, row);
//        player.setPlayerTurn();
    }


    /**
     * 
     * @param col: the column to move the player
     * @param nrow: the row to move the player
     * moves the player to correct grid
     */
    @Override
    public void movePlayer(int col, int nrow) {
        final int c = revert(col);
        final int r = revert(nrow);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                grid.getChildren().remove(player.getPlayerNode(player.getPlayerTurn()));
                System.out.println("Moving player");
                grid.add(player.getPlayerNode(player.getPlayerTurn()), c, r);
                player.setPlayerTurn();
            }
        });
          
    }

    /**
     * 
     * @param n: row or column to revert to 17x17 grid
     * @return correct row or column
     */
    private int revert(int n) {
        return n*2;
    }
    
    /**
     * 
     * @param column: the column number to convert
     * @return the converted column based on the 8x8 board
     */
    private int convert(int n) {
        if(n % 2 == 0) {
            return n / 2;
        } else {
            return n / 2 + 1;
        }
    }
    
    
    /**
     * 
     * @param numOfPlayers: The number of players in the game 2 or 4
     * @return The board with a 16 x 16 grid
     */
    private GridPane drawGrid(int numOfPlayers) {
        player = new Controller(numOfPlayers);
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
        gp.add(player.getPlayerNode(1), 8, 0);
        gp.add(player.getPlayerNode(2), 8, 16);
        if(numOfPlayers == 4) {
            gp.add(player.getPlayerNode(3), 0, 8);
            gp.add(player.getPlayerNode(4), 16, 8);
        }
        return gp;
    }
    
    /**
     * 
     * @return A Title for the game
     */
    private Region setTitleRegion() {
        Text text = new Text("QUORIDOR");
        text.setStyle("-fx-background-color: gray");
        text.setId("title_text");
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
    private Region setDescriptionRegion() {
        VBox vb = new VBox();
        
        vb.setPadding(new Insets(20,20,20,20));
        vb.setAlignment(Pos.CENTER);
        
        Label label = new Label("    Move     ");
        label.setAlignment(Pos.CENTER);
        output = new TextArea();
        output.setWrapText(true);
        output.setPrefWidth(200);
        output.setPrefHeight(100);
        
        vb.getChildren().addAll(label, output);
        vb.getStylesheets().addAll(this.getClass().getResource("Layout.css").toExternalForm());
        vb.setId("description");
        
        return vb;
    }
    
    /**
     * 
     * @return The bottom label of the game
     */
    private Region setBottomRegion() {
        Label text = new Label("Team Morties");
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
    
    // used to set on mouseClick for game
    // note the board can only be clicked if it is your turn
    // and there isn't a wall there
    private Region setRightRegion() {
        Button up = new Button("UP");       
        Button down = new Button("DOWN");      
        Button left = new Button("LEFT");
        Button right = new Button("RIGHT");
        
        // needs to know if there is a wall or other player in that spot 
        // if there is handle the specific situation
        // need move validator
                
        // up moves the player node up two rows
        up.setOnAction((ActionEvent e) -> {
            int row = GridPane.getRowIndex(player.getPlayerNode(player.getPlayerTurn()))-2;
            int column = GridPane.getColumnIndex(player.getPlayerNode(player.getPlayerTurn()));
            grid.getChildren().remove(player.getPlayerNode(player.getPlayerTurn()));
            grid.add(player.getPlayerNode(player.getPlayerTurn()), column, row);
            output.clear();
            output.appendText("Player " + player.getPlayerTurn() + " moved to " +
                              "Column " + convert(column) + " Row " + convert(row) + "\n\n");
            player.setPlayerTurn();
            
        });
        
        // down moves the player node down two rows
        down.setOnAction((ActionEvent e) -> {
            int row = GridPane.getRowIndex(player.getPlayerNode(player.getPlayerTurn())) + 2;
            int column = GridPane.getColumnIndex(player.getPlayerNode(player.getPlayerTurn()));
            grid.getChildren().remove(player.getPlayerNode(player.getPlayerTurn()));
            grid.add(player.getPlayerNode(player.getPlayerTurn()), column, row);
            output.clear();
            output.appendText("Player " + player.getPlayerTurn() + " moved to " +
                              "Column " + convert(column) + " Row " + convert(row) + "\n\n");
            player.setPlayerTurn();
        });
        
        // left moves the player node left two columns
        left.setOnAction((ActionEvent e) -> {
            int row = GridPane.getRowIndex(player.getPlayerNode(player.getPlayerTurn()));
            int column = GridPane.getColumnIndex(player.getPlayerNode(player.getPlayerTurn())) - 2;
            grid.getChildren().remove(player.getPlayerNode(player.getPlayerTurn()));
            grid.add(player.getPlayerNode(player.getPlayerTurn()), column, row);
            output.clear();
            output.appendText("Player " + player.getPlayerTurn() + " moved to " +
                              "Column " + convert(column) + " Row " + convert(row) + "\n\n");
            player.setPlayerTurn();
        });
        
        // right moves the player node right two columns
        right.setOnAction((ActionEvent e) -> {
            int row = GridPane.getRowIndex(player.getPlayerNode(player.getPlayerTurn()));
            int column = GridPane.getColumnIndex(player.getPlayerNode(player.getPlayerTurn())) + 2;
            grid.getChildren().remove(player.getPlayerNode(player.getPlayerTurn()));
            grid.add(player.getPlayerNode(player.getPlayerTurn()), column, row);
            output.clear();
            output.appendText("Player " + player.getPlayerTurn() + " moved to " +
                              "Column " + convert(column) + " Row " + convert(row) + "\n\n");
            player.setPlayerTurn();
        });

        // set the minimum width of the buttons
        up.setMinWidth(100.0);
        down.setMinWidth(100.0);
        left.setMinWidth(100.0);
        right.setMinWidth(100.0);
        // create a label for the controls called controls
        Label label = new Label("      Controls       ");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        VBox buttons = new VBox(10, label, up, down, left, right);
        buttons.setAlignment(Pos.CENTER);
        buttons.setStyle("-fx-background-color: #008000ff");
        return buttons;
    }
    

    // pre: none
    // post: sets a wall in the area clicked. It will cover two rows or two columns
    // depending on where you click. As of right now the click is between the squares
    private void TRUMPwall() {
        List <Node> childrens = grid.getChildren();
        
        // still need valid wall placement check
        
        // loop through and add the event to each node
        childrens.stream().forEach((node) -> {
            node.setOnMousePressed((MouseEvent event) -> {
                int row = GridPane.getRowIndex(node);
                int column = GridPane.getColumnIndex(node);
                if(row % 2 == 0 && column % 2 != 0 && row != 16) { // vertical wall
                    grid.add(new Rectangle(5.0, 50.0, Color.LAWNGREEN), column, row);
                    row+=2;
                    grid.add(new Rectangle(5.0, 50.0, Color.LAWNGREEN), column, row);
                    player.setPlayerTurn();
                } else if (row % 2 != 0 && column % 2 == 0 && column != 16) { // horizontal wall
                    grid.add(new Rectangle(50, 5.0, Color.LAWNGREEN), column, row);
                    column += 2;
                    grid.add(new Rectangle(50, 5.0, Color.LAWNGREEN), column, row);
                    player.setPlayerTurn();
                }
            });
        });
    }
    
    
    // this sets the effect on the player
    // currently in progress
    private void setEffectOnPlayer() {
        StackPane sp = new StackPane();
        sp.getStylesheets().addAll(this.getClass().getResource("Layout.css").toExternalForm());
        sp.getChildren().addAll(player.getPlayerNode(player.getPlayerTurn()));
        sp.setId("circles");
        sp.getChildren().addAll(player.getPlayerNode(player.getPlayerTurn()));
    }    
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
   
