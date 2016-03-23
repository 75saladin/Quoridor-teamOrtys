


import javafx.scene.layout.Region;

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
    
    
    public abstract String getPlayerPosition(int num); //return the current players position
    
    public abstract Region getRootRegion();
    
    public abstract void buildWall(String move); // build a wall based on a string passed in
    
    public abstract void movePlayer(String move); // move a player based on a string passed in
    
    public abstract void launch(); // launch the game
    
    
}
