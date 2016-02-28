import Player;
import Wall;
import java.util.ArrayList;
import java.util.HashMap;

public class ValidMoves {
    
    public Map<String, int[2]> validP;
    public ArrayList<Wall> walls;
    public ArrayList<Wall> validW;
    
    /**
     * Constructs the database.
     */
    public ValidMoves() {
	validP = new HashMap<String, int[2]>();
	walls = new ArrayList<Wall>();
	validW = new ArrayList<Wall>();
    }
    
    public void update(Player player) {
	validP.put(player.name, [player.x+1, player.y]); //right
	validP.put(player.name, [player.x-1, player.y]); //left
	validP.put(player.name, [player.x, player.y+1]); //up
	validP.put(player.name, [player.x, player.y-1]); //down
    }
    
    public void update(Wall wall) {
	walls.add(wall);
    }
    
    public void buildValidMoves() {
	
    }
}