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

    
    public String id;	//Three-char tag
    public int player;	//Player number (1-4)
    public String name = "Player" + player;	//Name of particular player

    
   
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
    
}
