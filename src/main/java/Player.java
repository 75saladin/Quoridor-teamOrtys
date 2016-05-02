/**
 * A player in the game. Used by LogicalBoard.
 */
public class Player {
    
    /** Player's current column */
    private int c;
    /** Player's current row */
    private int r;
    /** Player's number of walls left */
    private int walls;
    /** Player's number in the game */
    private int playerNum;
    
    
    /**
     * Player Constructor
     * player number will determine starting position
     * board will be placing it on the board logically
     *
     * @param playerNum Number of this player
     */
    public Player(int playerNum){
            if(playerNum==1){
                this.c = 4;
                this.r = 0;
            }if(playerNum==2){
                this.c = 4;
                this.r = 8;
            }if(playerNum==3){
                this.c = 0;
                this.r = 4;
            }if(playerNum==4){
                this.c = 8;
                this.r = 4;
            }
            this.playerNum = playerNum;
    }
  
    /**
<<<<<<< Updated upstream
     * Gets the player's column number.
     * @return This player's column number
=======
     * Returns the column that the player is currently in.
     *
     * @return the current column that the player is in.
>>>>>>> Stashed changes
     */
    public int getC() {
        return c;
    }

    /**
     * Gets the player's row number.
     * @return This player's row number
     */
    public int getR() {
        return r;
    }
    
    /**
     * Sets player's current column
     * @param c Column number
     */
    public void setC(int c) {
        this.c = c;
    }

    /**
     * Sets player's current row
     * @param r Row number
     */
    public void setR(int r) {
        this.r = r;
    }

    /** 
     * Retruns player's number of walls left.
     * @return Player's number of walls left
     */
    public int getWalls() {
        return walls;
    }
    
    /**
     * This player has laid a wall! Subtracts 1 from player's walls left count.
     */
    public void decrementWall(){
        this.walls--;
    }
    
    /**
     * Turns out they didn't lay a wall! Adds 1 to player's walls left count.
     */
    public void incrementWall(){
        this.walls++;
    }
    
    /**
     * Initializes player's wall count, depending on number of players in the game.
     * @param walls Number of walls they get
     */
    public void setWalls(int walls) {
        this.walls = walls;
    }

    /**
     * Checks if the players are in the same location and have the same wallocount.
     * @return Whether or not the players have the same location and wallcount.
     */
    public boolean equals(Player player) {
        return (r == player.r && c == player.c && walls == player.walls);
    }

    /**
     * Gets a string representation.
     * @return "{c=[col], r=[row], walls=[num]}"
     */
    @Override
    public String toString() {
        return "Player{" + "c=" + c + ", r=" + r + ", walls=" + walls + '}';
    }
    
    /**
     * Gets the player's number
     * @return This player's player number.
     */
    public int getPlayerNum(){
        return playerNum;
    }
    
    /**
     * Gets whether or not the player has more than 0 walls left.
     * @return Whether or not this player has walls left.
     */
    public boolean hasWalls(){
        if(walls==0)
            return false;
        return true;
    }

}
