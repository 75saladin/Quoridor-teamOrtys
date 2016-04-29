


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.jgrapht.graph.DefaultEdge;


/**
 * Edge for the graph in LogicalBoard which represents the game board. An edge 
 * is between two nodes (or board squares) if there is no wall between them. 
 * Further documentation of this class can be found in parent class.
 */
public class Edge extends DefaultEdge{
    
    /** serial id */
    private static final long serialVersionUID = 1L;

    /** 
     * Returns a node belonging to this edge.
     * @return A node of this edge. Since graph is undirected, either may be 
     * the source or destination. Also check getTarget() if you need a specific
     * node of this edge.  */
    @Override
    public Vertex getSource(){
        return (Vertex) super.getSource();
    }
    
    /** 
     * Returns a node belonging to this edge.
     * @return A node of this edge. Since graph is undirected, either may be 
     * the source or destination. Also check getSource() if you need a specific
     * node of this edge.  
     */
    @Override
    public Vertex getTarget(){
           return (Vertex) super.getTarget();
    }
    
    /**
     * Gets a string representation.
     * @return The string representation. "([node]:[node])\n"
     */
    @Override public String toString(){
           return "("+this.getSource().toString() + ":" + this.getTarget().toString()+ ")\n";
    }
    
    /**
     * Check equality of nodes belonging to this edge.
     * @return Whether or not the sources and targets are the .equal() each other.
     */
    public boolean equals(Edge e){
        return this.getSource().equals(e.getSource())&&this.getTarget().equals(e.getTarget());
    }
}
