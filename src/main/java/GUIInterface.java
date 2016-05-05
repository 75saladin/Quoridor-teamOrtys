


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jed_lechner
 */
public interface GUIInterface {
    
    /**
     *
     * @param move The move to update the board with. Either a wall or pawn move.
     */
    public abstract void update(String move); // updates the board with just move
    
    /**
     * 
     * @param c A four player controller. Two is the default.
     * Sets the game controller to four player. Two is the default. 
     */
    public abstract void setPlayer(Controller c);
    
    /**
     * Stops the application from running.
     */
    public abstract void stopApplication();
    
    /**
     * @param The winner of the game.
     * Plays music if the player wins the game.
     */
    public abstract void winGame(int winner);
    
    /**
     * @param The player number to remove.
     * @throws IllegalArgumentException if the number is < 0 or > 4.
     * Removes the player number.
     */
    public abstract void removePlayer(int num);
    
    /**
     * @param A boolean to make the click events possible.
     * @return A move for human player.
     * Calls TRUMPwall() to obtain a click event. The client then updates the move.
     * 
     */
    public abstract String getMove(boolean p);
    
    /**
     * Sets the move to null, and removes the ability to click on the board. 
     */
    public abstract void setMove();
    
    /**
     * 
     * @param names The names of the AI's playing the games.
     * 
     */
    public abstract void setPlayerArray(String[] names);
   
}
