/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author craig
 */
public class Pawn {

    private final int startx = 100;  // These will be set to whatever is needed
    private final int starty = 100;  //  ***************************
    private int x;
    private int y;
    public Player player;
   

    /**Constructor Player - initializes pawn with Starting position and Player
     * 
     * @param player - Player Using this pawn
     */
    public Pawn(Player player) {
        this.x = this.starty;
        this.y = this.startx;
        this.player = player;
    }
    
    
    /**updatePosition - updates position of pawn after a move
     * 
     * @param newX - X after a move
     * @param newY - Y after a move
     */
    public void updatePosition(int newX, int newY){
        this.x = newX;
        this.y = newY;
    }
    
    
    
}
