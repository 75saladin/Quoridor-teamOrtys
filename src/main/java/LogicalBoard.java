import com.sun.javafx.geom.Edge;
import java.util.Scanner;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.graph.ClassBasedVertexFactory;
import org.jgrapht.graph.SimpleGraph;

/**
 *
 * @author craig
 */
public class LogicalBoard{
	
    /**
     *
     */
    public UndirectedGraph<Vertex, Edge> board;

    /**
     *
     * @param playerCount
     */
    public LogicalBoard(){
            // Gotta populate the board
            board = new SimpleGraph<Vertex, Edge>(Edge.class);

            GridGraphGenerator<Vertex, Edge> graphGenerator =
                            new GridGraphGenerator<Vertex, Edge>(9, 9);

            VertexFactory<Vertex> vertexFactory =
                new ClassBasedVertexFactory<Vertex>(Vertex.class);

            graphGenerator.generateGraph(board, vertexFactory, null);
            // The graph is now constructed with no C,R Data, must fill
            int c = 0;
            int r = 0;
            // Iterate through each Vertex and put in its actual position
            // on the logical board
            for(Vertex v : board.vertexSet()){
                v.setC(c);   
                v.setR(r);  
                c++;        
                if(c==9){   // when you reach end of the row go to next
                    c=0;
                    r++;
                }
            }   
    }
    
    /*
     * getVertexByCoord - gets the Vertex object at that coordinate on the board
     * 
     * @param c - Column of vertex
     * @param r - Row of Vertex
     *
     * @return - Vertex object at that location on the board
     */
             
    public Vertex getVertexByCoord(int c,int r){
        for(Vertex v : board.vertexSet())
            if(v.c == c && v.r == r)
                return v;
        return null;
    }
        
    

    
    
    /*
     * validMove - Determines if provided destination is valid
     *
     * @param player
     * @param move
     * @return
     */
    public boolean validMove(Player player, String move){
        Scanner sc = new Scanner(move);
        int c = Integer.parseInt(sc.next());
        int r = Integer.parseInt(sc.next());
        Vertex Destination = getVertexByCoord(c, r);
        Vertex Source = getVertexByCoord(player.getC(), player.getR());
        //if the destination is outside the board there will be no vertex
        if(Destination != null)
            // if the destination is not the same as the current location
            if(!Destination.equals(Source))
                // if there is not a player in this location
                if(!Destination.isPlayerHere())
                    if(board.containsEdge(Source, Destination))
                        return true;
        // if there is no edge or Destination or there is a player 
        // on the destination or the source and destination are the same
        // the player cannot be a move here and needs to be kicked from the game
        return false;
    }
    
    /*
    * validWall - determines if wall is valed then places it
    *
    * parameters - cB - Column position of placed wall vertex
    *              rB - Row position of placed wall vertex
    *              Direction - direction of wall being placed
    */
    private boolean validWall(int sourceC,int sourceR, String Direction){
        // below source VERTEX
        int belowC = sourceC; // Column of Vertex
        int belowR = sourceR - 1; // Row of Vertex
        
        // right of source VERTEX
        int rightC = sourceC + 1; // Column of Vertex
        int rightR = sourceR; // Row of Vertex
        
        // below to the right of source
        int belowRC = sourceC + 1; // Column of vertex
        int belowRR = sourceR - 1; // row of vertex
        
        // getting positions of the vertexes for this wall
        Vertex sourceV = getVertexByCoord(sourceC, sourceR);
        Vertex belowV = getVertexByCoord(belowC,belowR);
        Vertex rightV = getVertexByCoord(rightC,rightR);
        Vertex belowRV = getVertexByCoord(belowRC, belowRR);
        
        // If any of these vertices are null wall cannot be placed;
        if(sourceV==null||belowV==null||rightV==null||belowRV==null)
            return false;
        
        //Edge sets of each vertex
        Set<Edge> sourceES = board.edgesOf(sourceV);
        Set<Edge> rightES = board.edgesOf(rightV);
        Set<Edge> belowES = board.edgesOf(belowV);
        Set<Edge> belowRES = board.edgesOf(belowRV);
        
        //Edge set of entire board;
        Set<Edge> edgeSet = board.edgeSet();
            
        if(Direction.toUpperCase()=="V"){
            Edge sourceE = board.getEdge(sourceV, rightV);
            Edge rightE = board.getEdge(rightV, sourceV);
            Edge belowE = board.getEdge(belowV,belowRV);
            Edge belowRE = board.getEdge(belowRV, belowV);
            if( edgeSet.contains(sourceE) && edgeSet.contains(rightE) &&
                edgeSet.contains(belowE)  && edgeSet.contains(belowRE)){
                    
                sourceES.remove(sourceE);
                rightES.remove(rightE);
                belowES.remove(belowE);
                belowRES.remove(belowRE);
                return true;
            }
        }else{
            Edge sourceE = board.getEdge(sourceV, belowV);
            Edge rightE  = board.getEdge(rightV, belowRV);
            Edge belowE  = board.getEdge(belowV,sourceV);
            Edge belowRE = board.getEdge(belowV, rightV);
            if( edgeSet.contains(sourceE) && edgeSet.contains(rightE) &&
                edgeSet.contains(belowE)  && edgeSet.contains(belowRE)){
                
                sourceES.remove(sourceE);
                rightES.remove(rightE);
                belowES.remove(belowE);
                belowRES.remove(belowRE);
                return true;
            }
        }
        return false;
    }
    
    
    /**
     *
     * @param wall
     * @return
     */
    public boolean placeWall(String wall){
        Scanner sc = new Scanner(wall);
        int cB = Integer.parseInt(sc.next()); // Column of beginning Vertex
        int rB = Integer.parseInt(sc.next()); // Row of beginning Vertex
        String Direction = sc.next();
        return validWall(cB, rB, Direction);
    }      
}
