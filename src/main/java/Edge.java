


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.jgrapht.graph.DefaultEdge;


/**
 */
public class Edge extends DefaultEdge{
    
    
    private static final long serialVersionUID = 1L;

    @Override
    public Vertex getSource(){
        return (Vertex) super.getSource();
    }
    @Override
    public Vertex getTarget(){
           return (Vertex) super.getTarget();
    }
    
    @Override public String toString(){
           return "("+this.getSource().toString() + ":" + this.getTarget().toString()+ ")\n";
    }
    public boolean equals(Edge e){
        return this.getSource().equals(e.getSource())&&this.getTarget().equals(e.getTarget());
    }
}
