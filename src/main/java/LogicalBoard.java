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
    
    /**
     * Checks if a given move destination is valid for the given player.
     * 
     * @param Player The player in question
     * @param move The hypothetical destination to be checked
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
    
    /**
     * Puts a player in a given destination. This will make the move, always;
     * the given player and mave combo should be passed into validMove() just 
     * before makeMove().
     * 
     * @param player The player to move
     * @param move The desination to move the player to
     */
    public void makeMove(Player player, String move) {
	Scanner sc = new Scanner(move);
        int c = Integer.parseInt(sc.next());
        int r = Integer.parseInt(sc.next());
        Vertex destination = getVertexByCoord(c, r);
	
	player.setC(c);
	player.setR(r);
    }
    
    /**
     * Checks if a given wall is valid. The given location does not have to 
     * be a legal board position, and the direction does not have to be a legal
     * direction; in either of those cases, validWall() will simply return 
     * false.
     * 
     * @param sourceC column of wall position
     * @param sourceR row of wall position
     * @param direction the direction of the wall
     */
    private boolean validWall(int sourceC,int sourceR, String direction){
	
	if (sourceC>7 || sourceR>7 || sourceC<0 || sourceR<0)
	    return false; //wall must be on board and not in the 8th row or col
	
	/* Craig,
	 * Some of these aren't right; eg, subtracting 1 from the row gives you
	 * the row ABOVE, not below. See the board in Dr. Ladd's protocol:
	 * http://cs.potsdam.edu/Classes/405/assignments/project/protocol.txt
	 * Either way, I was able to factor out this logic. You can remove
	 * everything in the block if you want.
	 * - Lucas 3/24

        // below source VERTEX
        int belowC = sourceC; // Column of Vertex
        int belowR = sourceR - 1; // Row of Vertex
        
        // right of source VERTEX
        int rightC = sourceC + 1; // Column of Vertex
        int rightR = sourceR; // Row of Vertex
        
        // below to the right of source
        int belowRC = sourceC + 1; // Column of vertex
        int belowRR = sourceR - 1; // row of vertex
        */        
	
        // getting positions of the vertexes for this wall
        Vertex sourceV = getVertexByCoord(sourceC, sourceR);
        Vertex belowV = getVertexByCoord(sourceC, sourceR+1);
        Vertex rightV = getVertexByCoord(sourceC+1, sourceR);
        Vertex belowRV = getVertexByCoord(sourceC+1, sourceR+1);
        
	/* Craig,
	 * Not sure if you planned to use these somehow in the future, but 
	 * I was able to factor them out. Feel free to remove them if you won't
	 * need them.  
	 *     -Lucas 3/24
	
        //Edge sets of each vertex
        Set<Edge> sourceES = board.edgesOf(sourceV);
        Set<Edge> rightES = board.edgesOf(rightV);
        Set<Edge> belowES = board.edgesOf(belowV);
        Set<Edge> belowRES = board.edgesOf(belowRV);
        
        //Edge set of entire board;
        Set<Edge> edgeSet = board.edgeSet();
        */
            
        if(direction.toUpperCase()=="V"){
            if( board.containsEdge(sourceV, rightV) && 
	        board.containsEdge(belowV, belowRV))
                return true;
        } else if (direction.toUpperCase()=="H") {
            if( board.containsEdge(sourceV, belowV) &&
	        board.containsEdge(rightV, belowRV))
		return true;
        } else { //ie, direction was not h or v and thus invalid
	    return false;
	}
	
	/* Still necessary: for each player in the game, check if this wall 
	 * results in blocking that player's path to winning.
	 * (Is it okay to block off some of the winning squares, or must all
	 * 8 of them be available?)
	 * - Lucas, 3/24
	 */
	
        return false;
    }
    
    
    /**
     * Places a wall. This wall must be validated by validWall() BEFORE being 
     * placed. 
     *
     * @param wall The previously validated wall to be placed, as a string in
     *             the protocol form.
     * @return
     */
    public void placeWall(String wall){
        Scanner sc = new Scanner(wall);
        int cB = Integer.parseInt(sc.next()); // Column of beginning Vertex
        int rB = Integer.parseInt(sc.next()); // Row of beginning Vertex
        String direction = sc.next();
	
	// getting positions of the vertexes for this wall
        Vertex sourceV = getVertexByCoord(cB, rB);
        Vertex belowV = getVertexByCoord(cB, rB+1);
        Vertex rightV = getVertexByCoord(cB+1, rB);
        Vertex belowRV = getVertexByCoord(cB+1, rB+1);
	
	if (direction.toUpperCase()=="V") {
	    board.removeEdge(sourceV, rightV);
	    board.removeEdge(belowV, belowRV);
	} else {
	    board.removeEdge(sourceV, belowV);
	    board.removeEdge(rightV, belowRV);
	}
    }      
}
