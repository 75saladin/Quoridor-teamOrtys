

/**
 *
 * @author craig
 */
public class Player{
    
    private int playerNum;
    private int c;
    private int r;

    /**
     * Player Constructor
	 * player number will determine starting position
	 * board will be placing it on the board logically
     */
    public Player(int playerNum,LogicalBoard board){
		this.playerNum=playerNum;
		initPlayer(this,board);
    }
    
    /*
    *   placePlayer - puts player on board when initializing
    *
    *   Parameter - Player that is being initialized
    */

    /**
     * Can probably Call jeds gui stuff from here
     * might be the best way
     * @param player
     */

    public void initPlayer(Player player,LogicalBoard board) {
		// Top player
        if(player.getPlayerNum() == 1){
            board.getVertexByCoord(0,4).placePlayer(player);
        	player.setC(0);
        	player.setR(4);
        }
        // bottom player
        if(player.getPlayerNum()==2){
            board.getVertexByCoord(4,8).placePlayer(player);
        	player.setC(8);
        	player.setR(4);
        }
        // left player
        if(player.getPlayerNum()==3){
            board.getVertexByCoord(0,4).placePlayer(player);
        	player.setC(0);
        	player.setR(4);
    	}
    	// right player
        if(player.getPlayerNum()==4){
            board.getVertexByCoord(8,4).placePlayer(player);
        	player.setC(0);
        	player.setR(4);        
    	}
    }

    @Override
    public String toString() {
        return "Player{" + "playerNum=" + playerNum + ", c=" + c + ", r=" + r + '}';
    }
    

    /**
     *
     * @return
     */
    public int getPlayerNum() {
         return playerNum;
    }


    /**
     *
     * @return
     */
    public int getC() {
        return c;
    }

    /**
     *
     * @return
     */
    public int getR() {
        return r;
    }
    
    /**
     *
     * @param c
     */
    public void setC(int c) {
        this.c = c;
    }

    /**
     *
     * @param r
     */
    public void setR(int r) {
        this.r = r;
    }
}
