/**
 * The controller class controlls the players in the GUI. The players are a 
 * stackpane. This class implements the Controller Interface. 
 */



import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/* This object handles the number of players in the game
 * 
 */

/**
 *
 * @author jed_lechner
 */
public class  Controller implements ControllerInterface{
    
    private int playerCount; // the number of players competing
    private int playerTurn; // which player turn it is
    
    // stackpane nodes for players
    private StackPane player1; 
    private StackPane player2;
    private StackPane player3;
    private StackPane player4;
    
    // circle nodes for players
    private Circle circle1; // circle to represent players
    private Circle circle2;
    private Circle circle3;
    private Circle circle4;
    
    // wall count for players
    private int walls1;
    private int walls2;
    private int walls3;
    private int walls4;

    
    
    /**
     * 
     * @param numOfPlayers The number of players in the game.
     * Constructor.
     */
    public Controller (int numOfPlayers) {
        if(numOfPlayers == 2 || numOfPlayers == 4) {
            this.playerCount = numOfPlayers;
            this.playerTurn = 1;
            setPlayers(numOfPlayers);
        } else {
            throw new IndexOutOfBoundsException();
        }
        
    }
    
    /**
     * 
     * @param pNum The player number.
     * @return The number of walls specific to the player number.
     */
    public int getWalls(int pNum) {
        switch(pNum) {
            case 1:
                return walls1;
            case 2:
                return walls2;
            case 3:
                return walls3;
            case 4:
                return walls4;
        }
        
        return -1;
    }
    
    /**
     * 
     * @param pNum The number of the player.
     * Decreases the number of walls specific to the player. 
     */
    public void setWalls(int pNum) {
        switch(pNum) {
            case 1:
                walls1--;
                break;
            case 2:
                walls2--;
                break;
            case 3:
                walls3--;
                break;
            case 4:
                walls4--;
                break;
        }
    }
    
    /**
     * 
     * @return The number count of players.
     */
    public int getPlayerCount() {
        return playerCount;
    }
    
    /**
     * 
     * @return The players turn, specific to the game.
     */ 
    public int getPlayerTurn(){
        return playerTurn;   
    }
    
    /**
     * Sets the players turn specific to the game.
     */
    public void setPlayerTurn(){
        if(playerCount > 2) {
            switch(playerTurn) {
                case 1: 
                    playerTurn = 4;
                    if(player4 == null) setPlayerTurn();
                    break;
                case 2: 
                    playerTurn = 3;
                    if(player3 == null) setPlayerTurn();
                    break;
                case 3: 
                    playerTurn = 1;
                    if(player1 == null) setPlayerTurn();
                    break;
                case 4:
                    playerTurn = 2;
                    if(player2 == null) setPlayerTurn();
                    break;
            }
        } else {
            switch(playerTurn) {
                case 1:
                    playerTurn = 2;
                    break;
                case 2: 
                    playerTurn = 1;
                    break;
            }
        }

    }

    
    /**
     * 
     * @param n The player to remove.
     * Removes the player specific to the parameter passed in.
     * Does not error check. It does nothing if n is too large or small.
     */
    public void removePlayer(int n) {
        switch(n) {
            case 1:
                player1 = null;
                circle1 = null;
                walls1 = 0; 
                break;
            case 2:
                player2 = null;
                circle2 = null;
                walls2 = 0;
                break;
            case 3:
                player3 = null;
                circle3 = null;
                walls2 = 0;
                break;
            case 4:
                player4 = null;
                circle4 = null;
                walls4 = 0; 
                break;
        }
    }
    
    /** 
     * 
     * @param n: The player turn
     * @return The player object of whose turn it is. 
     */
    public StackPane getPlayerNode(int n) {
        switch(n) {
            case 1:
                return player1;
            case 2: 
                return player2;
            case 3:
                return player3;
            case 4:
                return player4;    
        }
        return null;
    }
    
    /**
     * 
     * @param n: The number of players. 
     * Sets the player number and circle in a stackpane
     * Sets the players initial point
     */
    private void setPlayers(int n) {
        if(n == 4) {
            circle1 = new Circle(20.0, Color.BLUE);
            player1 = new StackPane(circle1, new Text("1"));
            walls1 = 5;
            
            circle2 = new Circle(20.0, Color.DARKGREEN);
            player2 = new StackPane(circle2, new Text("2"));
            walls2 = 5;
        
            circle3 = new Circle(20.0, Color.YELLOW);
            player3 = new StackPane(circle3, new Text("3"));
            walls3 = 5;
            
            circle4 = new Circle(20.0, Color.DARKORANGE);
            player4 = new StackPane(circle4, new Text("4"));
            walls4 = 5;
        } else {
            circle1 = new Circle(20.0, Color.BLUE);
            player1 = new StackPane(circle1, new Text("1"));
            walls1 = 10;
            
            circle2 = new Circle(20.0, Color.DARKGREEN);
            player2 = new StackPane(circle2, new Text("2"));
            walls2 = 10;
            
        }
        
    }
   
}

