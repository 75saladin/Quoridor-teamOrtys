/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author
 */
public class Wall {

    private final int x;
    private final int y;
    private final String direction; //h or v

    /**
     * 
     * @param x - X position on board
     * @param y - Y position on board
     * @param direction - direction of wall being placed
     */
    public Wall(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    
    /*
     * @return X position of this wall
    */
    public int getXPos(){
        return this.x;
    }
    
    /**
     * @return Y position of this wall
     */
    public int getYPos(){
        return this.y;
    }
   
    public String getDirection(){
        return direction;
    }
    
    public int[4] getBlocked() {
	if (this.direction.toLowerCase.equals("h")
	    int[] temp = new int[this.x, this.y, this.x+1, this.y];
	else 
	    int[] temp = new int[this.x, this.y, this.x, this.y+1];
	return temp;
}
