import java.util.HashSet;
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
 * An object representing a board state. Contains a jgrapht.UndirectedGraph 
 * with board squares represented by vertices and valid (ie, not blocked by a 
 * wall) moves between them. Also contains the set of players in the game.
 * 
 */
public class LogicalBoard {

    /** The board represented as a graph. Vertices are board squares and edges 
     *  connect vertices that have no wall between them. */
    public UndirectedGraph<Vertex, Edge> board = new SimpleGraph<Vertex, Edge>(Edge.class);
    
    /** The set of players in the game, indexed by playerNumber-1 */
    public Player[] players;

    /**
     * Constructor
     *
     * @param playerCount Number of players in this game
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
    }

    //API methods; to be called by whoever instantiates a board
    /**
     * hasWon Checks whether or not the given player has won the game
     *
     * @param playerNum Player # of the player to check
     * @return Whether or not the player has won
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
        if (enoughPlayers()) 
            return false;
        else
            return true;
    }

    /**
     * Removes from the board the player with the given player number
     *
     * @param playerNum Player to kick
     */
    public void kick(int playerNum) {
        Player p = getPlayer(playerNum);
        Vertex v = getVertexByCoord(p.getC(), p.getR());
        v.removePlayer();

        players[playerNum - 1] = null;
    }

    /**
     * Gets the shortest win path for the given player
     *
     * @param playerNum Player to check
     * @return DijkstraShortestPath of player's shortest winning path
     */
    public DijkstraShortestPath<Vertex, Edge> getShortestWinningPath(int playerNum,UndirectedGraph<Vertex,Edge> board) {
        if(getPlayer(playerNum)==null)
            return null;
        Vertex source = getVertexByCoord(getPlayer(playerNum).getC(), getPlayer(playerNum).getR());
        Vertex destination = null;
        DijkstraShortestPath<Vertex, Edge> temp = null;
        DijkstraShortestPath<Vertex, Edge> Dijkstra = null;
        int length = 89;
        if (playerNum == 1) {
            for (int i = 0; i < 9; i++) {
                destination = getVertexByCoord(i, 8);
                temp = new DijkstraShortestPath<Vertex, Edge>(board, source, destination);
                if (temp != null) {
                    if (temp.getPathLength() < length) {
                        Dijkstra = temp;
                        length = (int) temp.getPathLength();
                    }
                }
            }
        } else if (playerNum == 2) {
            for (int i = 0; i < 9; i++) {
                destination = getVertexByCoord(i, 0);
                temp = new DijkstraShortestPath<Vertex, Edge>(board, source, destination);
                if (temp != null) {
                    if (temp.getPathLength() < length) {
                        Dijkstra = temp;
                        length = (int) temp.getPathLength();
                    }
                }
            }
        } else if (playerNum == 3) {
            for (int i = 0; i < 9; i++) {
                destination = getVertexByCoord(8, i);
                temp = new DijkstraShortestPath<Vertex, Edge>(board, source, destination);
                if (temp != null) {
                    if (temp.getPathLength() < length) {
                        Dijkstra = temp;
                        length = (int) temp.getPathLength();
                    }
                }
            }
        } else {
            for (int i = 0; i < 9; i++) {
                destination = getVertexByCoord(0, i);
                temp = new DijkstraShortestPath<Vertex, Edge>(board, source, destination);
                if (temp != null) {
                    if (temp.getPathLength() < length) {
                        Dijkstra = temp;
                        length = (int) temp.getPathLength();
                    }
                }
            }
        }

        return Dijkstra;
    }

    /**
     * Checks whether or not the given player can make the given
     * move. If it's a valid move, returns true and automatically makes the
     * move.
     *
     * @param playerNum Player to check
     * @param move A string representing the move in the parsed format
     * @return Whether or not it's a valid move
     */
    public boolean checkValid(int playerNum, String move) {
        if (move.contains("Error")) {
            return false;
        }
        if (move.length() == 3 || move.length() == 5) {
            if (move.length() == 3) {
                if (validMove(playerNum, move)) {
                    makeMove(playerNum, move);
                    return true;
                } else {
                    return false;
                }
            } else if (validWall(playerNum, move)) {
                placeWall(playerNum, move);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Helper methods; they get called under the hood of the API
    /**
     * Checks whether or not the given player can make the given
     * move.
     *
     * @param playerNum  Player to check
     * @param move A string representing the move: "[c] [r]"
     * @return Whether or not it is a valid move
     */
    public boolean validMove(int playerNum, String move) {
        Scanner sc = new Scanner(move);
        int c = Integer.parseInt(sc.next());
        int r = Integer.parseInt(sc.next());
        Vertex Destination = getVertexByCoord(c, r);
        Set<Vertex> validMoves = getValidMoves(playerNum);
        for (Vertex v : validMoves) {
            if (v.equals(Destination)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Puts a player in a given destination. This will make the move,
     * always; the given player and move combo should be passed into validMove()
     * just before makeMove().
     *
     * @param playerNum The player to move
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
     * Checks whether or not a given player can place a given wall
     *
     * @param playerNum Player placing wall
     * @param wall A string representing the wall: "[c] [r] [d]"
     * @return Whether or not the wall placement is valid
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
        if (sourceC > 7 || sourceR > 7 || sourceC < 0 || sourceR < 0) {
            return false;
        }

        // if the player has no walls left, it's an invalid wall
        if (player != null && player.getWalls() <= 0) {
            return false;
        }

        // Fewer than 2 edges to remove means there's a conflicting wall
        Set<Edge> EdgeSetToRemove = getEdgesToRemove(wall);
        if (EdgeSetToRemove == null || EdgeSetToRemove.size() < 2) {
            return false;
        }

        // if 0 edges to remove for opposite wall, this wall is a crisscross
        //                                                                              \
        //FIXME - cannot place a wall in between to walls when we are supposed to!!!! --\--
        if (direction.equals("V")
                && getEdgesToRemove(sourceC + " " + sourceR + " H").size() == 0) {
            return false;
        } else if (direction.equals("H")
                && getEdgesToRemove(sourceC + " " + sourceR + " v").size() == 0) {
            return false;
        }


        // Starting logic to test if winners path is blocked......
        for (Player p : players) {
            // vertex that contains this Player p
            // if path is blocked return false
            if (pathBlocked(getPlayerNum(p), EdgeSetToRemove)) {
                return false;
            }
        }

        // if we get here it's a valid wall
        return true;
    }

    /**
     * Places a wall. This wall must be validated by validWall() BEFORE being
     * placed.
     *
     * @param playerNum Player that is placing the wall.
     * @param wall A string representing the wall: "[c] [r] [d]"
     */
    public void placeWall(int playerNum, String wall) {
        Player player = getPlayer(playerNum);

        Set<Edge> edgesToRemove = getEdgesToRemove(wall);
        for (Edge e : edgesToRemove) {
            board.removeEdge(e);
        }

        if (player != null) {
            player.decrementWall();
        }
    }

    /**
     * Returns the set of vertices the given player can move to
     *
     * @param playerNum The player to check
     * @return The set of vertices the player can move to this turn
     */
    public Set<Vertex> getValidMoves(int playerNum) {
        
        Player p = getPlayer(playerNum);
        if(p!=null)
            return validMovesOf(p, new HashSet<Player>());
        return null;
    }

    /**
     * Returns the set of vertices a given player can move to.
     *
     * @param p The player to check
     * @param ignoreJump The set of players that p must not jump over. Moves
     * that are accessed by jumping are added through a recursive call, passing
     * in the adjacent player. If the player being jumped didn't ignore the
     * jumping player, there would be an infinite reursive call alternating
     * between the two players. The recursive call must ignore ALL players from
     * parent calls for the case of four players in a square formation: they
     * could check jumps in a circle forever otherwise.
     * @return - set of p's valid destinations
     */
    private Set<Vertex> validMovesOf(Player p, Set<Player> ignoreJump) {
        Set<Vertex> valid = new HashSet<Vertex>();
        Set<Vertex> surrounding = new HashSet<Vertex>();
        Vertex cur = getVertexByCoord(p.getC(), p.getR());

        // Can't add directly to surrounding b/c vertex may be null
        Set<Vertex> temp = new HashSet<Vertex>();
        temp.add(getVertexByCoord(cur.c + 1, cur.r)); //right
        temp.add(getVertexByCoord(cur.c - 1, cur.r)); //left
        temp.add(getVertexByCoord(cur.c, cur.r + 1)); //below
        temp.add(getVertexByCoord(cur.c, cur.r - 1)); //above

        for (Vertex v : temp) {
            if (v != null) {
                surrounding.add(v);
            }
        }

        //recursive calls must ignore all previous players
        ignoreJump.add(p);

        Player vp;
        for (Vertex v : surrounding) {
            if (hasEdge(cur, v)) {  //if there's not a blocking wall
                vp = getPlayer(v);
                if (vp == null) //no player there means it's valid
                {
                    valid.add(v);
                } else if (!ignoreJump.contains(vp)) //ignore list not containing vp means that player's valid moves are valid
                {
                    valid.addAll(validMovesOf(vp, ignoreJump));
                }
            }
        }

        return valid;
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
     *
     * Checks whether or not the given wall blocks all of the
     * given player's remaining win paths.
     *
     * @param playerNum The player to check
     * @param edgeSet The set of edges representing the wall to check
     * @return Whether or not edgeSet blocks playerNum from winning
     */
    public boolean pathBlocked(int playerNum, Set<Edge> edgeSet) {
        Player p = getPlayer(playerNum);
        if(p==null)
            return false;
        int blocked = 0; //number of win spaces blocked
        DijkstraShortestPath<Vertex, Edge> Dijkstra;
        Vertex destination;
        Vertex source = getVertexByCoord(p.getC(), p.getR());

        UndirectedGraph<Vertex, Edge> boardCopy = new SimpleGraph<Vertex, Edge>(Edge.class);
        Graphs.addGraph(boardCopy, this.board);
        // builds the graph with correct C R Vertex Positions
        boardCopy = buildGraph(boardCopy);
        // puts each player on the board in the correct position
        for (Player temp : players) 
        	if(temp !=null)
	            getVertexByCoord(temp.getC(), temp.getR(), boardCopy).placePlayer();
        // Removing Edges to place wall temporarily to check if winning path is 
        //  blocked will replace at the end before returning

        // Remove edges from board for test to see if path is blocked
        for (Edge e : edgeSet) 
            boardCopy.removeEdge(e);

        switch (playerNum) {

            case 1:
                for (int i = 0; i < 9; i++) {
                    destination = getVertexByCoord(i, 8, boardCopy); // bottom wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex, Edge>(boardCopy, source, destination);
                    if (Dijkstra.getPath() == null) // .getPath() returns null if there is no path
                    {
                        blocked++;
                    }
                }
                break;

            case 2:
                for (int i = 0; i < 9; i++) {
                    destination = getVertexByCoord(i, 0, boardCopy);  // top wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex, Edge>(boardCopy, source, destination);
                    if (Dijkstra.getPath() == null) {
                        blocked++;
                    }
                }
                break;

            case 3:
                for (int i = 0; i < 9; i++) {
                    destination = getVertexByCoord(8, i, boardCopy); // right wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex, Edge>(boardCopy, source, destination);
                    if (Dijkstra.getPath() == null) {
                        blocked++;
                    }
                }
                break;

            case 4:
                for (int i = 0; i < 9; i++) {
                    destination = getVertexByCoord(0, i, boardCopy); // left wall of nodes
                    Dijkstra = new DijkstraShortestPath<Vertex, Edge>(boardCopy, source, destination);
                    if (Dijkstra.getPath() == null) {
                        blocked++;
                    }
                }
                break;
        }

        // returning verdict of whether or not path is blocked
        return blocked==9;
    }

    /**
     * Returns whether or not the two vertices have an edge between
     * them. ie, checks if there is not a wall between the two vertices.
     *
     * @param destination One vertex
     * @param Source The other vertex
     * @return If the path is clear
     */
    private boolean hasEdge(Vertex destination, Vertex Source) {
        if (board.containsEdge(Source, destination)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the game has enough players to continue
     *
     * @return Whether or not there are enough players to continue playing
     */
    public boolean enoughPlayers() {
        if (getPlayerCount() > 1) {
            return true;
        }
        return false;
    }

    //Getters and Setters
    /**
     * Gets the set of vertices on the board
     *
     * @return The set of vertices on the board
     */
    public Set<Vertex> vertexSet() {
        return board.vertexSet();
    }

    /**
     * Gets the set of edges on the board
     *
     * @return The set of edges on the board
     */
    public Set<Edge> edgeSet() {
        return board.edgeSet();
    }

    /**
     * Given a player number, gets the associated Player object
     *
     * @param playerNum Player number
     * @return Player object
     */
    public Player getPlayer(int playerNum) {
        if(playerNum<0 || playerNum>players.length)
            return null;
        return players[playerNum - 1];
    }

    /**
     * Given a vertex, returns the player on that vertex. If there
     * is none, returns null.
     *
     * @param v Vertex in question
     * @return Player on that vertex (or null if none)
     */
    public Player getPlayer(Vertex v) {
        
        for (Player p : players)
            if (p!=null && (p.getC() == v.c && p.getR() == v.r)) 
                return p;
        return null;
    }

    /**
     * getPlayerNum - given a player object, returns their player number
     *
     * @param p The player
     * @return The player number
     */
    public int getPlayerNum(Player p) {
        if(p==null)
            return -1;
        if(players.length ==4){
            for (int i = 0; i < 4; i++) 
                if (players[i]!=null && players[i].equals(p)) 
                    return i + 1;
        }else{
            for (int i = 0; i < 2; i++) 
                if (players[i]!=null && players[i].equals(p)) 
                    return i + 1;
        }return -1;
    }

    /**
     * Returns the number of players still in the game.
     *
     * @return Number of players
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
     * Returns Vertex object at that coordinate on the board
     *
     * @param c Column of vertex
     * @param r Row of Vertex
     * @return Vertex object at that location on the board
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
     * Returns Vertex object at that coordinate on the board
     *
     * @param c Column of vertex
     * @param r Row of Vertex
     * @param board Board to get edgset from not just the default board
     * @return Vertex object at that location on the board
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
     * Gets the edges to remove when placing a wall
     *
     * @param wall The wall as a string: "[c] [r] [d]"
     * @return The set of edges to remove from the board to place that wall
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
            if (s2r != null) 
                remove.add(s2r);
            if (b2br != null) 
                remove.add(b2br);
        } else {
            if (s2b != null) 
                remove.add(s2b);
            if (r2br != null) 
                remove.add(r2br);
        }
        return remove;
    }

    /**
     * Sets wall counts for all players depending on player count
     *
     * @param playerCount Number of players in the game
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
     * Puts a player on the board, using the coordinates in the
     * Player object.
     *
     * @param player The player to add
     */
    private void addPlayer(Player player) {
        getVertexByCoord(player.getC(), player.getR()).placePlayer();
    }

    //Testing only methods
    /**
     * FOR TESTING PURPOSES ONLY. Removes a wall and increments the
     * player's wallcount. To be called just after placing a wall.
     *
     * @param playerNum Player number whose wallcount needs to be reverted
     * @param wall The wall to remove
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
     *
     * Returns the length of the players path if a wall is placed
     *
     *
     * @param playerNum The player to check
     * @param wall Wall to be place to see if it will be best
     * @return Whether or not edgeSet blocks playerNum from winning
     */
    public int pathLengthAfterWall(int playerNum, String wall){
        // Duplicate board SETUP
        UndirectedGraph<Vertex, Edge> boardCopy = new SimpleGraph<Vertex, Edge>(Edge.class);
        Graphs.addGraph(boardCopy, this.board);
        // builds the graph with correct C R Vertex Positions
        boardCopy = buildGraph(boardCopy);
        // puts each player on the board in the correct position
        placeWall(wall,boardCopy);
        return (int)getShortestWinningPath(playerNum,boardCopy).getPathLength();
    }
    
     /**
     * Places a wall. This wall must be validated by validWall() BEFORE being
     * placed. ## Modified for pathLengthAfterWall
     *
     * @param wall A string representing the wall: "[c] [r] [d]"
     */
    public void placeWall(String wall,UndirectedGraph<Vertex,Edge> boardCopy) {
        Set<Edge> edgesToRemove = getEdgesToRemove(wall,boardCopy);
        for (Edge e : edgesToRemove) {
            boardCopy.removeEdge(e);
        }
    }
    /**
     * Gets the edges to remove when placing a wall
     * ## Modified for pathLengthAfterWall
     * @param wall The wall as a string: "[c] [r] [d]"
     * @return The set of edges to remove from the board to place that wall
     */
    public Set<Edge> getEdgesToRemove(String wall,UndirectedGraph<Vertex,Edge> boardCopy) {
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

        Edge s2r = boardCopy.getEdge(s, r);
        Edge b2br = boardCopy.getEdge(b, br);
        Edge s2b = boardCopy.getEdge(s, b);
        Edge r2br = boardCopy.getEdge(r, br);

        Set<Edge> remove = new HashSet<Edge>();
  
        if(wall == "")
          return remove;

        // adding edges to the set to be removed based on wall placement direction
        if (d.equals("V")) {
            if (s2r != null) {
                remove.add(s2r);
            }
            if (b2br != null) {
                remove.add(b2br);
            }
        } else {
            if (s2b != null) {
                remove.add(s2b);
            }
            if (r2br != null) {
                remove.add(r2br);
            }
        }
        return remove;
    }




}





