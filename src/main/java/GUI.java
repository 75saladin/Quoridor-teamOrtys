
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    
    private TextArea output = null; // text area to broadcast game events

    private GridPane loserBox = new GridPane(); // the loserBox to put kicked players
    
    private boolean person = false;
    
    private String move;
    
    
    // launches threads simultaneously
    public static final CountDownLatch latch = new CountDownLatch(1);
    
    // instantiation of class, utilized for external use of appliations methods
    public static GUI gui = null;
    
    /**
     * Constructor
     */
    public GUI() {
        player = new Controller(2);
        guiStartUpTest(this);
        
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
     * @param g: Passing in parameter to initialize an instantion of GUI within itself
     *
     */
    public static void guiStartUpTest(GUI g) {
        gui = g;
        latch.countDown();
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
     *  closes the application upon call
     */
    public void stopApplication() {
        try {
            Platform.exit(); 
        } catch(Exception e) {
            e.printStackTrace();
        }
       
    }
    
    /**
     * Sets up the initial configurations for the gui
     */
    @Override
    public void init() {
        // place the board in the center
        // the controls on the right, description left, and label top
        root = new BorderPane();
        grid = drawGrid();
        root.setCenter(grid);
        root.setLeft(setDescriptionRegion());
        root.setTop(setTitleRegion());
        root.setBottom(setBottomRegion()); 
        centerAlignNodes();
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
        //stage.setFullScreen(true);
        
    }

    /**
     * @param move: The move to update the board.
     * Calls buildWall or movePlayer based on the message passed in
     */
    @Override
    public void update(String move) {
        move = move.toLowerCase();
        int column = 0;
        int row = 0;
        String[] s = move.split(" ");
        if(move.contains("h") || move.contains("v")) {
            column = parseToInt(s[0]);
            row = parseToInt(s[1]);
            buildWall(column, row, s[2]);
        } else {
            column = parseToInt(s[0]);
            row = parseToInt(s[1]);
            movePlayer(column, row);
        }

    }


    /**
     * 
     * @param column: the column to move the player
     * @param row: the column to move the player
     * builds a wall based on the c, r, and direction
     */
    private void buildWall(int column, int row, String direction) {
        final int c = revert(column);
        final int r = revert(row);
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Building wall");
                if(direction.equals("v")) {
                    grid.add(new Rectangle(7.0, 50, Color.WHITE), c + 1, r);
                    grid.add(new Rectangle(7.0, 50, Color.WHITE), c + 1, r + 2);
                } else {
                    grid.add(new Rectangle(50, 7.0, Color.WHITE), c, r + 1);
                    grid.add(new Rectangle(50, 7.0, Color.WHITE), c+2, r +1);
                }
                output.appendText("Player " + player.getPlayerTurn() + " placed wall " +
                              "column " + column + " row " + row + " " + direction + "\n\n");
                player.setPlayerTurn();
            }
        });
    }


    /**
     * 
     * @param col: the column to move the player
     * @param nrow: the row to move the player
     * moves the player to correct grid
     */
    private void movePlayer(int col, int nrow) {
        final int c = revert(col);
        final int r = revert(nrow);
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int turn = player.getPlayerTurn();
                grid.getChildren().remove(player.getPlayerNode(turn));
                grid.add(player.getPlayerNode(turn), c, r);
                output.appendText("Player " + player.getPlayerTurn() + " moved to " +
                              "Column " + col + " Row " + nrow + "\n\n");
                player.setPlayerPosition(turn, col, nrow);
                player.setPlayerTurn();
            }
        });
          
    }

    /** 
     * @param num: The player to kick.
     * Kicks the player passed in.
     */
    public void removePlayer(int num) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                grid.getChildren().remove(player.getPlayerNode(num));
                player.removePlayer(num);
                player.setPlayerTurn(); // i think
                try {
                    Thread.sleep(500);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
    }
    
    /**
     * 
     * @returns the move string generated by user click
     */
    public String getMove() {
        if(person) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    TRUMPwall();
                }
            });
        }
        
        return move;
        
            
    }

    public void setMove(){
        move = null;
    }
    
    public void setPerson(boolean set) {
        person = set;
    }
    


    // method to parse an integer from a string    
    private int parseToInt(String s) {
        try { 
            return Integer.parseInt(s);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
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
    private GridPane drawGrid() {
        GridPane gp = new GridPane();
        // loop through and add rectangles to create the board
        for(int i = 0; i < 17; i++) {
            for(int j = 0; j < 17; j++) {
                if(i % 2 == 0 && j%2==0) {
                    
                    gp.add(new Rectangle(50, 50, Color.BROWN), i , j);
                    //gp.add(new Text("(" + i + ", " + j + ")"), i, j);
                } else if(i % 2 != 0 && j % 2 == 0) { // vertical rectangles
                    gp.add(new Rectangle(7.0, 50, Color.BLACK), i, j);
                }else if(i % 2 == 0 && j % 2 != 0) { // horizontal rectangles
                    gp.add(new Rectangle(50, 7.0, Color.BLACK), i, j);
                } 
                
            }
        } 
        
        // default is 2 players, add the other two if there are more

        gp.add(player.getPlayerNode(1), 8, 0);
        gp.add(player.getPlayerNode(2), 8, 16);
        if(player.getPlayerCount() == 4) {
            gp.add(player.getPlayerNode(3), 0, 8);
            gp.add(player.getPlayerNode(4), 16, 8);
            
        }
        
        
        gp.setAlignment(Pos.CENTER);
        gp.getStylesheets().addAll(this.getClass().getResource("Layout.css").toExternalForm());
        gp.setId("board"); // set the css id of the gridpane
        
        return gp;
    }
    
    /**
     * Aligns all of the nodes to the center
     */
    private void centerAlignNodes() {
        List <Node> childrens = grid.getChildren();
        
        for(Node c : childrens) {
            GridPane.setHalignment(c, HPos.CENTER);
            GridPane.setValignment(c, VPos.CENTER);
        }
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
        stackPane.setMinHeight(135);
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
        Label text = new Label("Team Our Tease");
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
                    move = convert(column) + " " +  convert(row) + " v";
                    grid.add(new Rectangle(5.0, 30.0, Color.BLUE), column, row);
                    row+=2;
                    grid.add(new Rectangle(5.0, 30.0, Color.BLUE), column, row);
                    //player.setPlayerTurn();
                } else if (row % 2 != 0 && column % 2 == 0 && column != 16) { // horizontal wall
                    move = convert(column) + " " + convert(row) + " h";
                    grid.add(new Rectangle(30, 5.0, Color.BLUE), column, row);
                    column += 2;
                    grid.add(new Rectangle(30, 5.0, Color.BLUE), column, row);
                    //player.setPlayerTurn();
                } else {
                    //player.setPlayerTurn();
                    move = convert(column) + " " + convert(row);

                }
                System.out.println(move);
            });
        });
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
   
