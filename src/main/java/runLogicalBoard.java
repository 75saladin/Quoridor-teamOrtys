/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author craig
 */
public class runLogicalBoard {
    public static void main(String[]args){
        LogicalBoard board = new LogicalBoard(2);
        System.out.println("Player 1: "+board.getPlayer(1));
        board.validMove(1, "4 2");
        System.out.println("Player 1: "+board.getPlayer(1));
        System.out.println("cannot move 2 spaces at once. Check.");
        board.checkValid(1, "4 0 H");
        System.out.println("Player 1: "+board.getPlayer(1));
        board.checkValid(1, "4 0");
        System.out.println("cannot move to the same spot");
        board.checkValid(1, "4 1");
        System.out.println("Player 1: "+board.getPlayer(1));
        println("cannot move over a wall");
        board.checkValid(1, "4 2");
        board.checkValid(1, "4 3");
        board.checkValid(1, "4 4");
        board.checkValid(1, "4 5");
        board.checkValid(1, "4 6");
        board.checkValid(1, "4 7");
        println("Player 1: "+board.getPlayer(1).toString());
        board.checkValid(2, "4 6");
        println("Player 2: "+board.getPlayer(2).toString());
        println("player 2 jumped player 1");
        board.checkValid(1, "4 6");
        println("Player 1: "+board.getPlayer(1).toString());
        println("Player 1 tried to move onto player two and couldntt");
        board.checkValid(2, "4 6 H");
        board.checkValid(1, "4 5");
        println("Player 2: "+board.getPlayer(1).toString());
        println("Player 1: "+board.getPlayer(2).toString());
        println("Tried to jump over a wall");
        board.checkValid(2, "4 6 h");
        board.checkValid(2, "4 6 V");
        board.checkValid(2, "4 7 V");
        board.checkValid(2, "4 8 V");
        println("Player 2: "+board.getPlayer(1).toString());
        println("Player 2 tried to place an invalid wall");
    }
    public static void println(String print){
        System.out.println(print);
    }
    
    
}
