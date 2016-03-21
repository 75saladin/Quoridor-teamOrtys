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

    private final int c;
    private final int r;
    private final String direction; //h or v
    private final String properties;

    /**
     * 
     * @param c - X position on board
     * @param r - R position on board
     * @param direction - direction of wall being placed
     */
    public Wall(int c, int r, String direction) {
        this.c = c;
        this.r = r;
        this.direction = direction;
	this.properties = "" + this.c + " " + this.r + " " + this.direction;
    }
    
    /*
     * @return X position of this wall
    */
    public int getCPos(){
        return this.c;
    }
    
    public String getOppositeDirection() {
	if (this.direction.toLowerCase().equals("h"))
	    return "v";
	else
	    return "h";
    }
    
    /**
     * @return Y position of this wall
     */
    public int getRPos(){
        return this.r;
    }
   
    public String getDirection(){
        return direction;
    }

    public String getProperties(){
	return properties;
    }
    
    public int[] getBlocked() {
	int[] temp;
	if (this.direction.toLowerCase().equals("h")){
	    temp = new int[] {this.c, this.r, this.c+1, this.r};
	}else{
	    temp = new int[] {this.c, this.r, this.c, this.r+1};
	}
	return temp;
    }

    
    public String toString(){
	return properties;
    }
}
