//import Player; // Not necessary - The file is in the same folder.
//import Wall; // Not necessary - the file is in the same folder.
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* Notes:
   - On updating a player: When one player's valid moves are updated, do all 
     player's moves need to be recalculated? Consider the situation of moving
     to or away from a spot adjacent to another player; because of the 
     possibility of jumping, the other player's valid moves are no longer 
     reliable. Recalculating all player's moves is an easy but costly solution.
     Another solution could be to check whether they moved into another player's
     valid move OR away from another player's valid move. However, that would 
     require knowing the old location, and update() doesn't care about that the
     way it is right now. Also, 4-player compounds the problem when you consider
     double-jumping.
   - I changed the fields. Everything refers to player number instead of an
     instance of the Player class. However, that means, in its current state,
     we can't access the current location of a player. We could add another
     field to associate player number with Player. Or something.
*/
       
public final class ValidMoves {
    
    public ArrayList<ArrayList<int[]>> validM; //List of lists of valid moves. index is player number
    public ArrayList<String> walls; //list of user-placed walls.
    public ArrayList<String> validW; //list of placable walls.
    
    /**
     * Constructs the database.
     * @param fourP	Whether or not this is a 4-player game.
     */
    public ValidMoves(boolean fourP) {
	validM = new ArrayList<>();
	walls = new ArrayList<>();
	validW = new ArrayList<>();
	buildValidWalls();
	buildValidMoves(fourP);
    }
    
    /**
     * Recalculates the valid moves for the player number given.
     */
    public void update(int player) {
	ArrayList<int[]> newMoves = new ArrayList<>();
	int c = 0; //PLACEHOLDER: NEED TO GET THE PLAYER'S LOCATION
	int r = 0; //PLACEHOLDER: NEED TO GET THE PLAYER'S LOCATION
	
	//add the move above them if there isn't a blocking wall
	if (!walls.contains("" + (c-1) + " " + (r-1) + " " + "h")&&
	    !walls.contains("" + c + " " + (r-1) + " " + "h")) 
	    newMoves.add(new int[]{c, r-1});
	//add the move below them if there isn't a blocking wall
	if (!walls.contains("" + (c-1) + " " + r + " " + "h")&&
	    !walls.contains("" + c + " " + r + " " + "h")) 
	    newMoves.add(new int[]{c, r+1});
	//add the move left of them if there isn't a blocking wall
	if (!walls.contains("" + (c-1) + " " + r + " " + "v")&&
	    !walls.contains("" + (c-1) + " " + (r-1) + " " + "v")) 
	    newMoves.add(new int[]{c-1, r});
	//add the move right of them if there isn't a blocking wall
	if (!walls.contains("" + c + " " + r + " " + "h")&&
	    !walls.contains("" + c + " " + (r-1) + " " + "h")) 
	    newMoves.add(new int[]{c+1, r});
	
	//ADD VALID MOVES OF JUMPABLE PAWNS
    }
    
    /**
    * Adds a wall and removes from validW walls that conflict with the new wall.
    * @param	wall	The new wall as a Wall.
    */
    public void update(Wall wall) {
	walls.add(wall.getProperties()); //adds new wall to list of user-placed walls.
	walls.remove("" + wall.getCPos() + " " + wall.getRPos() + " " + wall.getDirection());
	walls.remove("" + wall.getCPos() + " " + wall.getRPos() + " " + wall.getOppositeDirection());
	if (wall.getDirection().equals("h")) {
	    walls.remove("" + (wall.getCPos()-1) +
			 " " + wall.getRPos() + 
			 " " + wall.getDirection());
	    walls.remove("" + (wall.getCPos()+1) +
			 " " + wall.getRPos() +
			 " " + wall.getDirection());
	} else {
	    walls.remove("" + wall.getCPos() + 
			 " " + (wall.getRPos()-1) + 
			 " " + wall.getDirection());
	    walls.remove("" + wall.getCPos() + 
			 " " + (wall.getRPos()+1) + 
			 " " + wall.getDirection());
	}
	
    }
    
    /**
     * Checks whether or not a given move destination is a valid one for the 
     * given player. ie, checks whether validM's ArrayList at index <player>
     * contains the given move in the form int[]{c, r}.
     * @param player	The player number to be considered
     * @param move	The move to be considered. In the form that the parser 
     *                  creates: a String of col + " " + row.
     */
    public boolean validateMove(int player, String move) {
	int c = move.charAt(0);
	int r = move.charAt(2);
	if (validM.get(player).contains(new int[]{c,r}))
	    return true;
	return false;
    }
    
    /**
     * Checks whether or not a given wall is a valid one for this board. ie,
     * checks if that wall exists in validW.
     * @param wall	The wall to be considered. In the form that the parser
     *			creates: A String of col + " " + row + " " + direction
     */
    public boolean validateWall(String wall) {
	if (validW.contains(wall))
	    return true;
	return false;
    }
       
    /**
     * Populates validW with every possible wall. To be called once in the 
     *   constructor.
     */
    public void buildValidWalls() {
	for (int x=0; x<8; x++) {
	    for (int y=0; y<8; y++) {
		// I didn't know why you wanted a String in validW, but
		// I didn't change it, I just added a toString call here
		// so that it could build. 
		validW.add(new Wall(x, y, "h").toString()); 
		validW.add(new Wall(x, y, "v").toString());
	    }
	}
    }
    
    /** Populates validM with initial possible moves. To be called once in the
     *  constructor.
     * @param fourP	Whether or not this game is 4-player
     */
    public void buildValidMoves(boolean fourP) {
	ArrayList<int[]> p1 = new ArrayList<>();
	ArrayList<int[]> p2 = new ArrayList<>();
	p1.add(new int[]{3, 0});
	p1.add(new int[]{4, 1});
	p1.add(new int[]{5, 0});
	p2.add(new int[]{3, 8});
	p2.add(new int[]{4, 7});
	p2.add(new int[]{5, 8});
	
	validM.add(p1);
	validM.add(p2);
	
	if(fourP) {
	    ArrayList<int[]> p3 = new ArrayList<>();
	    ArrayList<int[]> p4 = new ArrayList<>();
	    p3.add(new int[]{0, 3});
	    p3.add(new int[]{1, 4});
	    p3.add(new int[]{0, 5});
	    p4.add(new int[]{8, 3});
	    p4.add(new int[]{7, 4});
	    p4.add(new int[]{6, 5});
	    
	    validM.add(p3);
	    validM.add(p4);
	}
    }
}
