

/**
 *
 * @author craig
 */
public class Player{
    
    private int playerNum;
    private int port = 0;
    private String machine = "";
    private String ID = "";
    private int c;
    private int r;

    /**
     * Player Constructor
     * @param ID
     * @param machineName
     * @param port
     * @param playerNum
     */
    public Player(String ID, String machineName, int port, int playerNum){
        this.port = port;
        this.playerNum = playerNum;
        this.machine = machineName;
        this.ID = ID;     
    }
    
    /**
     *
     * @return
     */
    public String getName(){
        return ID;
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
    public int getPort() {
        return port;
    }

    /**
     *
     * @return
     */
    public String getMachine() {
        return machine;
    }

    /**
     *
     * @return
     */
    public String getID() {
        return ID;
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
