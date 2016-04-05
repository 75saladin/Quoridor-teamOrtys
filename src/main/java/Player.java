

/**
 *
 * @author craig
 */
public class Player {
    
    private int c;
    private int r;
    private int walls;
    
    

    /**
     * Player Constructor
     * player number will determine starting position
     * board will be placing it on the board logically
     *
     * @param playerNum number of this player
     */
    public Player(int playerNum){
            if(playerNum==1){
                this.c = 0;
                this.r = 4;
            }if(playerNum==2){
                this.c = 8;
                this.r = 4;
            }if(playerNum==3){
                this.c = 4;
                this.r = 0;
            }if(playerNum==4){
                this.c = 4;
                this.r = 8;
            }
    }
  

    public int getC() {
        return c;
    }

    public int getR() {
        return r;
    }
    
    public void setC(int c) {
        this.c = c;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getWalls() {
        return walls;
    }
    
    public void decrementWall(){
        this.walls = this.walls - 1;
    }
    
    public void setWalls(int walls) {
        this.walls = walls;
    }
    
    /* NOT SURE IF THIS DOES THE SAME THING AS THAT \/
    @Override public boolean equals(Object other){
        if (other == this) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        Player player = (Player)other;
        return (r == player.r && c == player.c && walls == player.walls);
    */
    public boolean equals(Player player) {
        return (r == player.r && c == player.c && walls == player.walls);
    }

}
