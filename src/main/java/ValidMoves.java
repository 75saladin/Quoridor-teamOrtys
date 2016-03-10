//import Player; // Not necessary - The file is in the same folder.
//import Wall; // Not necessary - the file is in the same folder.
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class ValidMoves {
    
    public Map<String, ArrayList<int[]>> validM; //map of players; list of possible moves
    public ArrayList<String> walls; //list of user-placed walls.
    public ArrayList<String> validW; //list of placable walls.
    
    /**
     * Constructs the database. (2-player)
     */
    public ValidMoves() {
	validM = new HashMap<>();
	walls = new ArrayList<>();
	validW = new ArrayList<>();
	buildValidWalls();
	buildValidMoves();
    }
    
    //Valid for all versions (2-4 players) - updates current player's valid moves    
    public void update(Player player) {
	/** REWORK AFTER BUILDING VALIDATE MOVE
	validP.put(player.name, [player.x+1, player.y]); //right
	validP.put(player.name, [player.x-1, player.y]); //left
	validP.put(player.name, [player.x, player.y+1]); //up
	validP.put(player.name, [player.x, player.y-1]); //down
	*/
    }
    
    public boolean validateMove(String move) {
	int c = move.charAt(0);
	int r = move.charAt(2);
	return false; //PLACEHOLDER RETURN - REPLACE AT SOME POINT!	
    }
    
    public boolean validateWall(String wall) {
	return false; //PLACEHOLDER RETURN - REPLACE AT SOME POINT!
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
    
    //Starting position's valid moves (for 2 player)
    //Need another version for 4-player
    //Why not just have a boolean flag in the method for 2 or 4 players?
    public void buildValidMoves() {
	
    }
}
