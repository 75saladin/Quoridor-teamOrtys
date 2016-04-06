
import java.awt.Point;
import java.util.List;
import java.util.Random;

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
    
    public RandomAI(GUI g) {
        gui = g;
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
