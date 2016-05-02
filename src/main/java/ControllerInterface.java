
import javafx.scene.layout.StackPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jed_lechner
 */
public interface ControllerInterface {
    
    
    public abstract int getWalls(int pNum); // returns the walls specific to the player passed in
    
    public void setWalls(int pNum); // decrements wasll specific to the player passed in
    
    public int getPlayerCount(); // returns the player count
    
    public int getPlayerTurn(); // gets the current players turn
    
    public void setPlayerTurn(); // changes the players turn based on a move
    
    public void removePlayer(int n); // removes the player passed in
    
    public StackPane getPlayerNode(int n); // Gets the specific node for that player obj
    
}
