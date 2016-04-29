

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * A board square, represented as a vertex in the board-state graph (located in 
 * LogicalBoard).
 */
public class Vertex {

    /** Whether or not a player is on this square */
    public boolean here;
    /** Column number */
    public int c;
    /** Row number */
    public int r; 

    /**
     * With no parameters, creates a vertex with placeholder coordinates: 
     * (-1, -1)
     */
    public Vertex(){
        this.r = -1;
        this.c = -1;
        here = false;
    }
    
    /**
     * Creates a vertex with the given column and row number.
     * @param c Column number
     * @param r Row number
     */
    public Vertex(int c, int r){
        this.c = c;
        this.r = r;
        this.here = false;
    }
    
    /**
     * Checks if there's a player here.
     * @return Whether or not a player is on this square.
     */
    public boolean isHere(){
        return here;
    }
    
    /**
     * Resets the column number of this vertex.
     * @param c The column number to set
     */
    public void setC(int c) {
       this.c=c;
    }

    /**
     * Resets the row number of this vertex.
     * @param r The row number to set
     */
    public void setR(int r) {
        this.r=r;
    }

    /** 
     * Returns a string representation.
     * @return "{[here], [col], [row]}"
     */
    @Override
    public String toString() {
        return "{"+here+", " + c + "," + r + "}";
    }

    /**
     * Sets the "here" flag.
     */
    public void placePlayer(){
        here=true;
    }
    
    /**
     * Unsets the "here" flag.
     */
    public void removePlayer(){
        here=false;
    }
}
