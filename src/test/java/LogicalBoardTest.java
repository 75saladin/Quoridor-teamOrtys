import org.junit.*;
import java.util.Random;
import java.util.Set;
import static org.junit.Assert.*;

public class LogicalBoardTest {

    LogicalBoard board;

    @Before
    public void setUp() throws Exception {
        this.board = new LogicalBoard(2);
    }
    
    @Test
    public void testConstructor() throws Exception {
        assertNotNull("Board constructed null", board);
        assertEquals("Board has incorrect number of verticies", board.getVertexSet().size(), 81);
        assertEquals("Board has incorrect number of edges", board.getEdgeSet().size(), 144);
       
        int c = 0;
        int r = 0;
        for (Vertex v : board.getVertexSet()) {
            assertEquals("Vertex ["+v.c+" "+v.r+"] should have been ["+c+" "+r+"]: col mismatch", v.c, c);
            assertEquals("Vertex ["+v.c+" "+v.r+"] should have been ["+c+" "+r+"]: row mismatch", v.r, r);
            c++;        
            if(c==9){   // when you reach end of the row go to next
                c=0;
                r++;
            }
        }
        
        Vertex tlCorner = board.getVertexByCoord(0,0);
        Vertex trCorner = board.getVertexByCoord(8,0);
        Vertex blCorner = board.getVertexByCoord(0,8);
        Vertex brCorner = board.getVertexByCoord(8,8);
        Vertex topMid = board.getVertexByCoord(4,0);
        Vertex leftMid = board.getVertexByCoord(0,4);
        Vertex rightMid = board.getVertexByCoord(8,4);
        Vertex bottomMid = board.getVertexByCoord(4,8);
        Vertex tlCenter = board.getVertexByCoord(1,1);
        Vertex trCenter = board.getVertexByCoord(7,1);
        Vertex blCenter = board.getVertexByCoord(1,7);
        Vertex brCenter = board.getVertexByCoord(7,7);
        Vertex center = board.getVertexByCoord(4,4);
        
        assertEquals("Corner vertex should have 2 edges", board.board.edgesOf(tlCorner).size(), 2);
        assertEquals("Corner vertex should have 2 edges", board.board.edgesOf(trCorner).size(), 2);
        assertEquals("Corner vertex should have 2 edges", board.board.edgesOf(blCorner).size(), 2);
        assertEquals("Corner vertex should have 2 edges", board.board.edgesOf(brCorner).size(), 2);
        assertEquals("Side vertex should have 3 edges", board.board.edgesOf(topMid).size(), 3);
        assertEquals("Side vertex should have 3 edges", board.board.edgesOf(leftMid).size(), 3);
        assertEquals("Side vertex should have 3 edges", board.board.edgesOf(rightMid).size(), 3);
        assertEquals("Side vertex should have 3 edges", board.board.edgesOf(bottomMid).size(), 3);
        assertEquals("Center vertex should have 4 edges", board.board.edgesOf(tlCenter).size(), 4);
        assertEquals("Center vertex should have 4 edges", board.board.edgesOf(trCenter).size(), 4);
        assertEquals("Center vertex should have 4 edges", board.board.edgesOf(blCenter).size(), 4);
        assertEquals("Center vertex should have 4 edges", board.board.edgesOf(brCenter).size(), 4);
        assertEquals("Center vertex should have 4 edges", board.board.edgesOf(center).size(), 4);
    }
    
    @Test
    public void getVertexByCoordShouldReturnCorrectVertex() throws Exception {
        Random r = new Random();
        int randC;
        int randR;
        for (int i=0; i<10; i++) {
            randC = r.nextInt(9);
            randR = r.nextInt(9);
            Vertex v = board.getVertexByCoord(randC, randR);
            assertEquals("Vertex ["+v.c+" "+v.r+"] should have been ["+randC+" "+randR+"]: col mismatch", v.c, randC);
            assertEquals("Vertex ["+v.c+" "+v.r+"] should have been ["+randC+" "+randR+"]: row mismatch", v.r, randR);
        }
    }
    
    @Test
    public void placeHorizontalWallShouldRemoveCorrectEdges() throws Exception {
        Vertex src = board.getVertexByCoord(1,1);
        Vertex below = board.getVertexByCoord(1,2);
        Vertex right = board.getVertexByCoord(2,1);
        Vertex belowRight = board.getVertexByCoord(2,2);
        
        assertTrue(board.board.containsEdge(src, right));
        assertTrue(board.board.containsEdge(right, belowRight));
        assertTrue(board.board.containsEdge(belowRight, below));
        assertTrue(board.board.containsEdge(below, src));
        
        board.placeWall(new Player(1),"1 1 h");
        
        assertTrue(board.board.containsEdge(src, right));
        assertFalse(board.board.containsEdge(right, belowRight));
        assertTrue(board.board.containsEdge(belowRight, below));
        assertFalse(board.board.containsEdge(below, src));
    }
    
    @Ignore
    public void placeVerticalWallShouldRemoveCorrectEdges() throws Exception {
        Vertex src = board.getVertexByCoord(1,1);
        Vertex below = board.getVertexByCoord(1,2);
        Vertex right = board.getVertexByCoord(2,1);
        Vertex belowRight = board.getVertexByCoord(2,2);
        
        assertTrue(board.board.containsEdge(src, right));
        assertTrue(board.board.containsEdge(right, belowRight));
        assertTrue(board.board.containsEdge(belowRight, below));
        assertTrue(board.board.containsEdge(below, src));
        
        board.placeWall(new Player(1),"1 1 v");
        
        assertFalse(board.board.containsEdge(src, right));
        assertTrue(board.board.containsEdge(right, belowRight));
        assertFalse(board.board.containsEdge(belowRight, below));
        assertTrue(board.board.containsEdge(below, src));
    }
    
    @Test
    public void getEdgesToRemoveForHorizontalWallShouldReturnTwoCorrectEdges() throws Exception {
        Vertex src = board.getVertexByCoord(1,1);
        Vertex below = board.getVertexByCoord(1,2);
        Vertex right = board.getVertexByCoord(2,1);
        Vertex belowRight = board.getVertexByCoord(2,2);
        
        Edge srcRight = board.board.getEdge(src, right);
        Edge rightBelowRight = board.board.getEdge(right, belowRight);
        Edge belowRightBelow = board.board.getEdge(belowRight, below);
        Edge belowSrc = board.board.getEdge(below, src);
        
        Set<Edge> remove = board.getEdgesToRemove("1 1 h");
        assertEquals("There should always be two edges to remove.", remove.size(), 2);
        for (Edge e : remove) {
            assertTrue(e==belowSrc||e==rightBelowRight);
        }
    }
    
    @Test
    public void getEdgesToRemoveForVerticalWallShouldReturnTwoCorrectEdges() throws Exception {
        Vertex src = board.getVertexByCoord(1,1);
        Vertex below = board.getVertexByCoord(1,2);
        Vertex right = board.getVertexByCoord(2,1);
        Vertex belowRight = board.getVertexByCoord(2,2);
        
        Edge srcRight = board.board.getEdge(src, right);
        Edge rightBelowRight = board.board.getEdge(right, belowRight);
        Edge belowRightBelow = board.board.getEdge(belowRight, below);
        Edge belowSrc = board.board.getEdge(below, src);
        
        Set<Edge> remove = board.getEdgesToRemove("1 1 v");
        assertEquals("There should always be two edges to remove.", remove.size(), 2);
        for (Edge e : remove) {
            assertTrue(e==srcRight||e==belowRightBelow);
        }
    }
    
    @Ignore
    public void validWallAcceptsValidWalls() throws Exception {
        Random r = new Random();
        String direction;
        String wallString;
        for (int i=0; i<20; i++) {
            if (r.nextInt(2)==0)
                direction = "h";
            else
                direction = "v";  
            wallString = ""+r.nextInt(9)+" "+r.nextInt(9)+" "+direction;
            assertTrue("Wall "+wallString+" was judged invalid", board.validWall(1,wallString));
        }
    }
    
    @Ignore
    public void validWallRejectsInvalidWalls() throws Exception {
        //Wall out of bounds: <col/row> >7 or <0
        assertFalse(board.validWall(1,"-1 4 v"));
        assertFalse(board.validWall(1,"4 -1 h"));
        assertFalse(board.validWall(1,"8 4 v"));
        assertFalse(board.validWall(1,"4 8 h"));
        
        //Wall intersects another wall
        board.placeWall(new Player(1),"1 1 h");
        board.placeWall(new Player(1),"7 7 v");
	board.placeWall(new Player(1),"4 4 h");
        assertFalse(board.validWall(1,"1 1 v"));
        assertFalse(board.validWall(1,"7 7 h"));
	assertFalse(board.validWall(1,"3 5 v"));
    }
    
    @Test
    public void validWallRejectsWinBlockingWall() throws Exception {
	board.validWall(1, "0 0 h");
	board.validWall(1, "2 0 h");
	board.validWall(1, "4 0 h");
	board.validWall(1, "6 0 h");
	board.validWall(1, "7 0 v");
	
	assertFalse(board.validWall(1, "7 1 h"));
    }
    
    @Test
    public void validWallShouldDecrementPlayerCount() throws Exception {
	
	assertEquals("Player 1 should start with 10 walls", board.getPlayer(1).getWalls(), 10);
	assertEquals("Player 2 should start with 10 walls", board.getPlayer(2).getWalls(), 10);
	board.validWall(1, "0 0 h");
	board.validWall(1, "2 0 h");
	board.validWall(2, "4 0 h");
	board.validWall(1, "6 0 h");
	board.validWall(1, "7 0 v");
	
	assertEquals("Player 1 should start with 10 walls", board.getPlayer(1).getWalls(), 6);
	assertEquals("Player 2 should start with 10 walls", board.getPlayer(2).getWalls(), 9);
	
	board.validWall(1, "0 4 h");
	board.validWall(1, "2 4 h");
	board.validWall(1, "4 4 h");
	board.validWall(1, "6 4 h");
	board.validWall(1, "2 6 h");
	//Player 1 should have no walls left
	assertFalse(board.validWall(1, "4 6 h"));
    }
    
    @Ignore
    public void validMovesAcceptsValidMoves() throws Exception {
	//Moves in a circle, testing each direction
	assertTrue(board.validMove(1, "4 1"));
	assertTrue(board.validMove(1, "3 1"));
	assertTrue(board.validMove(1, "4 1"));
	assertTrue(board.validMove(1, "4 0"));	
    }
    
    @Ignore
    public void validMovesRejectsInvalidMoves() throws Exception {
	//Moves too far
	assertFalse(board.validMove(1, "4 2"));
	assertFalse(board.validMove(1, "6 0"));
	assertFalse(board.validMove(1, "2 0"));
	
	//Tries to jump over a horizontal wall then a vertical wall
	board.validWall(1, "3 0 h");
	assertFalse(board.validMove(1, "4 1"));
	board.validWall(1, "4 0 v");
	assertFalse(board.validMove(1, "5 0"));
	
	//Tries to leave the board
	assertFalse(board.validMove(1, "4 -1"));
	for (int i=4; i<9; i++)
	    board.validMove(1, i+" 0");
	assertFalse(board.validMove(1, "9 0"));
    }    
    
    @Ignore
    public void validMovesRejectsMoreInvalidMoves() throws Exception {
	//Move to current location
	assertFalse(board.validMove(1, "4 0"));
	
	//Can't move onto player 2
	for (int i=0; i<8; i++)
	    board.validMove(1, "4 "+i);
	assertFalse(board.validMove(1, "4 8"));
    }
    
    @Ignore
    public void jumping() throws Exception {
	Player one = board.getPlayer(1);
	Player two = board.getPlayer(2);
	board.makeMove(one, "4 4");
	board.makeMove(two, "4 5");
	assertTrue(board.validMove(1, "4 6"));
    }
    
    @Test
    public void jumpingOverAWallIsInvalid() throws Exception {
	Player one = board.getPlayer(1);
	Player two = board.getPlayer(2);
	board.makeMove(one, "4 4");
	board.makeMove(two, "4 5");
	board.validWall(1, "4 4 h");
	assertFalse(board.validMove(1, "4 6"));
    }
    
    @Ignore
    public void theMegaJump() throws Exception {
	board = new LogicalBoard(4);
	Player one = board.getPlayer(1);
	Player two = board.getPlayer(2);
	Player three = board.getPlayer(3);
	Player four = board.getPlayer(4);
	
	board.makeMove(one, "4 3");
	board.makeMove(two, "4 4");
	board.makeMove(three, "4 5");
	board.makeMove(four, "4 6");
	
	assertTrue(board.validMove(1, "4 7"));
	
    }
    
    @Ignore
    public void megaJumpingOverAWallIsInvalid() throws Exception {
	board = new LogicalBoard(4);
	Player one = board.getPlayer(1);
	Player two = board.getPlayer(2);
	Player three = board.getPlayer(3);
	Player four = board.getPlayer(4);
	
	board.makeMove(one, "4 3");
	board.makeMove(two, "4 4");
	board.makeMove(three, "4 5");
	board.makeMove(four, "4 6");
	board.validWall(1, "4 4 h");
	
	assertTrue(board.validMove(1, "4 7"));
	
    }
}
