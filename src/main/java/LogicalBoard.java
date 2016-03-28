import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;

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
    public Set<Player> players = new HashSet<>();
    public Set<Edge> edges;
    public int playerCount;

    /**
     * 
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
            this.edges = getEdgeSet();
            if(playerCount==2 || playerCount == 4){
                this.addPlayer(new Player(1));
                this.addPlayer(new Player(2));
            }else if(playerCount==4){
                this.addPlayer(new Player(3));
                this.addPlayer(new Player(4));
            }
            setWalls(playerCount);
    }
    
    
     /**
     *
     * @return set of vertices in board
     */
    public Set<Vertex> getVertexSet(){
        return board.vertexSet();
    }
    
    /**
     *
     * @return set of edges in board
     */
    public Set<Edge> getEdgeSet(){
        return board.edgeSet();
    }
    
        // helper method to get set of all players on the board
    public Set<Player> getPlayers(){
        Set<Player> players = new HashSet<Player>();
        Set<Vertex> vertices = board.vertexSet();
        for(Vertex v : vertices)
            if(v.isPlayerHere())
                players.add(v.player);
        return players;
    }
    
    public void addPlayer(Player player){
        Vertex destination = getVertexByCoord(player.getC(), player.getR());
        destination.placePlayer(player);
        players.add(player);
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

    
    /*
     * getVertexByCoord - returns Vertex object at that coordinate on the board
     * 
     * @param c - Column of vertex
     * @param r - Row of Vertex
     * @param board - board to get edgset from not just the default board
     * @return - Vertex object at that location on the board
     */
    public Vertex getVertexByCoord(int c,int r, UndirectedGraph<Vertex,Edge> board){
        for(Vertex v : board.vertexSet())
            if(v.c == c && v.r == r)
                return v;
        return null;
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

    
    /**
     * Puts a player in a given destination. This will make the move, always;
     * the given player and move combo should be passed into validMove() just 
     * before makeMove().
     * 
     * @param player The player to move
     * @param move The destination to move the player to
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
    public void placeWall(Player player, String wall){
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
            player.decrementWall();
	} else {
	    board.removeEdge(sourceV, belowV);
	    board.removeEdge(rightV, belowRV);
            player.decrementWall();
	}
    }      

    public boolean validMove(Player player, String move){
        Scanner sc = new Scanner(move);
        int c = Integer.parseInt(sc.next());
        int r = Integer.parseInt(sc.next());
        Vertex Destination = getVertexByCoord(c, r);  // Destination of move
        Vertex Source = getVertexByCoord(player.getC(), player.getR()); // location of player
        DijkstraShortestPath<Vertex,Edge> dijkstra = new DijkstraShortestPath<>(board,Source,Destination);
        GraphPath path = dijkstra.getPath();  // path of this move
        List<Edge> edgeList = path.getEdgeList(); 
        Set<Vertex> vertexOnPath = new HashSet<>();
        
        //if the destination is outside the board there will be no vertex
        if(Destination != null)
            // if the destination is not the same as the current location
            if(!Destination.equals(Source))
                // if there is not a player in this location
                if(!Destination.isPlayerHere())
                    if(board.containsEdge(Source, Destination)){
                        if(edgeList.size()>1){
                            for(Edge e : edgeList){
                                vertexOnPath.add(e.getTarget());
                                vertexOnPath.add(e.getSource());
                            }
                            for(Vertex v : vertexOnPath)
                                if(v.isPlayerHere() && !v.equals(Destination)){
                                    if(!v.isPlayerHere() && v.equals(Destination))
                                        makeMove(player, move);
                                        return true;
                                }
                        }else{
                            makeMove(player, move);
                            return true;
                        }
                    }
        // if there is no edge or Destination or there is a player
        // on the destination or the source and destination are the same
        // the player cannot be a move here and needs to be kicked from the game
        
        return false;
    }

    public boolean validWall(Player player, String wall){
        // cannot leave if one thing is true, must check all
        // but if one thing is false we return
        boolean valid = false;
        Scanner sc = new Scanner(wall);
        int sourceC = Integer.parseInt(sc.next());
        int sourceR = Integer.parseInt(sc.next());
        String direction = sc.next().toUpperCase();
        
        if(player.getWalls() <= 0){
            return false;
        }
        
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
        Vertex source;
        // need this edgeset to test if path is blocked
        Set<Edge> EdgeSetToRemove = getEdgesToRemove(wall);
        for(Player p : players){
            // vertex that contains this Player p
            source = getVertexByCoord(p.getC(), p.getR()); 
            // if path is blocked return false
            if(pathBlocked(source,p.getPlayerNum(),EdgeSetToRemove)){
                return false;
            }
        }
        // if we get here return valid which should be true.....
        placeWall(player, wall);
        return valid;
    }
    
    // pathBlocked - Uses Dijkstras algorithm to make sure that the path to the 
    //               Winner row is not blocked by a player placed wall
    public boolean pathBlocked(Vertex source,int playerNum,Set<Edge> edgeSet){
        boolean blocked = true;
        DijkstraShortestPath<Vertex,Edge> Dijkstra;
        Vertex destination;
        Set<Edge> boardEdgeSet = getEdgeSet();
        
        UndirectedGraph<Vertex,Edge> boardCopy = new SimpleGraph<Vertex,Edge>(Edge.class);
        Graphs.addGraph(boardCopy, this.board);
        // Removing Edges to place wall temporarily to check if winning path is 
        //  blocked will replace at the end before returning
        
        for(Edge e : boardEdgeSet) {
                boardCopy.removeEdge(e);
        }

        switch(playerNum){
        
            case 1:
                for(int i = 0; i<9; i++){
                    destination = getVertexByCoord(i, 8, boardCopy); // bottom wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex,Edge>(boardCopy,source,destination);
                    if(Dijkstra.getPath() != null) // .getPath() returns null if there is no path
                        blocked = false;
                }
            
            case 2:
                for(int i = 0; i<9; i++){
                    destination = getVertexByCoord(i, 0,boardCopy);  // top wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex,Edge>(boardCopy,source,destination);
                    if(Dijkstra.getPath() != null)
                        blocked = false;
                }    
            
            case 3:
                for(int i = 0; i<9; i++){
                    destination = getVertexByCoord(8, i,boardCopy); // right wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex,Edge>(boardCopy,source,destination);
                    if(Dijkstra.getPath() != null)
                        blocked = false;
                }    
            
            case 4:
                for(int i = 0; i<9; i++){
                    destination = getVertexByCoord(0, i,boardCopy); // left wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex,Edge>(boardCopy,source,destination);
                    if(Dijkstra.getPath() != null)
                        blocked = false;
                }
        }

        // returning verdict of whether or not path is blocked
        return blocked;
    }
       
    





    
    public void setWalls(int playerCount){
        if(playerCount==2)
            for(Player p : players)
                p.setWalls(10);
        else
            for(Player p : players)
                p.setWalls(5);
    }
    
}
