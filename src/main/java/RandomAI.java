import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.jgrapht.alg.DijkstraShortestPath;

/**
 * @author  Craig Gardner
 * Documented by Josh Naar on 4/30/16
 */

public class RandomAI {

    private LogicalBoard board;
    private int playerCount;
    private int playerNum;

    /**
     * @param playerCount: number of players in game
     * @param playerNum: number of current player
     * Constructor
     */
    public RandomAI(int playerCount, int playerNum) {
        board = new LogicalBoard(playerCount);
        this.playerCount = playerCount;
        this.playerNum = playerNum;
    }

    /** 
     * @param player: number of player to be kicked
     * Kicks a player when an illegal move is made
     */
    public void kick(int player) {
        board.kick(player);
    }

    /**
     * @param playerNum: number of current player
     * @param move: instruction for current player's move
     * Updates the board to reflect most recent move
     */
    public void update(int playerNum, String move) {
        if (move.length() == 3) {
            board.makeMove(playerNum, move);
        } else {
            board.placeWall(playerNum, move);
        }
    }

    /**
     * @return String representation of move to be made
     * Get a player move in a 2Player Game
     */ 
    public String getMove() {
        // player1 init as null
        boolean P1hasWalls = false;
        Vertex p1BestMove = null;
        int playerOnePathLength = 1000;
        //player 2 init as null
        boolean P2hasWalls = false;
        Vertex p2BestMove = null;
        int playerTwoPathLength = 1000;
        //player 3 init as null
        boolean P3hasWalls = false;
        Vertex p3BestMove = null;
        int playerThreePathLength = 1000;
        // player 4 init as null
        boolean P4hasWalls = false;
        Vertex p4BestMove = null;
        int playerFourPathLength = 1000;
        
        //player 1
        if(board.getPlayer(1)!=null){
            P1hasWalls = board.getPlayer(1).hasWalls();
            p1BestMove = getBestMove(1);
            playerOnePathLength = (int) board.getShortestWinningPath(1, board.board).getPathLength();
        }
        // player 2
        if(board.getPlayer(2)!=null){
            P2hasWalls = board.getPlayer(2).hasWalls();
            p2BestMove = getBestMove(2);
            playerTwoPathLength = (int) board.getShortestWinningPath(2, board.board).getPathLength();
        }
        // if 2p no need to initialize
        if (playerCount > 2) {
            if(board.getPlayer(3)!=null){
                P3hasWalls = board.getPlayer(3).hasWalls();
                p3BestMove = getBestMove(3);
                playerThreePathLength = (int) board.getShortestWinningPath(3, board.board).getPathLength();
            }
            if(board.getPlayer(4)!=null){
                p4BestMove = getBestMove(4);
                playerFourPathLength = (int) board.getShortestWinningPath(4, board.board).getPathLength();
                P4hasWalls = board.getPlayer(4).hasWalls();
            }
        }
        
        
        // Changing any of these numbers will change the way the AI plays
        // each one is for that particular AI
        int one = 6;
        int two = 6;
        int three = 6;
        int four = 6;
        
               
        String wall = "";
        if (playerCount == 2) {
            // if this players path is shorter, move player position to here
            if (this.playerNum == 1) {
                if (playerTwoPathLength <= playerOnePathLength && P1hasWalls){ 
                    wall = getBestWall(1);
                    if (MyPathIsSame(1, wall)) 
                        return wall;
                }
                return p1BestMove.c + " " + p1BestMove.r;
            } else {
                if (playerOnePathLength <= playerTwoPathLength && P2hasWalls) {
                    wall = getBestWall(2);
                    if (MyPathIsSame(2, wall)) 
                        return wall;
                }
                return p2BestMove.c + " " + p2BestMove.r;
            }
        } else if (this.playerNum == 1) {
            if((playerFourPathLength<=one || playerTwoPathLength <= one || playerThreePathLength <= one) && playerOnePathLength>2){ 
                wall = getBestWall(1);
                if (MyPathIsSame(1, wall)) {
                    return wall;
                }
            }
            return p1BestMove.c + " " + p1BestMove.r;
        } else if (this.playerNum == 2) {
             if((playerOnePathLength<=two || playerThreePathLength <= two || playerThreePathLength <= two) && playerTwoPathLength>2){ 
                wall = getBestWall(2);
                if (MyPathIsSame(2, wall)) {
                    return wall;
                }
            }
            return p2BestMove.c + " " + p2BestMove.r;
        } else if (this.playerNum == 3) {
              if((playerOnePathLength<=three || playerTwoPathLength <= three || playerFourPathLength <=three) && playerThreePathLength>2){
                wall = getBestWall(3);
                if (MyPathIsSame(3, wall)) {
                    return wall;
                }
            }
            return p3BestMove.c + " " + p3BestMove.r;
        } else {
            if((playerOnePathLength<=four || playerTwoPathLength <= four || playerThreePathLength <= four) && playerFourPathLength>2){ 
                wall = getBestWall(4);
                if (MyPathIsSame(4, wall)) {
                    return wall;
                }
            }
            return p4BestMove.c + " " + p4BestMove.r;
        }

    }

    /**
     * @param playerNum: number of current player
     * @return String representation of best pawn movement
     * Gets best pawn movement for current player
     */
    private Vertex getBestMove(int playerNum) {
        DijkstraShortestPath<Vertex, Edge> shortest = board.getShortestWinningPath(playerNum, board.board);
        List<Edge> winningEdges = shortest.getPathEdgeList();
        Set<Vertex> winningVertices = new HashSet<>();
        for (Edge e : winningEdges) {
            winningVertices.add(e.getTarget());
            winningVertices.add(e.getSource());
        }
        Set<Vertex> validVertices = board.getValidMoves(playerNum);
        for (Vertex v : validVertices) {
            if (winningVertices.contains(v)) {
                return v;
            }
        }
        return null;
    }

    /**
     * @param player: player requesting wall
     * @return Wall String to be sent to server
     * Gets the best wall for a player to place whether it is 2 or 4 player game
     */
    private String getBestWall(int player) {
        String bestWall = "";
        String tempWallH = "";
        String tempWallV = "";
        int currentPathLengthP1 = 1000;
        if(board.getPlayer(1)!=null)
            currentPathLengthP1 = (int) board.getShortestWinningPath(1, board.board).getPathLength();
        int currentPathLengthP2 = 1000;
        if(board.getPlayer(2)!=null)
            currentPathLengthP2 = (int) board.getShortestWinningPath(2, board.board).getPathLength();
        int currentPathLengthP3 = 1000;
        int currentPathLengthP4 = 1000;

        if (playerCount > 2) {
            if(board.getPlayer(3)!=null)
                currentPathLengthP3 = (int) board.getShortestWinningPath(3, board.board).getPathLength();
            if(board.getPlayer(4)!=null)
                currentPathLengthP4 = (int) board.getShortestWinningPath(4, board.board).getPathLength();
        }
        
        if(currentPathLengthP1<=2 && playerNum!=1 && board.getPlayer(1) != null){
          Vertex temp = board.getVertexByCoord(board.getPlayer(1).getC(),board.getPlayer(1).getR());
          if(board.validWall(1,temp.c + " " + (temp.r) + " h"))
            return temp.c + " " + (temp.r) + " h";
        }
        if(currentPathLengthP2<=2 && playerNum!=2 && board.getPlayer(2) != null){
          Vertex temp = board.getVertexByCoord(board.getPlayer(2).getC(),board.getPlayer(2).getR());
          if(board.validWall(2,temp.c + " " + temp.r+1 + " h"))
            return temp.c + " " + temp.r+1 + " h";
        }
        if(currentPathLengthP3<=2 && playerNum!=3 && board.getPlayer(3) != null){
          Vertex temp = board.getVertexByCoord(board.getPlayer(3).getC(),board.getPlayer(3).getR());
          if(board.validWall(3,temp.c + " " + temp.r + " v"))
            return temp.c + " " + temp.r + " v";
        }
        if(currentPathLengthP4<=2 && playerNum!=4 && board.getPlayer(4) != null){
          Vertex temp = board.getVertexByCoord(board.getPlayer(4).getC(),board.getPlayer(4).getR());
          if(board.validWall(4,(temp.c-1) + " " + temp.r + " v"))
            return (temp.c-1) + " " + temp.r + " v";
        }
        
        
        int tempH = 0;
        int tempV = 0;
        int temp3 = 0;
        // Current Column
        for (int c = 0; c < 8; c++) {
            // Current Row
            for (int r = 0; r < 8; r++) {
                tempWallH = c + " " + r + " h";
                tempWallV = c + " " + r + " v";
                //Player 1
                if (player != 1 && board.getPlayer(1)!=null) {
                    // if it is a valid wall continue
                    if (board.validWall(player, tempWallH)) {
                        // Length of p1's path after the wall placement
                        tempH = board.pathLengthAfterWall(1, tempWallH);
                        // will i benefit from placing this wall?
                        if (currentPathLengthP1 < tempH) // is this player the most important one?
                            if(tempH>temp3){
                                bestWall = tempWallH;
                                temp3 = tempH;
                            }
                    }if(board.validWall(player, tempWallV)) {
                        tempV = board.pathLengthAfterWall(1, tempWallV);
                        // will i benefit from placing this wall?
                        if (currentPathLengthP1 < tempV) // is this player the most important one?
                            if(tempV > temp3){
                                bestWall = tempWallV;
                                temp3 = tempV;
                            }
                    }
                    if (board.validWall(player, tempWallV) && board.validWall(player, tempWallH)) {
                        
                        if (tempH > tempV && tempH > temp3) {
                            bestWall = tempWallH;
                            temp3 = tempH;
                        } else if (tempV > tempH && tempV > temp3) {
                            bestWall = tempWallV;
                            temp3 = tempV;
                        }
                            
                    }
                    // Player 2
                }
                //player 2
                if (player != 2 && board.getPlayer(2)!=null) {
                    // if it is a valid wall continue
                    if (board.validWall(player, tempWallH)) {
                        // Length of p1's path after the wall placement
                        tempH = board.pathLengthAfterWall(2, tempWallH);
                        // will i benefit from placing this wall?
                        if (currentPathLengthP1 < tempH) // is this player the most important one?
                            if(tempH>temp3){
                                bestWall = tempWallH;
                                temp3 = tempH;
                            }
                    }
                    if(board.validWall(player, tempWallV)) {
                        tempV = board.pathLengthAfterWall(2, tempWallV);
                        // will i benefit from placing this wall?
                        if (currentPathLengthP1 < tempV) // is this player the most important one?
                            if(tempV > temp3){
                                bestWall = tempWallV;
                                temp3 = tempV;
                            }
                    }
                    if (board.validWall(player, tempWallV) && board.validWall(player, tempWallH)) {
                        
                        if (tempH > tempV && tempH > temp3) {
                            bestWall = tempWallH;
                            temp3 = tempH;
                        } else if (tempV > tempH && tempV > temp3) {
                            bestWall = tempWallV;
                            temp3 = tempV;
                        }
                            
                    }
                    // Player 2
                }
                // Fpur Players
                if (this.playerCount > 2) {
                    //Player 3
                    if (player != 3 && board.getPlayer(3)!=null) {
                        // if it is a valid wall continue
                        if (board.validWall(player, tempWallH)) {
                            // Length of p1's path after the wall placement
                            tempH = board.pathLengthAfterWall(3, tempWallH);
                            // will i benefit from placing this wall?
                            if (currentPathLengthP3 < tempH) // is this player the most important one?
                                if(tempH>temp3){
                                    bestWall = tempWallH;
                                    temp3 = tempH;
                                }
                        }if(board.validWall(player, tempWallV)) {
                            tempV = board.pathLengthAfterWall(3, tempWallV);
                            // will i benefit from placing this wall?
                            if (currentPathLengthP3 < tempV) // is this player the most important one?
                                if(tempV > temp3){
                                    bestWall = tempWallV;
                                    temp3 = tempV;
                                }
                        }
                        if (board.validWall(player, tempWallV) && board.validWall(player, tempWallH)) {
                            
                            if (tempH > tempV && tempH > temp3) {
                                bestWall = tempWallH;
                                temp3 = tempH;
                            } else if (tempV > tempH && tempV > temp3) {
                                bestWall = tempWallV;
                                temp3 = tempV;
                            }
                                
                        }
                    }
                    // Player Four
                    if (player != 4 && board.getPlayer(4)!=null) {
                        // if it is a valid wall continue
                        if (board.validWall(player, tempWallH)) {
                            // Length of p1's path after the wall placement
                            tempH = board.pathLengthAfterWall(4, tempWallH);
                            // will i benefit from placing this wall?
                            if (currentPathLengthP4 < tempH) // is this player the most important one?
                                if(tempH>temp3){
                                    bestWall = tempWallH;
                                    temp3 = tempH;
                                }
                        }if(board.validWall(player, tempWallV)) {
                            tempV = board.pathLengthAfterWall(4, tempWallV);
                            // will i benefit from placing this wall?
                            if (currentPathLengthP4 < tempV) // is this player the most important one?
                                if(tempV > temp3){
                                    bestWall = tempWallV;
                                    temp3 = tempV;
                            }
                        }if (board.validWall(player, tempWallV) && board.validWall(player, tempWallH)) {
                            if (tempH > tempV && tempH > temp3) {
                                bestWall = tempWallH;
                                temp3 = tempH;
                            } else if (tempV > tempH && tempV > temp3) {
                                bestWall = tempWallV;
                                temp3 = tempV;
                            }
                                
                        }                    
                    }
                }
            }
        }
        if(Math.random() <= .75)
          return bestWall;
        return "";
    }

    /**
     * @param player: number of current player
     * @param wall: String representation of wall to be placed
     * @return: Boolean representation of whether this wall will negatively affect the player that is about to place it          
     * Makes sure wall about to be placed doesn't interfere with its owner's movement                  
     */
    private boolean MyPathIsSame(int player, String wall) {
        if (wall.equals("")) {
            return false;
        }
        if ((int) board.getShortestWinningPath(player, board.board).getPathLength() == board.pathLengthAfterWall(player, wall)) {
            return true;
        }
        return false;
    }
}
