import com.sun.javafx.geom.Edge;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import static jdk.nashorn.internal.objects.NativeRegExp.source;

import org.jgrapht.alg.DijkstraShortestPath;
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
    public LogicalBoard(int playerCount){
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
    
    /**
     *
     * @return set of edges in board
     */
    public Set<Edge> getEdgeSet(){
        return board.edgeSet();
    }
    
    /**
     *
     * @return set of vertices in board
     */
    public Set<Vertex> getVertexSet(){
        return board.vertexSet();
    }
    
    
    
    /*
     * getVertexByCoord - returns Vertex object at that coordinate on the board
     * 
     * @param c - Column of vertex
     * @param r - Row of Vertex
     * @return - Vertex object at that location on the board
     */
    public Vertex getVertexByCoord(int c,int r){
        for(Vertex v : board.vertexSet())
            if(v.c == c && v.r == r)
                return v;
        return null;
    }

    /*
    *  initPlayer - Initializes players on the logical board.
    *                Must be done for each player playing.  
    *               Player will be initialized with that player's coordinates
    *
    *@parm player - player being initialized
    */
    public void initPlayer(Player player){
        Vertex vertex = getVertexByCoord(player.getC(), player.getR());
        vertex.placePlayer(player);
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
	destination.placePlayer(player);
	player.setC(c);
	player.setR(r);
    }
    
    /**
     * Places a wall. This wall must be validated by validWall() BEFORE being 
     * placed. 
     *
     * @param wall The previously validated wall to be placed, as a string in
     *             the protocol form.
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

    private boolean validWall(String wall){
        // cannot leave if one thing is true, must check all
        // but if one thing is false we return
        boolean valid = false;
        Scanner sc = new Scanner(wall);
        int sourceC = Integer.parseInt(sc.next());
        int sourceR = Integer.parseInt(sc.next());
        String direction = sc.next().toUpperCase();
        
        //wall must be on board and not in the 8th row or col
        if (sourceC>7 || sourceR>7 || sourceC<0 || sourceR<0)
            return false; 
        
        // getting positions of the vertexes for this wall
        Vertex sourceV = getVertexByCoord(sourceC, sourceR);
        Vertex belowV = getVertexByCoord(sourceC, sourceR+1);
        Vertex rightV = getVertexByCoord(sourceC+1, sourceR);
        Vertex belowRV = getVertexByCoord(sourceC+1, sourceR+1);
        
            if(direction.equals("V"))
                if( board.containsEdge(sourceV, rightV) &&
                        board.containsEdge(belowV, belowRV))
                    valid = true;
            else if(direction.equals("H"))
                if( board.containsEdge(sourceV, belowV) &&
                        board.containsEdge(rightV, belowRV))
                    valid = true;
            else
                return false;
        
        // Starting logic to test if winners path is blocked......
        Set<Player> playerList = getPlayers(); // need to check all players
        Vertex source;
        // need this edgeset to test if path is blocked
        Set<Edge> EdgeSetToRemove = getEdgesToRemove(wall);
        for(Player p : playerList){
            // vertex that contains this Player p
            source = getVertexByCoord(p.getC(), p.getR()); 
            // if path is blocked return false
            if(pathBlocked(source,p.getPlayerNum(),EdgeSetToRemove)){
                return false;
            }
        }
        // if we get here return valid which should be true.....
        return valid;
    }
    
    // pathBlocked - Uses Dijkstras algorithm to make sure that the path to the 
    //               Winner row is not blocked by a player placed wall
    public boolean pathBlocked(Vertex source,int playerNum,Set<Edge> edgeSet){
        boolean blocked = true;
        DijkstraShortestPath<Vertex,Edge> Dijkstra;
        Vertex destination;
        Set<Edge> boardEdgeSet = getEdgeSet();
        
        // Removing Edges to place wall temporarily to check if winning path is 
        //  blocked will replace at the end before returning
        for(Edge e : edgeSet)
            boardEdgeSet.remove(e);

        switch(playerNum){
        
            case 1:
                for(int i = 0; i<9; i++){
                    destination = getVertexByCoord(i, 8); // bottom wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex,Edge>(board,source,destination);
                    if(Dijkstra.getPath() != null) // .getPath() returns null if there is no path
                        blocked = false;
                }
            
            case 2:
                for(int i = 0; i<9; i++){
                    destination = getVertexByCoord(i, 0);  // top wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex,Edge>(board,source,destination);
                    if(Dijkstra.getPath() != null)
                        blocked = false;
                }    
            
            case 3:
                for(int i = 0; i<9; i++){
                    destination = getVertexByCoord(8, i); // right wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex,Edge>(board,source,destination);
                    if(Dijkstra.getPath() != null)
                        blocked = false;
                }    
            
            case 4:
                for(int i = 0; i<9; i++){
                    destination = getVertexByCoord(0, i); // left wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex,Edge>(board,source,destination);
                    if(Dijkstra.getPath() != null)
                        blocked = false;
                }
        }
        
        // putting the removed edges back where they belong
        for(Edge e : edgeSet)
            boardEdgeSet.add(e);
        
        // returning verdict of whether or not path is blocked
        return blocked;
    }
       
    
    // method takes in wall move and gets the set of edges to be removed
    // uses wall move protocol
    public Set<Edge> getEdgesToRemove(String wall){
        Scanner sc = new Scanner(wall);
        int pc = Integer.parseInt(sc.next());
        int pr = Integer.parseInt(sc.next());
        String d  = sc.next().toUpperCase();
        // Source node
        Vertex s  = getVertexByCoord(pc, pr);
        // node to right of source node
        Vertex r  = getVertexByCoord(pc+1, pr);
        // node below source node
        Vertex b  = getVertexByCoord(pc, pr+1);
        // node below and to the right of source node
        Vertex br = getVertexByCoord(pc+1, pr+1);
        
        Set <Edge> remove = new HashSet<Edge>();
        
        // adding edges to the set to be removed based on wall placement direction
        if(d.equals("V")){
            // edge from source to right
            remove.add(board.getEdge(s, r));
            // edge from node below source to node below and to the right
            remove.add(board.getEdge(b, br));
        }else{
            // edge from source to node below the source
            remove.add(board.getEdge(s,b));
            // edge from node to the right of source to node below and to the right
            remove.add(board.getEdge(r,br));
        }
        return remove;
    }

        
        
        /* Still necessary: for each player in the game, check if this wall
        * results in blocking that player's path to winning.
        * (Is it okay to block off some of the winning squares, or must all
        * 8 of them be available?)
        * - Lucas, 3/24
        */
    
    // helper method to get set of all players on the board
    public Set<Player> getPlayers(){
        Set<Player> players = new HashSet<Player>();
        Set<Vertex> vertices = board.vertexSet();
        for(Vertex v : vertices)
            if(v.isPlayerHere())
                players.add(v.player);
        return players;
    }
    
}
