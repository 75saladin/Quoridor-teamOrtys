

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author craig
 * 
 * Vertex Class is to be used with VertexFactory Class from JGraphT Library.
 */
public class Vertex {

    public boolean here;
    public int c;  // COlumn of vertex on Logical Graph
    public int r;  // Row of vertex on Logical Graph


    public Vertex(){
        this.r = -1;
        this.c = -1;
        here = false;
    }
    public Vertex(int c, int r){
        this.c = c;
        this.r = r;
        this.here = false;
    }
    
    public boolean isHere(){
        if(here)
            return true;
        return false;
    }
    
    /**
     *
     * @param c
     */
    public void setC(int c) {
       this.c=c;
    }

    /**
     *
     * @param r
     */
    public void setR(int r) {
        this.r=r;
    }

    @Override
    public String toString() {
        return "{"+here+", " + c + "," + r + "}";
    }

    /**
     *
     * @param player
     */
    public void placePlayer(){
        here=true;
    }
    
    /**
     *
     */
    public void removePlayer(){
        here=false;
    }

    /**
     *
     * @return
     */
    public boolean isPlayerHere(){
        return here;
    }
}
