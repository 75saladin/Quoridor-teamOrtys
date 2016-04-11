
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

    private GUI gui;
    private LogicalBoard board;
    private int playerCount;
    private int playerNum;
    private Random rand = new Random();

    public RandomAI(GUI g) {
        this.gui = g;
    }

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
     * getMove - gets the best move for THIS player
     *
     * @return String move or wall depending on best move circumstances
     */
    public String getMove() {
        if (playerCount == 2) {
            return getMove2P();
        } else {
            return getMove4P();
        }
    }

    public String getRandomMove() {
        // example player1 is at 4, 0
        // player 1 can move to (4, 1), (3, 0), or (5, 0)
        // same column + or - 1 row or same row + or - one column
        Set<Vertex> validVertices = board.getValidMoves(this.playerNum);
        System.out.println(validVertices);
        for (Vertex v : validVertices) {
            return Parser.formatMove(v.c + " " + v.r);
        }
        return "? ?";
    }
    
    
    /**
     * get a player move in a 2 player game NOT WORKING
     *
     * @param playerNum - player requesting move
     * @return
     */
    private String getMove2P() {

        // position of players best next move
        Vertex player1BestMove = null;
        Vertex player2BestMove = null;

        //paths of each player
        DijkstraShortestPath<Vertex, Edge> player1Path = board.getShortestWinningPath(1);
        DijkstraShortestPath<Vertex, Edge> player2Path = board.getShortestWinningPath(2);

        // length of each players path
        int playerOnePathLength = (int) player1Path.getPathLength();
        int playerTwoPathLength = (int) player2Path.getPathLength();

        Vertex p1BestMove = getBestMove1();
        Vertex p2BestMove = getBestMove2();

        Player p1 = board.getPlayer(1);
        Player p2 = board.getPlayer(2);
        
        // if this players path is shorter, move player position to here
        if (this.playerNum == 1){
            if(playerOnePathLength<=playerTwoPathLength || !board.validWall(1,p2.getC()+" "+p2.getR()+" H"))
                return p1BestMove.c + " " + p1BestMove.r;
            else
                return board.getPlayer(2).getC() + " " + (board.getPlayer(2).getR()-1) + " H"; 
        } else{// if(this.playerNum==2){
            if(playerOnePathLength>=playerTwoPathLength || !board.checkValid(2,p1.getC()+" "+p1.getR()+" H"))
                return p2BestMove.c + " " + p2BestMove.r;
            else
                return board.getPlayer(1).getC() + " " + (board.getPlayer(1).getR()) + " H";
        }/*else if(this.playerNum==3){
            if(playerOnePathLength>=playerTwoPathLength || !board.checkValid(2,p1.getC()+" "+p1.getR()+" H"))
                return p2BestMove.c + " " + p2BestMove.r;
            else
                return board.getPlayer(1).getC()-1 + " " + (board.getPlayer(1).getR()) + " V";
        }else{
            if(playerOnePathLength>=playerTwoPathLength || !board.checkValid(2,p1.getC()+" "+p1.getR()+" H"))
                return p2BestMove.c + " " + p2BestMove.r;
            else
                return board.getPlayer(1).getC() + " " + (board.getPlayer(1).getR()) + " V";
            
        }*/
    }

    private String getMove4P() {

        DijkstraShortestPath<Vertex, Edge> player1 = board.getShortestWinningPath(1);
        DijkstraShortestPath<Vertex, Edge> player2 = board.getShortestWinningPath(2);
        DijkstraShortestPath<Vertex, Edge> player3 = null;
        DijkstraShortestPath<Vertex, Edge> player4 = null;
        if (playerCount > 2) {
            player3 = board.getShortestWinningPath(3);
            player4 = board.getShortestWinningPath(4);
        }
        int playerOnePathLength = (int) player1.getPathLength();
        int playerTwoPathLength = (int) player1.getPathLength();
        int playerThreePathLength = (int) player1.getPathLength();
        int playerFourPathLength = (int) player1.getPathLength();
        return "";
    }

    public Vertex getBestMove1(){
        Player p = board.getPlayer(1);
        DijkstraShortestPath<Vertex,Edge> shortest = board.getShortestWinningPath(1);
        List<Edge> winningEdges = shortest.getPathEdgeList();
        Set<Vertex> winningVertices = new HashSet<>();
        for(Edge e : winningEdges){
            winningVertices.add(e.getTarget());
            winningVertices.add(e.getSource());
        }
        Set<Vertex> validVertices = board.getValidMoves(1);
        Vertex vUP = board.getVertexByCoord(p.getC(),p.getR()-1);
        Vertex vRIGHT = board.getVertexByCoord(p.getC()+1,p.getR());
        Vertex vLEFT = board.getVertexByCoord(p.getC()-1,p.getR());
        Vertex vBELOW = board.getVertexByCoord(p.getC(),p.getR()+1);
        if(validVertices.contains(vBELOW) && winningVertices.contains(vBELOW))
            return vBELOW;
        else if(validVertices.contains(vRIGHT)&& winningVertices.contains(vRIGHT))
            return vRIGHT;
        else if(validVertices.contains(vLEFT)&& winningVertices.contains(vLEFT))
            return vLEFT;
        else
            return vUP;
    }
    
    public Vertex getBestMove2(){
        Player p = board.getPlayer(2);
        DijkstraShortestPath<Vertex,Edge> shortest = board.getShortestWinningPath(2);
        List<Edge> winningEdges = shortest.getPathEdgeList();
        Set<Vertex> winningVertices = new HashSet<>();
        for(Edge e : winningEdges){
            winningVertices.add(e.getTarget());
            winningVertices.add(e.getSource());
        }
        Set<Vertex> validVertices = board.getValidMoves(2);
        Vertex vUP = board.getVertexByCoord(p.getC(),p.getR()-1);
        Vertex vRIGHT = board.getVertexByCoord(p.getC()+1,p.getR());
        Vertex vLEFT = board.getVertexByCoord(p.getC()-1,p.getR());
        Vertex vBELOW = board.getVertexByCoord(p.getC(),p.getR()+1);
        if(validVertices.contains(vUP) && winningVertices.contains(vUP))
            return vUP;
        else if(validVertices.contains(vRIGHT)&& winningVertices.contains(vRIGHT))
            return vRIGHT;
        else if(validVertices.contains(vLEFT)&& winningVertices.contains(vLEFT))
            return vLEFT;
        else
            return vBELOW;
    }
    public Vertex getBestMove3(){
        Player p = board.getPlayer(3);
        DijkstraShortestPath<Vertex,Edge> shortest = board.getShortestWinningPath(3);
        List<Edge> winningEdges = shortest.getPathEdgeList();
        Set<Vertex> winningVertices = new HashSet<>();
        for(Edge e : winningEdges){
            winningVertices.add(e.getTarget());
            winningVertices.add(e.getSource());
        }
        Set<Vertex> validVertices = board.getValidMoves(3);
        Vertex vUP = board.getVertexByCoord(p.getC(),p.getR()-1);
        Vertex vRIGHT = board.getVertexByCoord(p.getC()+1,p.getR());
        Vertex vLEFT = board.getVertexByCoord(p.getC()-1,p.getR());
        Vertex vBELOW = board.getVertexByCoord(p.getC(),p.getR()+1);
        if(validVertices.contains(vRIGHT) && winningVertices.contains(vRIGHT))
            return vRIGHT;
        else if(validVertices.contains(vUP)&& winningVertices.contains(vUP))
            return vUP;
        else if(validVertices.contains(vBELOW)&& winningVertices.contains(vBELOW))
            return vBELOW;
        else
            return vLEFT;
    }
    public Vertex getBestMove4(){
        Player p = board.getPlayer(4);
        DijkstraShortestPath<Vertex,Edge> shortest = board.getShortestWinningPath(4);
        List<Edge> winningEdges = shortest.getPathEdgeList();
        Set<Vertex> winningVertices = new HashSet<>();
        for(Edge e : winningEdges){
            winningVertices.add(e.getTarget());
            winningVertices.add(e.getSource());
        }
        Set<Vertex> validVertices = board.getValidMoves(4);
        Vertex vUP = board.getVertexByCoord(p.getC(),p.getR()-1);
        Vertex vRIGHT = board.getVertexByCoord(p.getC()+1,p.getR());
        Vertex vLEFT = board.getVertexByCoord(p.getC()-1,p.getR());
        Vertex vBELOW = board.getVertexByCoord(p.getC(),p.getR()+1);
        if(validVertices.contains(vLEFT) && winningVertices.contains(vLEFT))
            return vLEFT;
        else if(validVertices.contains(vUP)&& winningVertices.contains(vUP))
            return vUP;
        else if(validVertices.contains(vBELOW)&& winningVertices.contains(vBELOW))
            return vBELOW;
        else
            return vRIGHT;
    }
}
