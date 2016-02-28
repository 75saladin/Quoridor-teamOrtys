import Player;
import Wall;
import java.util.ArrayList;
import java.util.HashMap;

public class ValidMoves {
    
    public Map<String, ArrayList<int[2]>> validM; //map of players; list of possible moves
    public ArrayList<String> walls; //list of user-placed walls.
    public ArrayList<String> validW; //list of placable walls.
    
    /**
     * Constructs the database. (2-player)
     */
    public ValidMoves() {
	validM = new HashMap<String, int[2]>();
	walls = new ArrayList<String>();
	validW = new ArrayList<String>();
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
	
	
    }
    
    public boolean validateWall(String wall) {
	
    }
    
    /**
     * Adds a wall and removes from validW walls that conflict with the new wall.
     * @param	wall	The new wall as a Wall.
     */
    public void update(Wall wall) {
	walls.add(wall.properties); //adds new wall to list of user-placed walls.
	walls.remove("" + wall.c + " " + wall.r + " " + wall.direction);
	walls.remove("" + wall.c + " " + wall.r + " " + wall.getOppositeDirection());
	if (wall.direction.equals("h")) {
	    walls.remove("" + wall.c-1 + " " + wall.r + " " + wall.direction);
	    walls.remove("" + wall.c+1 + " " + wall.r + " " + wall.direction);
	} else {
	    walls.remove("" + wall.c + " " + wall.r-1 + " " + wall.direction);
	    walls.remove("" + wall.c + " " + wall.r+1 + " " + wall.direction);
	}
	
    }
    
    /**
     * Populates validW with every possible wall. To be called once in the 
     *   constructor.
     */
    public void buildValidWalls() {
	for (int x=0; x<8; x++) {
	    for (int y=0; y<8; y++) {
		validW.add(new Wall(x, y, "h"));
		validW.add(new Wall(x, y, "v"));
	    }
	}
    }
    
    //Starting position's valid moves (for 2 player)
    //Need another version for 4-player
    public void buildValidMoves() {
	
    }
}