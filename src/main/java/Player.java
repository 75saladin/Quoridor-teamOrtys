/* This object handles the number of players in the game
 * 
 */

/**
 *
 * @author jed_lechner
 */
public class Player {
    
    private int playerCount; // the number of players competing
    private int playerTurn; // which player turn it is
    
    // constructor
    public Player (int numOfPlayers) {
        if(numOfPlayers == 2 || numOfPlayers == 4) {
            this.playerCount = numOfPlayers;
            this.playerTurn = 1;
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
    
    
    
}
