

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jed_lechner
 */
public class User {

    private LogicalBoard board;
    private int playerCount;
    private int playerNum;
    // Jed i need you here
    private userGUI gui = null;

    /**
     *
     * @param playerCount
     * @param playerNum
     */
    public User(int playerCount, int playerNum) {
        board = new LogicalBoard(playerCount);
        this.playerCount = playerCount;
        this.playerNum = playerNum;
        //jed i also need you here
    }

    /**
     *
     * @param player
     */
    public void kick(int player) {
        // we need to update the User's gui here
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
        // we also need to update the users gui here
        } else {
            board.placeWall(playerNum, move);
        // we also need to update the users gui here
        }
    }

    /**
     * get a player move in a 2Player Game
     *
     * @param playerNum - player requesting move
     * @return
     */
    public String getMove() {
        String move = "";
        // jed I need to know what you return from your gui when it is clicked
        return move;
    }

}
