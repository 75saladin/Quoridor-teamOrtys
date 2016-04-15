
import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.jgrapht.alg.DijkstraShortestPath;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jed_lechner
 */
public class RandomAI {

    private LogicalBoard board;
    private int playerCount;
    private int playerNum;
    private Random rand = new Random();

    /**
     *
     * @param playerCount
     * @param playerNum
     */
    public RandomAI(int playerCount, int playerNum) {
        board = new LogicalBoard(playerCount);
        this.playerCount = playerCount;
        this.playerNum = playerNum;
    }

    /**
     *
     * @param player
     */
    public void kick(int player) {
        board.kick(player);
    }

    /**
     *
     * @param playerNum
     * @param move
     */
    public void update(int playerNum, String move) {
        if (move.length() == 3) {
            board.makeMove(playerNum, move);
        } else {
            board.placeWall(playerNum, move);
        }
    }

    /**
     * get a player move in a 2Player Game
     *
     * @param playerNum - player requesting move
     * @return
     */
    public String getMove() {
        //player 1
        boolean P1hasWalls = board.getPlayer(1).hasWalls();
        Vertex p1BestMove = getBestMove(1);
        int playerOnePathLength = (int) board.getShortestWinningPath(1, board.board).getPathLength();
        // player 2
        boolean P2hasWalls = board.getPlayer(2).hasWalls();
        Vertex p2BestMove = getBestMove(2);
        int playerTwoPathLength = (int) board.getShortestWinningPath(2, board.board).getPathLength();
        //player 3
        boolean P3hasWalls = false;
        Vertex p3BestMove = null;
        int playerThreePathLength = 0;
        // player 4
        boolean P4hasWalls = false;
        Vertex p4BestMove = null;
        int playerFourPathLength = 0;

        // if 2p no need to initialize
        if (playerCount > 2) {
            P3hasWalls = board.getPlayer(3).hasWalls();
            p3BestMove = getBestMove(3);
            playerThreePathLength = (int) board.getShortestWinningPath(3, board.board).getPathLength();

            p4BestMove = getBestMove(4);
            playerFourPathLength = (int) board.getShortestWinningPath(4, board.board).getPathLength();
            P4hasWalls = board.getPlayer(4).hasWalls();
        }
        String wall = "";
        if (playerCount == 2) {
            // if this players path is shorter, move player position to here
            if (this.playerNum == 1) {
                if (playerOnePathLength <= playerTwoPathLength-1) {
                    return p1BestMove.c + " " + p1BestMove.r;
                } else if (P1hasWalls) {
                    wall = getBestWall(1);
                    if (MyPathIsSame(1, wall)) {
                        return wall;
                    }
                }
                return p1BestMove.c + " " + p1BestMove.r;
            } else {
                if (playerTwoPathLength <= playerOnePathLength-1) {
                    return p2BestMove.c + " " + p2BestMove.r;
                } else if (P2hasWalls) {
                    wall = getBestWall(2);
                    if (MyPathIsSame(2, wall)) {
                        return wall;
                    }
                }
                return p2BestMove.c + " " + p2BestMove.r;
            }
        } else if (this.playerNum == 1) {
            if (playerOnePathLength <= playerTwoPathLength && playerOnePathLength <= playerThreePathLength && playerOnePathLength <= playerFourPathLength) {
                return p1BestMove.c + " " + p1BestMove.r;
            } else if (P1hasWalls) {
                wall = getBestWall(1);
                if (MyPathIsSame(1, wall)) {
                    return wall;
                }
            }
            return p1BestMove.c + " " + p1BestMove.r;
        } else if (this.playerNum == 2) {
            if (playerTwoPathLength <= playerOnePathLength && playerTwoPathLength <= playerThreePathLength && playerTwoPathLength <= playerFourPathLength) {
                return p2BestMove.c + " " + p2BestMove.r;
            } else if (P2hasWalls) {
                wall = getBestWall(2);
                if (MyPathIsSame(2, wall)) {
                    return wall;
                }
            }
            return p2BestMove.c + " " + p2BestMove.r;
        } else if (this.playerNum == 3) {
            if (playerThreePathLength <= playerOnePathLength && playerThreePathLength <= playerTwoPathLength && playerThreePathLength <= playerFourPathLength) {
                return p3BestMove.c + " " + p3BestMove.r;
            } else if (P3hasWalls) {
                wall = getBestWall(3);
                if (MyPathIsSame(3, wall)) {
                    return wall;
                }
            }
            return p3BestMove.c + " " + p3BestMove.r;
        } else {
            if (playerFourPathLength <= playerOnePathLength && playerFourPathLength <= playerTwoPathLength && playerFourPathLength <= playerThreePathLength) {
                return p4BestMove.c + " " + p4BestMove.r;
            } else if (P4hasWalls) {
                wall = getBestWall(4);
                if (MyPathIsSame(4, wall)) {
                    return wall;
                }
            }
            return p4BestMove.c + " " + p4BestMove.r;
        }

    }

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
     * getBestWall - gets the best wall for a player to place whether it is 2 or
     * 4 player game
     *
     * @param player - player requesting wall
     * @return Wall String to be sent to server
     */
    private String getBestWall(int player) {
        String bestWall = "";
        String tempWallH = "";
        String tempWallV = "";
        int currentPathLengthP1 = (int) board.getShortestWinningPath(1, board.board).getPathLength();
        int currentPathLengthP2 = (int) board.getShortestWinningPath(2, board.board).getPathLength();
        int currentPathLengthP3 = 0;
        int currentPathLengthP4 = 0;

        int shortest = currentPathLengthP1;
        if(currentPathLengthP2<shortest)
            shortest = currentPathLengthP2;
        

        if (playerCount > 2) {
            currentPathLengthP3 = (int) board.getShortestWinningPath(3, board.board).getPathLength();
            currentPathLengthP4 = (int) board.getShortestWinningPath(4, board.board).getPathLength();
            if(currentPathLengthP3<shortest)
                shortest = currentPathLengthP3;
            if(currentPathLengthP4<shortest)
                shortest = currentPathLengthP4;
        }
        int temp = 0;
        int temp3 = 100;
        // Current Column
        for (int c = 0; c < 8; c++) {
            // Current Row
            for (int r = 0; r < 8; r++) {
                tempWallH = c + " " + r + " H";
                tempWallV = c + " " + r + " V";
                //Player 1
                if (player != 1) {
                    // if it is a valid wall continue
                    if (board.validWall(player, tempWallH)) {
                        // Length of p1's path after the wall placement
                        temp = board.pathLengthAfterWall(1, tempWallH);
                        // will i benefit from placing this wall?
                        if (currentPathLengthP1 < temp) // is this player the most important one?
                            if(temp<temp3){
                                bestWall = tempWallH;
                                temp3 = temp;
                            }
                    }
                    if (board.validWall(player, tempWallV)) {
                        temp = board.pathLengthAfterWall(1, tempWallV);
                        if (currentPathLengthP1 < temp)
                            if(temp<temp3){
                                bestWall = tempWallV;
                                temp3 = temp;
                            }
                    }
                    // Player 2
                }
                if (player != 2) {
                    if (board.validWall(player, tempWallH)) {
                        temp = board.pathLengthAfterWall(2, tempWallH);
                        if (currentPathLengthP2 < temp)
                            if(temp<temp3){
                                bestWall = tempWallH;
                                temp3 = temp;
                            }
                    }
                    if (board.validWall(player, tempWallV)) {
                        temp = board.pathLengthAfterWall(2, tempWallV);
                        if (currentPathLengthP2 < temp)
                            if(temp<temp3){
                                bestWall = tempWallV;
                                temp3 = temp;
                            }
                    }
                }
                // Fpur Players
                if (this.playerCount > 2) {
                    //Player 3
                    if (player != 3) {
                        if (board.validWall(player, tempWallH)) {
                            temp = board.pathLengthAfterWall(3, tempWallH);
                            if (currentPathLengthP3 < temp)
                                if(temp<temp3){
                                    bestWall = tempWallH;
                                    temp3 = temp;
                                }
                        }if (board.validWall(player, tempWallV)) {
                            temp = board.pathLengthAfterWall(3, tempWallV);
                            if (currentPathLengthP3 < temp) 
                                if(temp<temp3){
                                    bestWall = tempWallV;
                                    temp3 = temp;
                                }
                        }
                    }
                    // Player Four
                    if (player != 4) {
                        if (board.validWall(player, tempWallH)) {
                            temp = board.pathLengthAfterWall(4, tempWallH);
                            if (currentPathLengthP4 < temp)
                                if(temp<temp3){
                                    bestWall = tempWallH;
                                    temp3 = temp;
                                }
                        }
                        if (board.validWall(player, tempWallV)) {
                            temp = board.pathLengthAfterWall(4, tempWallV);
                            if (currentPathLengthP4 < temp)
                                if(temp<temp3){
                                    bestWall = tempWallV;
                                    temp3 = temp;
                                }
                        }
                    }
                }
            }
        }
        return bestWall;
    }

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
