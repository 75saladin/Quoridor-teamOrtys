package EntireDirectory;



import java.awt.Point;
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
public class  Controller{
    
    private int playerCount; // the number of players competing
    private int playerTurn; // which player turn it is
    
    private Point position1; // position of player 1
    private Point position2; // position of player 1
    private Point position3; // position of player 1
    private Point position4; // position of player 1
    
    private StackPane player1; // stackpane nodes for player
    private StackPane player2;
    private StackPane player3;
    private StackPane player4;
    
    private Circle circle1; // circle to represent players
    private Circle circle2;
    private Circle circle3;
    private Circle circle4;

    
    
    // constructor
    public Controller (int numOfPlayers) {
        if(numOfPlayers == 2 || numOfPlayers == 4) {
            this.playerCount = numOfPlayers;
            this.playerTurn = 1;
            setPlayers(numOfPlayers);
        } else {
            throw new IndexOutOfBoundsException();
        }
        
    }
    
    // pre: none
    // returns: the player count
    public int getPlayerCount() {
        return playerCount;
    }
    
    // pre: none
    // returns: which players turn it is based on 
    public int getPlayerTurn(){
        return playerTurn;   
    }
    
    // pre: none
    // post: sets the player turn based off of the number of players. 
    public void setPlayerTurn(){
        System.out.println("Player count = " + playerCount);
        
        if(playerCount > 2) {
            switch(playerTurn) {
                case 1: 
                    System.out.println("Player turn = " + playerTurn);
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
                    if(player1 == null) setPlayerTurn();
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


    public void removePlayer(int n) {
        playerCount--;
        switch(n) {
            case 1:
                position1 = null;
                player1 = null;
                circle1 = null;
                break;
            case 2:
                position2 = null;
                player2 = null;
                circle2 = null;
                break;
            case 3:
                position3 = null;
                player3 = null;
                circle3 = null;
                break;
            case 4:
                position4 = null;
                player4 = null;
                circle4 = null;
                break;
        }
    }

    // pre: none
    // post: sets player count
    // typically only used if a player is kicked from the game
    public void setPlayerCount(int n) {
        playerCount = n;
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
     * @param p: The player to set the new point for.
     * @param col The column position of the moved player
     * @param row The row position of the moved player
     * Post: Sets the players point position
     */
    public void setPlayerPosition(int p, int col, int row) {
        if(col < 0 || row < 0 || col > 8 || row > 8) 
            throw new IllegalArgumentException();
        switch(p) {
            case 1:
                position1 = new Point(col, row);
            case 2: 
                position2 = new Point(col, row);
            case 3: 
                position3 = new Point(col, row);
            case 4: 
                position4 = new Point(col, row);
        }
        
    }
    
    /**
     * 
     * @param player: The player number to get their position on the board
     * @returns a point coordinate of the specific person. 
     */
    public Point getPlayerPosition(int player) {
        if(player > playerCount) 
            throw new IllegalArgumentException();
        switch(player) {
            case 1:
                return position1;
            case 2: 
                return position2;
            case 3: 
                return position3;
            case 4: 
                return position4;
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
        circle1 = new Circle(20.0, Color.BLUE);
        player1 = new StackPane(circle1, new Text("1"));
        position1 = new Point(4, 0);
        
        circle2 = new Circle(20.0, Color.DARKGREEN);
        player2 = new StackPane(circle2, new Text("2"));
        position2 = new Point(4, 8);
        
        if(n == 4) {
            circle3 = new Circle(20.0, Color.YELLOW);
            player3 = new StackPane(circle3, new Text("3"));
            position3 = new Point(0, 4);
            
            circle4 = new Circle(20.0, Color.DARKORANGE);
            player4 = new StackPane(circle4, new Text("4"));
            position4 = new Point(8, 4);
        }
        
    }

    public static void main(String[] args) {
        Controller c = new Controller(4);
        //System.out.println(c.getPlayerTurn());
        //c.setPlayerTurn();
        //System.out.println(c.getPlayerTurn());
        //c.setPlayerTurn();
        //System.out.println(c.getPlayerTurn());
        //c.setPlayerTurn();
        //System.out.println(c.getPlayerTurn());

        System.out.println("");
        c.removePlayer(4);
        c.setPlayerTurn();
        System.out.println(c.getPlayerTurn());
    }
   
}

