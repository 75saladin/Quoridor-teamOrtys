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

    public Player player;
    public int c;  // COlumn of vertex on Logical Graph
    public int r;  // Row of vertex on Logical Graph


    public Vertex(){
        this.r = -1;
        this.c = -1;
        player = null;
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
        return "Vertex{" + "player=" + player + ", c=" + c + ", r=" + r + '}';
    }

    /**
     *
     * @param player
     */
    public void placePlayer(Player player){
        this.player = player;
    }
    
    /**
     *
     */
    public void removePlayer(){
        this.player = null;
    }

    /**
     *
     * @return
     */
    public boolean isPlayerHere(){
        if(this.player == null)
            return false;
        return true;
    }
}
