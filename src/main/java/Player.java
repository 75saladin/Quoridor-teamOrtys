

/**
 *
 * @author craig
 */
public class Player{
    
    private int playerNum;
    private int c;
    private int r;
    private int walls;
    
    

    /**
     * Player Constructor
	 * player number will determine starting position
	 * board will be placing it on the board logically
     */
    public Player(int playerNum){
            setPlayerNum(playerNum);
            if(playerNum==1){
                setC(0);
                setR(5);
            }if(playerNum==2){
                setC(8);
                setR(5);
            }if(playerNum==3){
                setC(5);
                setR(0);
            }if(playerNum==4){
                setC(5);
                setR(8);
            }
    }
  
    public int getPlayerNum() {
         return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
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
}
