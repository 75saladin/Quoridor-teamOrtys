
import java.awt.Point;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
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
    
    public RandomAI(GUI g) {
        this.gui = g;
    }
    /**
     * 
     * @param playerCount
     * @param playerNum 
     */
    public RandomAI(int playerCount, int playerNum){
        board = new LogicalBoard(playerCount);
        this.playerCount = playerCount;
        this.playerNum = playerNum;
    }
    /**
     * 
     * @param player 
     */
    public void kick(int player){
        board.kick(player);
    }

    /**
     * 
     * @param playerNum
     * @param move 
     */
    public void update(String move){
        Scanner sc = new Scanner(move);
        int playerNum = sc.nextInt();
        String temp = "";
        temp = sc.next()+" ";
        temp = sc.next();
        if(sc.hasNext()){
            temp = " " + sc.next();
            board.placeWall(playerNum, move);
        }else 
            board.makeMove(playerNum, move);
    }
                            
    /**
     * getMove - gets the best move for THIS player  
     * 
     * @return  String move or wall depending on best move circumstances
     */
    public String getMove(){
        if(playerCount==2)
            return getMove2P();
        else
            return getMove4P();
    }
    
    
    /**
     * get a player move in a 2 player game
     * 
     * @param playerNum - player requesting move
     * @return 
     */
    private String getMove2P(){
        //Players in game
        Player player1 = board.getPlayer(1);
        Player player2 = board.getPlayer(2);
        
        // current position of players
        Vertex player1Source = board.getVertexByCoord(player1.getC(),player1.getR());
        Vertex player2Source = board.getVertexByCoord(player2.getC(),player2.getR());
        
        // position of players best next move
        Vertex player1BestMove = null;
        Vertex player2BestMove = null;
        
        //paths of each player
        DijkstraShortestPath <Vertex,Edge> player1Path = board.getShortestWinningPath(1);
        DijkstraShortestPath <Vertex,Edge> player2Path = board.getShortestWinningPath(2);
        
        // length of each players path
        int playerOnePathLength = (int)player1Path.getPathLength();
        int playerTwoPathLength = (int)player2Path.getPathLength();
        
        // List of Edges for best move
        List<Edge> player1EdgeList = player1Path.getPathEdgeList();
        List<Edge> player2EdgeList = player2Path.getPathEdgeList();
        
        // logic to get next best move for player1
        Vertex EdgeSource = null;
        Vertex EdgeTarget = null;

        // getting next node on path next to the player1 position
        for(Edge e : player1EdgeList){
            EdgeTarget = e.getTarget();
            EdgeSource = e.getSource();
            // if the source of this edge is the location of the player
            // the next best move will be the target Vertex of this edge
            if(EdgeSource.equals(player1Source))
                    player1BestMove = EdgeTarget;
        }

        // if we need to make a jump of any kind
        // this second iteration out of the first is so that we get
        // the next position based off of best move
        while(player1BestMove.isHere()){
            for(Edge e : player1EdgeList){
                EdgeTarget = e.getTarget();
                EdgeSource = e.getSource();
                // if the source of this edge is the location of the player
                // the next best move will be the target Vertex of this edge
                if(EdgeSource.equals(player1BestMove))
                        player1BestMove = EdgeTarget;
            }
        }
        //
        //
        // getting next node on path next to the player2 position
        //
        //
        for(Edge e : player2EdgeList){
            EdgeTarget = e.getTarget();
            EdgeSource = e.getSource();
            // if the source of this edge is the location of the player
            // the next best move will be the target Vertex of this edge
            if(EdgeSource.equals(player2Source))
                    player2BestMove = EdgeTarget;
        }


        // if we need to make a jump of any kind
        // this second iteration out of the first is so that we get
        // the next position based off of best move
        while(player2BestMove.isHere()){
            for(Edge e : player2EdgeList){
                EdgeTarget = e.getTarget();
                EdgeSource = e.getSource();
                // if the source of this edge is the location of the player
                // the next best move will be the target Vertex of this edge
                if(EdgeSource.equals(player2BestMove))
                        player2BestMove = EdgeTarget;
            }
        }
        
        
        // if this players path is shorter, move player position to here
        if(this.playerNum==1)
        //    if(playerOnePathLength<playerTwoPathLength)
                return player1BestMove.c + " " + player1BestMove.r + " 0";
        else
         //   if(playerOnePathLength>playerTwoPathLength)
                return player2BestMove.c + " " + player2BestMove.r + " ";

        
        
        // START LOGIC FOR CORRECT WALL PLACEMENT IF Opponent has a shorter path
        
        //when returning wall placement - "<int col> <int row> (1(Vertical) 2(Horizontal)) 
        

        //return "";
    }
    
    
    
    private String getMove4P(){
        
        DijkstraShortestPath <Vertex,Edge> player1 = board.getShortestWinningPath(1);
        DijkstraShortestPath <Vertex,Edge> player2 = board.getShortestWinningPath(2);
        DijkstraShortestPath <Vertex,Edge> player3 = null;
        DijkstraShortestPath <Vertex,Edge> player4 = null;
        if(playerCount>2){
            player3 = board.getShortestWinningPath(3);
            player4 = board.getShortestWinningPath(4);
        }
        int playerOnePathLength = (int)player1.getPathLength();
        int playerTwoPathLength = (int)player1.getPathLength();
        int playerThreePathLength = (int)player1.getPathLength();
        int playerFourPathLength = (int)player1.getPathLength();
        switch(playerNum){
            case 1:
                
                
                    
            case 2:
                
            case 3:
                
            case 4:
                
                
        }
        
        return "";
    }
    
    
    
    
    public Point getRandomMove(Controller pl) {
        Controller player = pl;
        // is there a player in the surrounding squares
        int turn = player.getPlayerTurn();
        // current players position
        Point p = player.getPlayerPosition(turn);
        
        Random rand = new Random();

        // example player1 is at 4, 0
        // player 1 can move to (4, 1), (3, 0), or (5, 0)
        // same column + or - 1 row or same row + or - one column
        int move = rand.nextInt(4);
        switch(move) {
            case 0: // same column - 1 row
                return new Point(p.x, p.y - 1);
            case 1: // same column + 1 row
                return new Point(p.x, p.y + 1);
            case 2: // same row + 1 column
                return new Point(p.x + 1, p.y);
            case 3:
                return new Point(p.x-1, p.y);
        }
        return null;
    }
    
    public boolean valid(Point p, Controller pl) {
        Controller player = pl;
        int turn = player.getPlayerTurn();
        // current players position
        
        // next players position
        if(turn == player.getPlayerCount()) {
            turn = 1;
        }else {
            turn++;
        }
        // next players position
        Point p1 = player.getPlayerPosition(turn);
        
        if(p.equals(p1) || (p.y < 0 || p.y > 8) || (p.x < 0 || p.x > 8))
            return false;
        
        return true;
    }

    
    // you can't build walls 8, 0 
    // you can't build walls a, 8
    
    
    
}
