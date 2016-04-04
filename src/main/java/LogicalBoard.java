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
    
    // the board as a 9X9 graph

    /**
     *
     */
    public UndirectedGraph<Vertex, Edge> board;
    // the set of players in the game

    /**
     *
     */
    public Set<Player> players = new HashSet<>();
    // the set of edges in the game

    /**
     *
     */
    public Set<Edge> edges;
    // the number of players in the game

    /**
     *
     */
    public int playerCount;

    /** 
     * Constructor - builds the logical board with the correct number of players 
     *
     * @param playerCount - number of players in this game
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
            if(playerCount>=2){
                this.addPlayer(new Player(1));
                this.addPlayer(new Player(2));
            }if(playerCount==4){
                this.addPlayer(new Player(3));
                this.addPlayer(new Player(4));
            }
            setWalls(playerCount);
    }
    
    /**
     *  checkValid - returns boolean if move is valid or not and updates board
     * 
     * @param playerNum - player making the move
     * @param move 
     * @return if valid move
     */
    public boolean checkValid(int playerNum,String move){
        if(move.length()>3)
            return validWall(playerNum, move);
        else 
            return validMove(playerNum, move);
    }
    
    /**
     *
     * @param playerNum - player in game to be returned
     * @return Player with that playerNum
     */
    public Player getPlayer(int playerNum) {
	for (Player p : players) {
	    if (p.getPlayerNum()==playerNum)
		return p;
	}
	return null;
    }
    
    /**
     *
     * @return
     */
    public int getPlayerCount(){
        return players.size();
    }
    
     /**
     * 
     * @return the set of vertices in board
     */
    public Set<Vertex> getVertexSet(){
        return board.vertexSet();
    }
    
    /**
     *
     * @return the set of edges in board
     */
    public Set<Edge> getEdgeSet(){
        return board.edgeSet();
    }
    
        // helper method to get set of all players on the board

    /**
     *
     * @return
     */
    public Set<Player> getPlayers(){
        Set<Player> players = new HashSet<Player>();
        Set<Vertex> vertices = board.vertexSet();
        for(Vertex v : vertices)
            if(v.isPlayerHere())
                players.add(v.player);
        return players;
    }
    
    /**
     *  addPlayer - adds player to the board in corresponding player position
     *            - adds player to players set
     * 
     * @param player
     */
    public void addPlayer(Player player){
        Vertex destination = getVertexByCoord(player.getC(), player.getR());
        destination.placePlayer(player);
        players.add(player);
    }
    
    
    // kicks a player from the game

    /**
     *
     * @param playerNum
     */
    public void kick(int playerNum){
        Vertex v = null;
        Player p = null;
        for(Player temp : players)
            if(temp.getPlayerNum()==playerNum){
               p = temp; 
            }
        v = getVertexByCoord(p.getC(), p.getR());
        v.removePlayer();
        players.remove(p);
    }

    // method takes in wall move and gets the set of edges to be removed
    // uses wall move protocol

    /**
     *  getEdgesToRemove - gets the edges to remove when placing a wall
     * 
     * @param wall
     * @return
     */
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

    
    /**
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
    
    /**
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
     * makeMove - Puts a player in a given destination. This will make the move, always;
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
     * @param player - player that is placing the wall
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
	
	if (direction.toUpperCase().equals("V")) {
	    board.removeEdge(sourceV, rightV);
	    board.removeEdge(belowV, belowRV);
            player.decrementWall();
	} else {
	    board.removeEdge(sourceV, belowV);
	    board.removeEdge(rightV, belowRV);
            player.decrementWall();
	}
    }      

    /**
     *
     * @param playerNum - player that is trying to move
     * @param move - move the player is making
     * @return whether or not it is a valid move
     */
    public boolean validMove(int playerNum, String move){
        Player player = null;
        for(Player p : players)
          if(p.getPlayerNum() == playerNum)
              player=p;

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

    /**
     *
     * @param playerNum - player placing wall 
     * @param wall - wall position ( "C R Direction" )
     * @return
     */
    public boolean validWall(int playerNum, String wall){
        // cannot leave if one thing is true, must check all
        // but if one thing is false we return
        Player player = null;
        for(Player p : players)
          if(p.getPlayerNum() == 
	      playerNum)
              player=p;
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
                if( !board.containsEdge(sourceV, rightV) ||
                        !board.containsEdge(belowV, belowRV) ||
                        (!board.containsEdge(sourceV, belowV) &&
                        !board.containsEdge(rightV, belowRV))
			)
                    return false;
            else if(direction.equals("H"))
                if( !board.containsEdge(sourceV, belowV) ||
                        !board.containsEdge(rightV, belowRV) ||
                        (!board.containsEdge(sourceV, rightV) &&
                        !board.containsEdge(belowV, belowRV))
			)
                    return false;
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
        // if we get here return true
        placeWall(player, wall);
        return true;
    }
    
    /**
     *
     *  pathBlocked - Uses Dijkstras algorithm to make sure that the path to the 
     *               Winner row is not blocked by a player placed wall
     * 
     * @param source
     * @param playerNum
     * @param edgeSet
     * @return
     */
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
       
    /**
     * Sets wall counts for all players depending on player count
     *
     * @param playerCount
     */
    public void setWalls(int playerCount){
        if(playerCount==2)
            for(Player p : players)
                p.setWalls(10);
        else
            for(Player p : players)
                p.setWalls(5);
    }
    
    //FOR TESTING PURPOSES ONLY. Removes a wall. To be called just after placing a wall.

    /**
     *
     * @param wall
     */
    public void removeWall(String wall) {
	Scanner sc = new Scanner(wall);
        int cB = Integer.parseInt(sc.next()); // Column of beginning Vertex
        int rB = Integer.parseInt(sc.next()); // Row of beginning Vertex
        String direction = sc.next();
	
	// getting positions of the vertexes for this wall
        Vertex sourceV = getVertexByCoord(cB, rB);
        Vertex belowV = getVertexByCoord(cB, rB+1);
        Vertex rightV = getVertexByCoord(cB+1, rB);
        Vertex belowRV = getVertexByCoord(cB+1, rB+1);
	
	if (direction.toUpperCase().equals("V")) {
	    board.addEdge(sourceV, rightV);
	    board.addEdge(belowV, belowRV);
	} else {
	    board.addEdge(sourceV, belowV);
	    board.addEdge(rightV, belowRV);
	}
    }
 
    /**
    *   hasWon - determines if player has won the game
    *
    *   @param playerNum - player to be determined winner
    *   @return - true or false if player has won
    */
    public boolean hasWon(int playerNum){
        Player p = null;
        for(Player player : players)
            if(player.getPlayerNum()==playerNum)
                p = player;
        // if he is not in the game he did not win
        if(p == null)
            return false;
        // player 1 must reach Row 8
        if(playerNum==1 && p.getR()==8){
            return true;
        }
        // player 2 must reach Row 0
        if(playerNum==2 && p.getR()==0){
            return true;
        }
        // player 3 must reach Column 8
        if(playerNum==3 && p.getC()==8){
            return true;
        }
        // player 4 must reach Column 0
        if(playerNum==4 && p.getC()==0){
            return true;
        }
        
        // if he hasnt made it to the end but is the last man standing he wins
        if(!enoughPlayers())
            return true;
        return false;
    }
    

    /**
     *
     * @returns true if there are more than one players
     */
    public boolean enoughPlayers(){
        if(players.size()>1)
            return true;
        return false;
    }
    
}
