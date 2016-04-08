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
        board.makeMove(1, "4 4");
        board.makeMove(2, "4 5");
        System.out.println(board.getValidMoves(1));
        System.out.println(board.getValidMoves(2));
        
    }
    public static void println(String print){
        System.out.println(print);
    }
    
    
}
