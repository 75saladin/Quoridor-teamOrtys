
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
    
    private Circle player1;
    private Circle player2;
    private Circle player3;
    private Circle player4;
    
    
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
        if(playerTurn == playerCount) {
            playerTurn = 1;
        }else {
            playerTurn++;
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
    public Circle getPlayerNode(int n) {
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
     */
    private void setPlayers(int n) {
        player1 = new Circle(25.0, Color.BLUE);
        player2 = new Circle(25.0, Color.DARKGREEN);
        if(n == 4) {
            player3 = new Circle(25.0, Color.YELLOW);
            player4 = new Circle(25.0, Color.DARKORANGE);
        } 
    }
   
}
