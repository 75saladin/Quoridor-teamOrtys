
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
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
public class LogicalBoard {

    // the board as a 9X9 graph
    public UndirectedGraph<Vertex, Edge> board = new SimpleGraph<Vertex, Edge>(Edge.class);
    // the set of players in the game

    public Player[] players;
    // the set of edges in the game

    public int playerCount;

    /**
     * Constructor - builds the logical board with the correct number of players
     *
     * @param playerCount - number of players in this game
     */
    public LogicalBoard(int playerCount) {
        GridGraphGenerator<Vertex, Edge> graphGenerator
                = new GridGraphGenerator<Vertex, Edge>(9, 9);

        VertexFactory<Vertex> vertexFactory
                = new ClassBasedVertexFactory<Vertex>(Vertex.class);

        graphGenerator.generateGraph(board, vertexFactory, null);
        board = buildGraph(board);
        if (playerCount == 2) {
            players = new Player[2];
        } else {
            players = new Player[4];
        }

        if (playerCount >= 2) {
            Player one = new Player(1);
            players[0] = one;
            addPlayer(one);
            Player two = new Player(2);
            players[1] = two;
            addPlayer(two);
            if (playerCount == 4) {
                Player three = new Player(3);
                players[2] = three;
                addPlayer(new Player(3));
                Player four = new Player(4);
                players[3] = four;
                addPlayer(four);
            }
        }
        setWalls(playerCount);
        this.playerCount = playerCount;
    }

    public Set<Vertex> vertexSet() {
        return board.vertexSet();
    }

    public Set<Edge> edgeSet() {
        return board.edgeSet();
    }

    /**
     *
     * @param playerNum - player in game to be returned
     * @return Player with that playerNum
     */
    public Player getPlayer(int playerNum) {
        return players[playerNum - 1];
    }
    
    /**
     *
     * @param v - vertex in question
     * @return Player on that vertex
     */
    public Player getPlayer(Vertex v) {
        for (Player p : players)
            if (p.getC()==v.c && p.getR()==v.r)
                return p;
        return null;
        
    }

    public int getPlayerNum(Player p) {
        for (int i = 0; i < 4; i++) {
            if (players[i].equals(p)) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     *
     * @return
     */
    public int getPlayerCount() {
        int temp = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                temp++;
            }
        }
        return temp;
    }

    /**
     * addPlayer - adds player to the board in corresponding player position -
     * adds player to players set
     *
     * @param player
     */
    private void addPlayer(Player player) {
        getVertexByCoord(player.getC(), player.getR()).placePlayer();
    }

    // kicks a player from the game
    /**
     *
     * @param playerNum
     */
    public void kick(int playerNum) {
        Player p = getPlayer(playerNum);
        Vertex v = getVertexByCoord(p.getC(), p.getR());
        v.removePlayer();

        players[playerNum - 1] = null;
    }

    // method takes in wall move and gets the set of edges to be removed
    // uses wall move protocol
    /**
     * getVertexByCoord - returns Vertex object at that coordinate on the board
     *
     * @param c - Column of vertex
     * @param r - Row of Vertex
     * @param board - board to get edgset from not just the default board
     * @return - Vertex object at that location on the board
     */
    public Vertex getVertexByCoord(int c, int r, UndirectedGraph<Vertex, Edge> board) {
        for (Vertex v : board.vertexSet()) {
            if (v.c == c && v.r == r) {
                return v;
            }
        }
        return null;
    }

    /**
     * getVertexByCoord - returns Vertex object at that coordinate on the board
     *
     * @param c - Column of vertex
     * @param r - Row of Vertex
     * @return - Vertex object at that location on the board
     */
    public Vertex getVertexByCoord(int c, int r) {
        for (Vertex v : board.vertexSet()) {
            if (v.c == c && v.r == r) {
                return v;
            }
        }
        return null;
    }

    /**
     * checkValid - returns boolean if move is valid or not and updates board
     *
     * @param playerNum - player making the move
     * @param move
     * @return if valid move
     */
    public boolean checkValid(int playerNum, String move) {
        if (move.contains("Error")) {
            return false;
        }
        if (move.length() == 3 || move.length() == 5) {
            if (move.length() == 3) {
                if (validMove(playerNum, move)){
                    makeMove(playerNum,move);
                    return true;
                } else{
                    return false;
                }
            } else if (validWall(playerNum, move)){
                placeWall(playerNum,move);
                return true;
            } else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * makeMove - Puts a player in a given destination. This will make the move,
     * always; the given player and move combo should be passed into validMove()
     * just before makeMove().
     *
     * @param player The player to move
     * @param move The destination to move the player to
     */
    public void makeMove(int playerNum, String move) {
        Player player = getPlayer(playerNum);
        Scanner sc = new Scanner(move);
        int c = Integer.parseInt(sc.next());
        int r = Integer.parseInt(sc.next());

        //source 
        getVertexByCoord(player.getC(), player.getR()).removePlayer();
        //destination
        getVertexByCoord(c, r).placePlayer();

        player.setC(c);
        player.setR(r);
    }

    /**
     *
     * @param playerNum - player that is trying to move
     * @param move - move the player is making
     * @return whether or not it is a valid move
     */
    public boolean validMove(int playerNum, String move) {
        Scanner sc = new Scanner(move);
        int c = Integer.parseInt(sc.next());
        int r = Integer.parseInt(sc.next());
        Vertex Destination = getVertexByCoord(c, r);
        Set<Vertex> validMoves = getValidMoves(playerNum);
        for(Vertex v : validMoves){
            if(v.equals(Destination)){
                return true;
            }
        }
        return false;
    }

    public boolean validJump(Vertex Source, Vertex Destination) {
        DijkstraShortestPath<Vertex,Edge> dijkstra = new DijkstraShortestPath<>(board,Source,Destination);
        boolean isValidJump = false;
        List<Edge> edgeList = dijkstra.getPathEdgeList();
        Set<Vertex> vertexOnPath = new HashSet<>();
        for (Edge e : edgeList) {
            vertexOnPath.add(e.getTarget());
            vertexOnPath.add(e.getSource());
        }

        for (Vertex v : vertexOnPath) {
            if (v.isHere() && !v.equals(Destination) || !v.isHere() && v.equals(Destination)) {
                isValidJump = true;
            }
            if (v.isHere() && v.equals(Destination) || !v.isHere() && !v.equals(Destination)) {
                return false;
            }

        }
        return isValidJump;
    }

    /**
     * Places a wall. This wall must be validated by validWall() BEFORE being
     * placed.
     *
     * @param player - player that is placing the wall. 0 can be used for
     * testing; in that case, no player's wall count will be decremented.
     * @param wall The previously validated wall to be placed, as a string in
     * the protocol form.
     */
    public void placeWall(int playerNum, String wall) {
        Player player = getPlayer(playerNum);
        
        Set<Edge> edgesToRemove = getEdgesToRemove(wall);
        for (Edge e : edgesToRemove)
            board.removeEdge(e);
        
        if (player != null)
            player.decrementWall();
    }

    /**
     *
     * @param playerNum - player placing wall. 0 can be passed for testing; it
     * will succeed but not decrement any player's wall count.
     * @param wall - wall position ( "C R Direction" )
     * @return
     */
    public boolean validWall(int playerNum, String wall) {
        // cannot leave if one thing is true, must check all
        // but if one thing is false we return
        Player player = getPlayer(playerNum);

        Scanner sc = new Scanner(wall);
        int sourceC = Integer.parseInt(sc.next());
        int sourceR = Integer.parseInt(sc.next());
        String direction = sc.next().toUpperCase();
        
        
        // wall must be on board and not in the 8th row or col
        if (sourceC > 7 || sourceR > 7 || sourceC < 0 || sourceR < 0)
            return false;
        
        // if the player has no walls left, it's an invalid wall
        if (player != null && player.getWalls() <= 0)
            return false;

        // Fewer than 2 edges to remove means there's a conflicting wall
        Set<Edge> EdgeSetToRemove = getEdgesToRemove(wall);
        if (EdgeSetToRemove == null || EdgeSetToRemove.size() < 2)
            return false;
        
        // if 0 edges to remove for opposite wall, this wall is a crisscross
        if (direction.equals("V") && 
                getEdgesToRemove(sourceC + " " + sourceR + " H").size() == 0)
            return false;            
        else if (direction.equals("H") && 
                getEdgesToRemove(sourceC + " " + sourceR + " v").size() == 0)
            return false;        
        
        // Starting logic to test if winners path is blocked......
        for (Player p : players) {
            // vertex that contains this Player p
            // if path is blocked return false
            if (pathBlocked(getPlayerNum(p), EdgeSetToRemove))
                return false;
        }
        
        // if we get here it's a valid wall
        return true;
    }

    /**
     *
     * pathBlocked - Uses Dijkstras algorithm to make sure that the path to the
     * Winner row is not blocked by a player placed wall
     *
     * @param source
     * @param playerNum
     * @param edgeSet
     * @return
     */
    public boolean pathBlocked(int playerNum, Set<Edge> edgeSet) {
        Player p = getPlayer(playerNum);
        boolean blocked = false;
        DijkstraShortestPath<Vertex, Edge> Dijkstra;
        Vertex destination;
        Vertex source = getVertexByCoord(p.getC(), p.getR());

        UndirectedGraph<Vertex, Edge> boardCopy = new SimpleGraph<Vertex, Edge>(Edge.class);
        Graphs.addGraph(boardCopy, this.board);
        // builds the graph with correct C R Vertex Positions
        boardCopy = buildGraph(boardCopy);
        // puts each player on the board in the correct position
        for (Player temp : players) {
            getVertexByCoord(temp.getC(), temp.getR(), boardCopy).placePlayer();
        }
        // Removing Edges to place wall temporarily to check if winning path is 
        //  blocked will replace at the end before returning

        // Remove edges from board for test to see if path is blocked
        for (Edge e : edgeSet) {
            boardCopy.removeEdge(e);
        }

        switch (playerNum) {

            case 1:
                for (int i = 0; i < 9; i++) {
                    destination = getVertexByCoord(i, 8, boardCopy); // bottom wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex, Edge>(boardCopy, source, destination);
                    if (Dijkstra.getPath() == null) // .getPath() returns null if there is no path
                    {
                        blocked = true;
                    }
                }
                break;

            case 2:
                for (int i = 0; i < 9; i++) {
                    destination = getVertexByCoord(i, 0, boardCopy);  // top wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex, Edge>(boardCopy, source, destination);
                    if (Dijkstra.getPath() == null) {
                        blocked = true;
                    }
                }
                break;

            case 3:
                for (int i = 0; i < 9; i++) {
                    destination = getVertexByCoord(8, i, boardCopy); // right wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex, Edge>(boardCopy, source, destination);
                    if (Dijkstra.getPath() == null) {
                        blocked = true;
                    }
                }
                break;

            case 4:
                for (int i = 0; i < 9; i++) {
                    destination = getVertexByCoord(0, i, boardCopy); // left wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex, Edge>(boardCopy, source, destination);
                    if (Dijkstra.getPath() == null) {
                        blocked = true;
                    }
                }
                break;
        }

        // returning verdict of whether or not path is blocked
        return blocked;
    }

    /**
     * getEdgesToRemove - gets the edges to remove when placing a wall
     *
     * @param wall
     * @return
     */
    public Set<Edge> getEdgesToRemove(String wall) {
        Scanner sc = new Scanner(wall);
        int pc = Integer.parseInt(sc.next());
        int pr = Integer.parseInt(sc.next());
        String d = sc.next().toUpperCase();
        // Source node
        Vertex s = getVertexByCoord(pc, pr);
        // node to right of source node
        Vertex r = getVertexByCoord(pc + 1, pr);
        // node below source node
        Vertex b = getVertexByCoord(pc, pr + 1);
        // node below and to the right of source node
        Vertex br = getVertexByCoord(pc + 1, pr + 1);

        Edge s2r = board.getEdge(s, r);
        Edge b2br = board.getEdge(b, br);
        Edge s2b = board.getEdge(s, b);
        Edge r2br = board.getEdge(r, br);

        Set<Edge> remove = new HashSet<Edge>();

        // adding edges to the set to be removed based on wall placement direction
        if (d.equals("V")) {
            if (s2r != null) {
                remove.add(board.getEdge(s, r));
            }
            if (b2br != null) {
                remove.add(board.getEdge(b, br));
            }
        } else {
            if (s2b != null) {
                remove.add(board.getEdge(s, b));
            }
            if (r2br != null) {
                remove.add(board.getEdge(r, br));
            }
        }
        return remove;
    }

    /**
     * Sets wall counts for all players depending on player count
     *
     * @param playerCount
     */
    private void setWalls(int playerCount) {
        if (playerCount == 2) {
            players[0].setWalls(10);
            players[1].setWalls(10);
        } else {
            players[0].setWalls(5);
            players[1].setWalls(5);
            players[2].setWalls(5);
            players[3].setWalls(5);
        }
    }

    /**
     * FOR TESTING PURPOSES ONLY. Removes a wall. To be called just after
     * placing a wall.
     *
     * @param wall
     */
    public void removeWall(int playerNum, String wall) {
        Player p = getPlayer(playerNum);
        Scanner sc = new Scanner(wall);
        int cB = Integer.parseInt(sc.next()); // Column of beginning Vertex
        int rB = Integer.parseInt(sc.next()); // Row of beginning Vertex
        String direction = sc.next();

        // getting positions of the vertexes for this wall
        Vertex sourceV = getVertexByCoord(cB, rB);
        Vertex belowV = getVertexByCoord(cB, rB + 1);
        Vertex rightV = getVertexByCoord(cB + 1, rB);
        Vertex belowRV = getVertexByCoord(cB + 1, rB + 1);

        if (direction.toUpperCase().equals("V")) {
            board.addEdge(sourceV, rightV);
            board.addEdge(belowV, belowRV);
        } else {
            board.addEdge(sourceV, belowV);
            board.addEdge(rightV, belowRV);
        }
        p.incrementWall();
    }

    /**
     * hasWon - determines if player has won the game
     *
     * @param playerNum - player to be determined winner
     * @return - true or false if player has won
     */
    public boolean hasWon(int playerNum) {
        Player p = players[playerNum - 1];

        // if he is not in the game he did not win
        if (p == null) {
            return false;
        }
        // player 1 must reach Row 8
        if (playerNum == 1 && p.getR() == 8) {
            return true;
        }
        // player 2 must reach Row 0
        if (playerNum == 2 && p.getR() == 0) {
            return true;
        }
        // player 3 must reach Column 8
        if (playerNum == 3 && p.getC() == 8) {
            return true;
        }
        // player 4 must reach Column 0
        if (playerNum == 4 && p.getC() == 0) {
            return true;
        }

        // if he hasnt made it to the end but is the last man standing he wins
        if (!enoughPlayers()) {
            return true;
        }
        return false;
    }

    /**
     *
     * @returns true if there are more than one players
     */
    public boolean enoughPlayers() {
        if (getPlayerCount() > 1) {
            return true;
        }
        return false;
    }

    public UndirectedGraph<Vertex, Edge> buildGraph(UndirectedGraph<Vertex, Edge> graph) {

        // The graph is now constructed with no C,R Data, must fill
        int c = 0;
        int r = 0;
        // Iterate through each Vertex and put in its actual position
        // on the logical board
        for (Vertex v : graph.vertexSet()) {
            v.setC(c);
            v.setR(r);
            c++;
            if (c == 9) {   // when you reach end of the row go to next
                c = 0;
                r++;
            }
        }
        return graph;
    }

    /**
     * getShortestWinningPath - gets the shortest path for that player to win
     *
     * @param playerNum - players shortest Winning path to get
     * @return
     */
    public DijkstraShortestPath<Vertex, Edge> getShortestWinningPath(int playerNum) {
        Vertex source = getVertexByCoord(getPlayer(playerNum).getC(), getPlayer(playerNum).getR());
        Vertex destination = null;
        DijkstraShortestPath<Vertex, Edge> temp = null;
        DijkstraShortestPath<Vertex, Edge> Dijkstra = null;
        int length = 89;
        if (playerNum == 1) {
            for (int i = 0; i < 9; i++) {
                destination = getVertexByCoord(i, 8);
            }
            temp = new DijkstraShortestPath<Vertex, Edge>(this.board, source, destination);
            if (temp != null) {
                if (temp.getPathLength() < length) {
                    Dijkstra = temp;
                }
            }

        } else if (playerNum == 2) {
            for (int i = 0; i < 9; i++) {
                destination = getVertexByCoord(i, 0);
            }
            temp = new DijkstraShortestPath<Vertex, Edge>(this.board, source, destination);
            if (temp != null) {
                if (temp.getPathLength() < length) {
                    Dijkstra = temp;
                }
            }
        } else if (playerNum == 3) {
            for (int i = 0; i < 9; i++) {
                destination = getVertexByCoord(8, i);
            }
            temp = new DijkstraShortestPath<Vertex, Edge>(this.board, source, destination);
            if (temp != null) {
                if (temp.getPathLength() < length) {
                    Dijkstra = temp;
                }
            }
        } else {
            for (int i = 0; i < 9; i++) {
                destination = getVertexByCoord(0, i);
            }
            temp = new DijkstraShortestPath<Vertex, Edge>(this.board, source, destination);
            if (temp != null) {
                if (temp.getPathLength() < length) {
                    Dijkstra = temp;
                }
            }
        }

        return Dijkstra;
    }

    public Set<Vertex> getValidMoves(int playerNum) {
        Player p = getPlayer(playerNum);
        return validMovesOf(p, new HashSet<Player>());
        
        /*Player p = getPlayer(playerNum);
        int c = p.getC();
        int r = p.getR();
        Vertex Source = getVertexByCoord(c, r);
        return getValidMoves(Source, p);*/
    }
    
    private Set<Vertex> validMovesOf(Player p, Set<Player> ignoreJump) {
        Set<Vertex> valid = new HashSet<Vertex>();
        Set<Vertex> surrounding = new HashSet<Vertex>();
        Vertex cur = getVertexByCoord(p.getC(), p.getR());
        
        // Can't add directly to surrounding b/c vertex may be null
        Set<Vertex> temp = new HashSet<Vertex>();
        temp.add(getVertexByCoord(cur.c+1, cur.r)); //right
        temp.add(getVertexByCoord(cur.c-1, cur.r)); //left
        temp.add(getVertexByCoord(cur.c, cur.r+1)); //below
        temp.add(getVertexByCoord(cur.c, cur.r-1)); //above
        
        for (Vertex v : temp)
            if (v != null)
                surrounding.add(v);
        
        //recursive calls must ignore all previous players
        ignoreJump.add(p);
        
        Player vp;
        for (Vertex v : surrounding) {
            if (hasEdge(cur, v)) {  //if there's not a blocking wall
                vp = getPlayer(v);
                if (vp==null)    //no player there means it's valid
                    valid.add(v);
                else if (!ignoreJump.contains(vp))
                    //ignore list not containing vp means that player's valid moves are valid
                    valid.addAll(validMovesOf(vp, ignoreJump));
            }
        }
        
        return valid;
    }

    /**
     * getValidMoves - second attempt
     *
     * @param v position of player
     * @return set of vertices that are valid
     */
    private Set<Vertex> getValidMoves(Vertex v, Player p) {
        int c = v.c;
        int r = v.r;
        Set<Vertex> tempVertexSet;
        Set<Vertex> validVertices = new HashSet<>();
        // this is for jumping over a wall
        // originally i was thinking of using the players source position to 
        // check to see if there was an edge but that doesnt make sense because
        // we could be a couple of iterations away from the player asking for 
        // valid moves.  So then i tried to do it with the parameter v.  
        // sadly that just never ends and i do not know why
        
        // to see what im talking about run - runlogicalboard.java
        // then uncomment the if and comment the one above it out in all four cases
        //
        Vertex Source = getVertexByCoord(p.getC(),p.getR());
        // vertices to check for validity
        Vertex right = getVertexByCoord(c + 1, r);
        Vertex left = getVertexByCoord(c - 1, r);
        Vertex below = getVertexByCoord(c, r + 1);
        Vertex above = getVertexByCoord(c, r - 1);

        if (above != null) {
            System.out.println(above);
            if(board.containsEdge(Source,above)){
            //if(hasEdge(above,v)){
                if (above.isHere()) {
                    tempVertexSet = getValidMoves(above, p);
                    if (tempVertexSet != null) {
                        for (Vertex temp : tempVertexSet) {
                                if (validMove(p, temp.c + " " + temp.r)) 
                                    validVertices.add(temp);
                        }
                    }
                } else if (validMove(p, above.c + " " + above.r)) {
                    validVertices.add(above);
                }
            }
        }
        if (below != null) {
            System.out.println(below);
            if(board.containsEdge(Source,below)){
            //if(hasEdge(below,v)){
                if (below.isHere()) {
                    tempVertexSet = getValidMoves(below, p);
                    if (tempVertexSet != null) {
                        for (Vertex temp : tempVertexSet) {
                                if (validMove(p, temp.c + " " + temp.r)) {
                                    validVertices.add(temp);
                                }
                        }
                    }
                } else if (validMove(p, below.c + " " + below.r)) {
                    validVertices.add(below);
                }
            }
        }
        if (right != null) {
            System.out.println(right);
            if(board.containsEdge(Source,right)){
            //if(hasEdge(right,v)){
                if (right.isHere()) {
                    tempVertexSet = getValidMoves(right, p);
                    if (tempVertexSet != null) {
                        for (Vertex temp : tempVertexSet) {
                            if (validMove(p, temp.c + " " + temp.r)) {
                                    validVertices.add(temp);
                                }
                        }
                    }
                } else if (validMove(p, right.c + " " + right.r)) {
                    validVertices.add(right);
                }
            }
        }
        if (left != null) {
            System.out.println(left);
            if(board.containsEdge(Source,left)){
            //if(hasEdge(left,v)){
                if (left.isHere()) {
                    tempVertexSet = getValidMoves(left, p);
                    if (tempVertexSet != null) {
                        for (Vertex temp : tempVertexSet) {
                            if (validMove(p, temp.c + " " + temp.r)) {
                                validVertices.add(temp);
                            }
                        }
                    }
                } else if (validMove(p, left.c + " " + left.r)) {
                    validVertices.add(left);
                }
            }
        }
        return validVertices;
    }
    /**
     * valid move - checks if player can move to this location
     * 
     * @param p - Player that is making a move
     * @param move - move that p is making
     * @return 
     */
    private boolean validMove(Player p, String move) {
        int r = p.getR();
        int c = p.getC();
        Vertex source = getVertexByCoord(c, r);
        Scanner sc = new Scanner(move);
        c = sc.nextInt();
        r = sc.nextInt();
        Vertex destination = getVertexByCoord(c,r);
        if(destination!=null){
            if(!destination.equals(source)){
                if(destination.isHere())
                    return false;
                else
                    return true;
            }
        }
        return false;

    }
    private boolean hasEdge(Vertex destination,Vertex Source){
        if (board.containsEdge(Source, destination))
            return true;
        return false;
    }
}
