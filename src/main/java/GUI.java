/**
 * The GUI is the user interface for the game. This class utilizes javafx.
 * More specifically the class utilizes a BorderPane as it's root scene and
 * and a GridPane to display the actual game. The players are StackPanes and 
 * the combination of text and Circle objects. This board does allow a human 
 * interaction, however this interaction needs to be set up through the client.
 * 
 */
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;





/**
 *
 * @author jed_lechner
 */
public class GUI extends Application implements GUIInterface {
    

    private BorderPane root; // the main stage of the javafx app
    private GridPane grid; // the grid where the game will be implemented
        
    /* Players in the Game. Right now they are represented by circles */
    private Controller player; 
    
    private TextArea output = null; // text area to broadcast game events
    
    private boolean person = false;
    
    private String move;
    
    private String[] playerNames;
    
    private TextArea walls = null; // text area 
    
    private int numberOfMoves = 0;
    
    
    // launches threads in synch
    private static final CountDownLatch latch = new CountDownLatch(1);
    
    // instantiation of class, utilized for external use of appliations methods
    private static GUI gui = null;
    
    /** audo clip for player and wall movement **/
    private static final AudioClip ALERT_AUDIOCLIP = new AudioClip(GUI.class.getResource("/boop.wav").toString());
    
    /** Audio clips for winning the game **/
    private static final AudioClip OH_YEA = new AudioClip("https://d2eopxgp627wep.cloudfront.net/ps/audios/000/000/547/original/Oooo_yeah__caaan_doo!.wav?1441333631");
    
    private static final AudioClip WRECKED = new AudioClip("https://d2eopxgp627wep.cloudfront.net/ps/audios/000/000/533/original/Riggity.wav?1441330423");
    
    private static final AudioClip WUBBA = new AudioClip("https://d2eopxgp627wep.cloudfront.net/ps/audios/000/000/533/original/Riggity.wav?1441330423");
    
    
    /**
     * Constructor.
     */
    public GUI() {
        player = new Controller(2);
        guiStartUpTest(this);
        
    }
    

    /**
     * Statically instantiates a GUI object for external reference and 
     * utilizes a countdownlatch to synchronize the launching of the application
     * threads. 
     * @return An instantiation of the GUI for external class reference. 
     */
    public static GUI waitForGUIStartUpTest()  {
        try {
            latch.await();
        } catch(InterruptedException e) {
            //e.printStackTrace();
        }
        return gui;

    }
    
    /**
     * Sets gui object with internal instantiation and starts the coundown
     * on the countdownlatch.
     * @param g: Passing in parameter to initialize an instantiation of GUI object.
     *
     */
    public static void guiStartUpTest(GUI g) {
        gui = g;
        latch.countDown();
    }
    
    /**
     * Sets the controller for the player objects in the game. Only
     * called during four player game.
     * @param c The player object to set up with. Used for four player game.
     * 
     */
    public void setPlayer(Controller c) {
        player = c;
    }
    
    /**
     *  Closes the application upon call.
     */
    public void stopApplication() {
        try {
            Platform.exit(); 
        } catch(Exception e) {
            //e.printStackTrace();
        }
       
    }
    
    /**
     * Sets up the initial configurations for the user interface.
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
     * @param stage Main stage.
     * Entry point for javaFX Applications.
     */
    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(root, 1000, 1000, Color.BLUE);
        stage.setTitle("QUORIDOR");
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        
        stage.setScene(scene);
        stage.show();
        //stage.setFullScreen(true);
        
    }

    /**
     * @param move Updates board with move.
     * Calls buildWall or movePlayer based on the message passed in.
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
     * Plays an mp3 when the player wins the game.
     */
    public void winGame(int winner) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Random rand = new Random();
                int n = rand.nextInt(3);
                output.appendText("OOOOOOOOH YEAAAA!!!\n");
                output.appendText("Player " + winner + ": " + playerNames[winner-1] + " won the game!");
                switch(n) {
                    case 0:
                        GUI.WRECKED.play(100.0);
                        break;
                    case 1:
                        GUI.OH_YEA.play(100.0);
                        break;
                    case 2:
                        GUI.WUBBA.play(100.0);
                        break;
                                
                }
            }
        }) ;
    }

        /** 
     * @param num The player number to kick. 
     * @throws IllegalArgumentException if the parameter is less than 0 or 
     * greater than 4.
     * Kicks the player passed in.
     */
    public void removePlayer(int num) {
        if(num < 0 || num > 4) 
            throw new IllegalArgumentException("Number < 0 or > 4");
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
     * Gets a move generated by a user click event.
     * @return The move string generated by user click event.
     */
    public String getMove(boolean p) {
        person = p;
        if(p) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    TRUMPwall();
                }
            });
        }
        
        return move;
        
            
    }

    /**
     * Sets the previous clicked move to null. Only used with human server.
     */
    public void setMove(){
        move = null;
        person = false;
    }
    
    /** 
     * 
     * @param names: The array of player names.
     * Sets the player 
     */
    public void setPlayerArray(String[] names) {
        playerNames = names;
    }


    /**
     * 
     * @param column The column to move the player
     * @param row The column to move the player
     * builds a wall based on the c, r, and direction
     */
    private void buildWall(int column, int row, String direction) {
        final int c = revert(column);
        final int r = revert(row);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(direction.equals("v")) {
                    grid.add(new Rectangle(7.0, 50, Color.WHITE), c + 1, r);
                    grid.add(new Rectangle(7.0, 50, Color.WHITE), c + 1, r + 2);
                } else {
                    grid.add(new Rectangle(50, 7.0, Color.WHITE), c, r + 1);
                    grid.add(new Rectangle(50, 7.0, Color.WHITE), c+2, r +1);
                }
                GUI.ALERT_AUDIOCLIP.play();
                output.appendText("-----------------\n");
                output.appendText("Number of moves: " + numberOfMoves++ + "\n");
                player.setWalls(player.getPlayerTurn()); // decrements this players walls
                System.out.println(Arrays.toString(playerNames));
                for(int i = 1; i <= playerNames.length; i++) {
                    output.appendText("Player " + i + ": " + playerNames[i-1] + "\n" + 
                            " Walls Remaining: " + player.getWalls(i) + "\n");
                }
                output.appendText("-----------------\n");
                
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
                GUI.ALERT_AUDIOCLIP.play();
                output.appendText("-----------------\n");
                output.appendText("Number of moves: " + numberOfMoves++ + "\n");
                for(int i = 1; i <= player.getPlayerCount(); i++) {
                    output.appendText("Player " + i + ": " + playerNames[i-1] + "\n" + 
                            "Walls Remaining: " + player.getWalls(i) + "\n");
                }
                output.appendText("-----------------\n");
                player.setPlayerTurn();
            }
        });
          
    }



    /**
     * 
     * @param s: An integer in string form.
     * @return: The parsed integer.
     */    
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
     * @param n: Row or column to revert to 17x17 grid
     * @return Correct row or column
     */
    private int revert(int n) {
        return n*2;
    }
    
    /**
     * 
     * @param column: The column number to convert
     * @return The converted column based on the 8x8 board
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
     * @return The board with a 17 x 17 grid
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
        output.setPrefHeight(400);
        
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
    

    /**
     * Sets mouse pressed event on all board nodes. Used for wall building
     * and pawn movement. Used only with Human Server. 
     */
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
    
    
}
   
