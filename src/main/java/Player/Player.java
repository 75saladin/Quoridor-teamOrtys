/*
 * This is the object that will be initialized when gathering information
 * through the network server that communicates with others
 * 
 */

/**
 *
 * @author 
 */
public class Player {

    
    public String id;
    public int player;
    public String name = "Player" + player;

    
   
    /**Player Constructor
     * 
     * @param id - Identifier for specific player
     * @param name - given name from console
     * @param player - player # for position on board
     */
    public Player(String id, String name, int player) {
        this.id = id;
        this.name = name;
        this.player = player;
    }
    
    /**getName()
     * 
     * @return name of this player
     */
    public String getName(){
        return this.name;
    }
    
    
    /**getPlayer()
     * 
     * @return Player number of this player
     */
    public int getPlayer(){
        return this.player;
    }
    
    /**
     * 
     * @return id of this player
     */
    public String getID(){
        return this.id;
    }
    
}
