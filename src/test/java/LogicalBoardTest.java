
import EntireDirectory.Edge;
import EntireDirectory.LogicalBoard;
import EntireDirectory.Player;
import EntireDirectory.Vertex;
import org.junit.*;
import java.util.Random;
import java.util.Set;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import static org.junit.Assert.*;

public class LogicalBoardTest {

    LogicalBoard boardTwo;
    LogicalBoard boardFour;

    @Before
    public void setUp() throws Exception {
        this.boardTwo = new LogicalBoard(2);
        this.boardFour = new LogicalBoard(4);
    }
    
    @Test
    public void testConstructorFourPlayer() throws Exception {
        assertNotNull("Board constructed null", boardTwo);
        assertNotNull("Board constructed null", boardFour);
        assertEquals("Board Two has incorrect number of verticies", boardTwo.vertexSet().size(), 81);
        assertEquals("Board Four has incorrect number of verticies", boardFour.vertexSet().size(), 81);
        assertEquals("Board Two has incorrect number of edges", boardTwo.edgeSet().size(), 144);
        assertEquals("Board Four has incorrect number of edges", boardFour.edgeSet().size(), 144);
        assertEquals("Board Two has incorrect number of players", boardTwo.getPlayerCount(),2);
        assertEquals("Board Four has incorrect number of players", boardFour.getPlayerCount(),4);
        int c = 0;
        int r = 0;
        for (Vertex v : boardTwo.vertexSet()) {
            assertEquals("Vertex ["+v.c+" "+v.r+"] should have been ["+c+" "+r+"]: col mismatch", v.c, c);
            assertEquals("Vertex ["+v.c+" "+v.r+"] should have been ["+c+" "+r+"]: row mismatch", v.r, r);
            c++;        
            if(c==9){   // when you reach end of the row go to next
                c=0;
                r++;
            }
        }
        
        Vertex tlCorner = boardTwo.getVertexByCoord(0,0);
        Vertex trCorner = boardTwo.getVertexByCoord(8,0);
        Vertex blCorner = boardTwo.getVertexByCoord(0,8);
        Vertex brCorner = boardTwo.getVertexByCoord(8,8);
        Vertex topMid = boardTwo.getVertexByCoord(4,0);
        Vertex leftMid = boardTwo.getVertexByCoord(0,4);
        Vertex rightMid = boardTwo.getVertexByCoord(8,4);
        Vertex bottomMid = boardTwo.getVertexByCoord(4,8);
        Vertex tlCenter = boardTwo.getVertexByCoord(1,1);
        Vertex trCenter = boardTwo.getVertexByCoord(7,1);
        Vertex blCenter = boardTwo.getVertexByCoord(1,7);
        Vertex brCenter = boardTwo.getVertexByCoord(7,7);
        Vertex center = boardTwo.getVertexByCoord(4,4);
        
        assertEquals("Corner vertex should have 2 edges", boardTwo.board.edgesOf(tlCorner).size(), 2);
        assertEquals("Corner vertex should have 2 edges", boardTwo.board.edgesOf(trCorner).size(), 2);
        assertEquals("Corner vertex should have 2 edges", boardTwo.board.edgesOf(blCorner).size(), 2);
        assertEquals("Corner vertex should have 2 edges", boardTwo.board.edgesOf(brCorner).size(), 2);
        assertEquals("Side vertex should have 3 edges", boardTwo.board.edgesOf(topMid).size(), 3);
        assertEquals("Side vertex should have 3 edges", boardTwo.board.edgesOf(leftMid).size(), 3);
        assertEquals("Side vertex should have 3 edges", boardTwo.board.edgesOf(rightMid).size(), 3);
        assertEquals("Side vertex should have 3 edges", boardTwo.board.edgesOf(bottomMid).size(), 3);
        assertEquals("Center vertex should have 4 edges", boardTwo.board.edgesOf(tlCenter).size(), 4);
        assertEquals("Center vertex should have 4 edges", boardTwo.board.edgesOf(trCenter).size(), 4);
        assertEquals("Center vertex should have 4 edges", boardTwo.board.edgesOf(blCenter).size(), 4);
        assertEquals("Center vertex should have 4 edges", boardTwo.board.edgesOf(brCenter).size(), 4);
        assertEquals("Center vertex should have 4 edges", boardTwo.board.edgesOf(center).size(), 4);
	
	//from center
	Vertex right = boardTwo.getVertexByCoord(5,4);
	Vertex below = boardTwo.getVertexByCoord(4,5);
	Vertex belowRight = boardTwo.getVertexByCoord(5,5);
	
	assertTrue(boardTwo.board.containsEdge(center, right));
	assertTrue(boardTwo.board.containsEdge(right, belowRight));
	assertTrue(boardTwo.board.containsEdge(belowRight, below));
	assertTrue(boardTwo.board.containsEdge(below, center));
	assertFalse(boardTwo.board.containsEdge(center, belowRight));
	assertFalse(boardTwo.board.containsEdge(right, below));
    }
    
    
    @Test
    public void TestGraphCopy() throws Exception{
                
        UndirectedGraph<Vertex,Edge> boardCopy = new SimpleGraph<>(Edge.class);
        Graphs.addGraph(boardCopy, this.boardTwo.board);
        // builds the graph with correct C R Vertex Positions
        boardCopy = boardTwo.buildGraph(boardCopy);
        while(boardCopy.vertexSet().size()!=0){
            for(Vertex v : boardTwo.board.vertexSet())
                assertTrue(boardCopy.removeVertex(v));
        }
        
        assertFalse(boardCopy.vertexSet().size()==boardTwo.board.vertexSet().size());
        
        assertEquals("boardCopy Vertex Set should be empty",boardCopy.vertexSet().size(),0);
        assertEquals("boardTwo Vertex Set should be empty",boardTwo.vertexSet().size(),81);
        boardCopy = new SimpleGraph<>(Edge.class);
        Graphs.addGraph(boardCopy, this.boardTwo.board);
        // builds the graph with correct C R Vertex Positions
        boardCopy = boardTwo.buildGraph(boardCopy);
        while(boardCopy.edgeSet().size()!=0){
            for(Edge e : boardTwo.board.edgeSet())
                assertTrue(boardCopy.removeEdge(e));
        }
        assertEquals("boardCopy Edge Set should be empty",boardCopy.edgeSet().size(),0);
        assertEquals("boardTwo Edge Set should be empty",boardTwo.edgeSet().size(),144);
    }
    
    @Test
    public void getVertexByCoordShouldReturnCorrectVertex() throws Exception {
        Random r = new Random();
        int randC;
        int randR;
        for (int i=0; i<10; i++) {
            randC = r.nextInt(9);
            randR = r.nextInt(9);
            Vertex v = boardTwo.getVertexByCoord(randC, randR);
            assertEquals("Vertex ["+v.c+" "+v.r+"] should have been ["+randC+" "+randR+"]: col mismatch", v.c, randC);
            assertEquals("Vertex ["+v.c+" "+v.r+"] should have been ["+randC+" "+randR+"]: row mismatch", v.r, randR);
        }
    }
    
    @Test
    public void placeWallShouldRemoveCorrectEdges() throws Exception {
        Random r = new Random();
        int randC;
        int randR;
        String dir;
        String placedWalls = "";
    
        for (int i=0; i<20; i++) {
            randC = r.nextInt(8);
            randR = r.nextInt(8);
            if (r.nextInt(2)==0)
                dir = "v";
            else
                dir = "h";
                
            String wall = ""+randC+" "+randR+" "+dir;
            
            Vertex src = boardTwo.getVertexByCoord(randC,randR);
            Vertex below = boardTwo.getVertexByCoord(randC,randR+1);
            Vertex right = boardTwo.getVertexByCoord(randC+1,randR);
            Vertex belowRight = boardTwo.getVertexByCoord(randC+1,randR+1);
        
            assertTrue("Iteration "+i+" with wall: "+wall+" after successfully placing:"+ placedWalls, boardTwo.board.containsEdge(src, right));
            assertTrue("Iteration "+i+" with wall: "+wall+" after successfully placing:"+ placedWalls, boardTwo.board.containsEdge(right, belowRight));
            assertTrue("Iteration "+i+" with wall: "+wall+" after successfully placing:"+ placedWalls, boardTwo.board.containsEdge(belowRight, below));
            assertTrue("Iteration "+i+" with wall: "+wall+" after successfully placing:"+ placedWalls, boardTwo.board.containsEdge(below, src));
        
            boardTwo.checkValid(1, wall);
            
            if (dir.equals("h")) {
                assertTrue("Iteration "+i+" with wall: "+wall+" after successfully placing:"+ placedWalls, boardTwo.board.containsEdge(src, right));
                assertFalse("Iteration "+i+" with wall: "+wall+" after successfully placing:"+ placedWalls, boardTwo.board.containsEdge(right, belowRight));
                assertTrue("Iteration "+i+" with wall: "+wall+" after successfully placing:"+ placedWalls, boardTwo.board.containsEdge(belowRight, below));
                assertFalse("Iteration "+i+" with wall: "+wall+" after successfully placing:"+ placedWalls, boardTwo.board.containsEdge(below, src));
            } else {
                assertFalse("Iteration "+i+" with wall: "+wall+" after successfully placing:"+ placedWalls, boardTwo.board.containsEdge(src, right));
                assertTrue("Iteration "+i+" with wall: "+wall+" after successfully placing:"+ placedWalls, boardTwo.board.containsEdge(right, belowRight));
                assertFalse("Iteration "+i+" with wall: "+wall+" after successfully placing:"+ placedWalls, boardTwo.board.containsEdge(belowRight, below));
                assertTrue("Iteration "+i+" with wall: "+wall+" after successfully placing:"+ placedWalls, boardTwo.board.containsEdge(below, src));
            }
            
            placedWalls += " " + wall + ",";
            boardTwo.removeWall(1,wall);
        }
    }
 
    @Test
    public void getEdgesToRemoveShouldReturnTwoCorrectEdges() throws Exception {
        Random r = new Random();
        int randC;
        int randR;
        String dir;
    
        for (int i=0; i<20; i++) {
            randC = r.nextInt(8);
            randR = r.nextInt(8);
            if (r.nextInt(2)==0)
                dir = "v";
            else
                dir = "h";
                
            String wall = ""+randC+" "+randR+" "+dir;
            
            Vertex src = boardTwo.getVertexByCoord(randC,randR);
            Vertex below = boardTwo.getVertexByCoord(randC,randR+1);
            Vertex right = boardTwo.getVertexByCoord(randC+1,randR);
            Vertex belowRight = boardTwo.getVertexByCoord(randC+1,randR+1);
            
            //Edges in question for horizontal wall:
            Edge rightBelowRight = boardTwo.board.getEdge(right, belowRight);
            Edge belowSrc = boardTwo.board.getEdge(below, src);
            //Edges in question for vertical wall:
            Edge srcRight = boardTwo.board.getEdge(src, right);
            Edge belowRightBelow = boardTwo.board.getEdge(belowRight, below);

            Set<Edge> remove = boardTwo.getEdgesToRemove(wall);
            assertEquals("There should always be two edges to remove.", remove.size(), 2);
            for (Edge e : remove) {
                if (dir.equals("h"))
                    assertTrue(e==belowSrc||e==rightBelowRight);
                else
                    assertTrue(e==srcRight||e==belowRightBelow);
            }
        }
    }
    
    @Test
    public void getEdgesToRemoveShouldReturnNoEdgesForAnAlreadyPlacedWall() throws Exception {
	Random rand = new Random();
        String direction;
        String wallString;
        for (int i=0; i<20; i++) {
            if (rand.nextInt(2)==0)
                direction = "h";
            else
                direction = "v";  
            wallString = ""+rand.nextInt(8)+" "+rand.nextInt(8)+" "+direction;
	    
            assertTrue(boardTwo.checkValid(1,wallString));
	    assertEquals(0,boardTwo.getEdgesToRemove(wallString).size());
	    boardTwo.removeWall(1,wallString);
	}
    }
    
    @Test
    public void checkValidAcceptsValidWalls() throws Exception {
        Random rand = new Random();
        String direction;
        String wallString;
        for (int i=0; i<20; i++) {
            if (rand.nextInt(2)==0)
                direction = "h";
            else
                direction = "v";  
            wallString = ""+rand.nextInt(8)+" "+rand.nextInt(8)+" "+direction;
            assertTrue("Wall "+wallString+" was judged invalid on iteration "+i, boardTwo.checkValid(1,wallString));
	    boardTwo.removeWall(1,wallString);
        }
        
        //testing filling board with horizontal walls (except rightmost column)
        //Cannot test that
        // you run out of walls.....
        for (int c=0; c<8; c+=2) {
            for (int r=0; r<8; r++) {
                wallString = ""+c+" "+r+" "+"h";
                assertTrue("Wall "+wallString+" was judged invalid I: ", boardTwo.checkValid(1,wallString));
                boardTwo.removeWall(1, wallString);
            }
        }
        
        this.setUp(); //freshen up that board
        
        //testing filling board with vertical walls (except bottommost row)
        for (int r=0; r<8; r+=2) {
            for (int c=0; c<8; c++) {
                wallString = ""+c+" "+r+" "+"v";
                assertTrue("Wall "+wallString+" was judged invalid", boardTwo.checkValid(1,wallString));
                boardTwo.removeWall(1, wallString);
            }
        }
    }
    
    @Test
    public void checkValidRejectsIncheckValids() throws Exception {
	//Wall out of bounds: <col/row> >7 or <0
	assertFalse(boardTwo.checkValid(1,"-1 4 v"));
	assertFalse(boardTwo.checkValid(1,"4 -1 h"));
	assertFalse(boardTwo.checkValid(1,"8 4 v"));
	assertFalse(boardTwo.checkValid(1,"4 8 h"));

	Random rand = new Random();
	int c;
	int r;
        String dir;
        String wallString;
	String badWall;
	
	//Wall overlaps another wall (ie, one half of the wall is the same as 
	//one half of the other wall)
        for (int i=0; i<20; i++) {
	    //nothing in last 2 cols and rows; need to be able to overlap
	    c = rand.nextInt(7);
	    r = rand.nextInt(7);
            if (rand.nextInt(2)==0) {
                dir = "h";
	        badWall = ""+(c+1)+" "+r+" "+dir;
	    } else {
		dir = "v";
                badWall = ""+c+" "+(r+1)+" "+dir;
	    }
	    
	    wallString = ""+c+" "+r+" "+dir;
	    
	    assertEquals("Iteration: " +i,144,boardTwo.edgeSet().size());
	    assertTrue(boardTwo.checkValid(1, wallString));
	    assertEquals(boardTwo.edgeSet().size(), 142);
	    assertFalse(boardTwo.checkValid(1, badWall));
	    boardTwo.removeWall(1,wallString);
            
	}
	
	
	String opDir;
	
        //Wall intersects (ie, crisscross) another wall
        for (int i=0; i<20; i++) {
	    c = rand.nextInt(8);
	    r = rand.nextInt(8);
            if (rand.nextInt(2)==0) {
		dir = "h";
		opDir = "v";
	    } else {
		dir = "v";
		opDir = "h";
	    }
	    wallString = ""+c+" "+r+" "+dir;
	    badWall = ""+c+" "+r+" "+opDir;
	    
	    assertTrue("Failed on iteration "+i+" on good wall "+wallString+" and bad wall "+badWall, 
		       boardTwo.checkValid(1, wallString));
	    assertFalse("Failed on iteration "+i+" on good wall "+wallString+" and bad wall "+badWall, 
			boardTwo.checkValid(1, badWall));
	    boardTwo.removeWall(1,wallString);
	}

    }
    
    @Test
    public void checkValidRejectsWinBlockingWall() throws Exception {
	boardTwo.checkValid(1, "0 0 h");
	boardTwo.checkValid(1, "2 0 h");
	boardTwo.checkValid(1, "4 0 h");
	boardTwo.checkValid(1, "6 0 h");
	assertFalse(boardTwo.checkValid(1, "7 0 v"));
	
	assertTrue(boardTwo.checkValid(1, "7 1 h"));
    }
    
    @Test
    public void checkValidShouldDecrementPlayerWallCount2Players() throws Exception {
	assertEquals("Player 1 should start with 10 walls", boardTwo.getPlayer(1).getWalls(), 10);
	assertEquals("Player 2 should start with 10 walls", boardTwo.getPlayer(2).getWalls(), 10);
	assertTrue(boardTwo.checkValid(1, "0 0 h"));
	assertTrue(boardTwo.checkValid(1, "2 0 h"));
	assertTrue(boardTwo.checkValid(2, "4 0 h"));
	assertTrue(boardTwo.checkValid(1, "6 0 h"));
	assertTrue(boardTwo.checkValid(1, "7 2 v"));// will not allow wall placed?
	
	assertEquals("Player 1 should have 6 walls left", boardTwo.getPlayer(1).getWalls(), 6);
	assertEquals("Player 2 should have 9 walls left", boardTwo.getPlayer(2).getWalls(), 9);
	
	boardTwo.checkValid(1, "0 4 h");
	boardTwo.checkValid(1, "2 4 h");
	boardTwo.checkValid(1, "4 4 h");
	boardTwo.checkValid(1, "6 4 h");
	boardTwo.checkValid(1, "2 6 h");
	boardTwo.checkValid(1, "4 6 h");

	assertEquals("Player 1 should have no walls left", boardTwo.getPlayer(1).getWalls(), 0);
	assertFalse(boardTwo.checkValid(1, "4 6 h"));
    }
    
    @Test
    public void checkValidsAcceptsValidMoves() throws Exception {
	//Moves in a circle, testing each direction
        assertTrue(boardTwo.getVertexByCoord(4,0).isPlayerHere());
        Player one = boardTwo.getPlayer(1);
        assertEquals(boardTwo.getPlayerNum(one),1);
        assertTrue(boardTwo.getVertexByCoord(4,8).isPlayerHere());
	assertTrue(boardTwo.checkValid(1, "4 1"));  
        assertEquals(boardTwo.getPlayer(1).getR(),1);
        assertEquals(boardTwo.getPlayer(1).getC(),4);
        assertFalse(boardTwo.getVertexByCoord(4,0).isPlayerHere());
	assertTrue(boardTwo.checkValid(1, "3 1"));
        assertEquals(boardTwo.getPlayer(1).getR(),1);
        assertEquals(boardTwo.getPlayer(1).getC(),3);
	assertTrue(boardTwo.checkValid(1, "4 1"));
        assertEquals(boardTwo.getPlayer(1).getR(),1);
        assertEquals(boardTwo.getPlayer(1).getC(),4);
        assertFalse(boardTwo.getVertexByCoord(4,0).isPlayerHere());
	assertTrue(boardTwo.checkValid(1, "4 0"));	
        assertEquals(boardTwo.getPlayer(1).getR(),0);
        assertEquals(boardTwo.getPlayer(1).getC(),4);
    }
    
    @Test
    public void checkValidsRejectsIncheckValids() throws Exception {
	//Moves too far
	assertFalse(boardTwo.checkValid(1, "4 2"));
	assertFalse(boardTwo.checkValid(1, "6 0"));
	assertFalse("should not be able to move to "+boardTwo.getVertexByCoord(2, 0).toString(),
                    boardTwo.checkValid(1, "2 0"));
	
	//Tries to jump over a horizontal wall then a vertical wall
	assertTrue(boardTwo.checkValid(1, "3 0 h"));
	assertFalse(boardTwo.checkValid(1, "4 1"));
	assertTrue(boardTwo.checkValid(1, "4 0 v"));
	assertFalse(boardTwo.checkValid(1, "5 0"));
	
	//Tries to leave the board
	assertFalse(boardTwo.checkValid(1, "4 -1"));
	for (int i=4; i<9; i++)
	    boardTwo.checkValid(1, i+" 0");
	assertFalse(boardTwo.checkValid(1, "9 0"));
    }    
    
    @Test
    public void checkValidsRejectsMoreIncheckValids() throws Exception {
	//Move to current location
	assertFalse(boardTwo.checkValid(1, "4 0"));
	
	//Can't move onto player 2
	for (int i=0; i<8; i++)
	    boardTwo.checkValid(1, "4 "+i);
	assertFalse(boardTwo.checkValid(1, "4 8"));
    }
    
    @Test
    public void twoPlayerJump() throws Exception {
	boardTwo.makeMove(1, "4 4");
	boardTwo.makeMove(2, "4 5");
        assertTrue(boardTwo.getVertexByCoord(4, 5).here);
        assertTrue(boardTwo.getVertexByCoord(4, 4).here);
	assertTrue(boardTwo.checkValid(1, "4 6"));
	boardTwo.makeMove(1, "4 4");
	assertTrue(boardTwo.checkValid(1, "5 5"));
	boardTwo.makeMove(1, "4 4");
	assertTrue(boardTwo.checkValid(1, "3 5"));
	
	//Corner case: only one valid jump
	boardTwo.makeMove(1, "0 0");
        boardTwo.makeMove(2, "0 1");
        assertTrue(boardTwo.checkValid(2, "1 0"));
    }
    
    @Test
    public void twoPlayerJumpWithWalls() throws Exception {
	boardTwo.makeMove(1, "4 4");
	boardTwo.makeMove(2, "4 5");
	
	//wall between the players
	assertTrue(boardTwo.checkValid(1, "4 4 h"));
        assertFalse(boardTwo.checkValid(1, "4 6"));
        assertFalse(boardTwo.checkValid(1, "5 5"));
        assertFalse(boardTwo.checkValid(1, "3 5"));
        boardTwo.removeWall(1, "4 4 h");

	//walls around jumpee except one
	assertTrue(boardTwo.checkValid(1, "4 4 v"));
	assertTrue(boardTwo.checkValid(1, "3 4 v"));
        assertFalse(boardTwo.checkValid(1, "5 5"));
        assertFalse(boardTwo.checkValid(1, "3 5"));
        assertTrue(boardTwo.checkValid(1, "4 6"));
        boardTwo.makeMove(1, "4 4");
	
	//walls all around jumpee
	assertTrue(boardTwo.checkValid(2, "4 5 h"));
	assertFalse(boardTwo.checkValid(1, "5 5"));
        assertFalse(boardTwo.checkValid(1, "3 5"));
        assertFalse(boardTwo.checkValid(1, "4 6"));
    }
    
    @Test
    public void fourPlayerJump() throws Exception {
	//ASCII diagrams: top left is always "4 3"
	
	// O
	// O
	// O
	// O
	boardFour.makeMove(1, "4 3");
	boardFour.makeMove(2, "4 4");
	boardFour.makeMove(3, "4 5");
	boardFour.makeMove(4, "4 6");
	assertTrue(boardFour.validMove(1, "3 3"));
	assertTrue(boardFour.validMove(1, "3 4"));
	assertTrue(boardFour.validMove(1, "3 5"));
	assertTrue(boardFour.validMove(1, "3 6"));
	assertTrue(boardFour.validMove(1, "4 7"));
	assertTrue(boardFour.validMove(1, "5 6"));
	assertTrue(boardFour.validMove(1, "5 5"));
	assertTrue(boardFour.validMove(1, "5 4"));
        assertTrue(boardFour.validMove(1, "5 3"));
	
        // OO
        // O
        // O
	boardFour.makeMove(1, "4 3");
        boardFour.makeMove(2, "5 3");
        boardFour.makeMove(3, "4 4");
        boardFour.makeMove(4, "4 5");
        assertTrue(boardFour.validMove(1, "5 2"));
        assertTrue(boardFour.validMove(1, "6 3"));
        assertTrue(boardFour.validMove(1, "5 4"));
        assertTrue(boardFour.validMove(1, "5 5"));
        assertTrue(boardFour.validMove(1, "4 6"));
        assertTrue(boardFour.validMove(1, "3 5"));
        assertTrue(boardFour.validMove(1, "3 4"));

	// O
        // OO
        //  O
	boardFour.makeMove(1, "4 3");
        boardFour.makeMove(2, "4 4");
        boardFour.makeMove(3, "5 4");
        boardFour.makeMove(4, "5 5");
        assertTrue(boardFour.validMove(1, "5 3"));
        assertTrue(boardFour.validMove(1, "6 4"));
        assertTrue(boardFour.validMove(1, "6 5"));
        assertTrue(boardFour.validMove(1, "5 6"));
        assertTrue(boardFour.validMove(1, "4 5"));
        assertTrue(boardFour.validMove(1, "3 4"));
	
	// OOO
	//  O
	boardFour.makeMove(1, "4 3");
        boardFour.makeMove(2, "5 3");
        boardFour.makeMove(3, "6 3");
        boardFour.makeMove(4, "5 4");
        assertTrue(boardFour.validMove(1, "5 2"));
        assertTrue(boardFour.validMove(1, "6 2"));
        assertTrue(boardFour.validMove(1, "7 3"));
        assertTrue(boardFour.validMove(1, "6 4"));
        assertTrue(boardFour.validMove(1, "5 5"));
        assertTrue(boardFour.validMove(1, "4 4"));        
	
	// OO
	// OO
	boardFour.makeMove(1, "4 3");
        boardFour.makeMove(2, "5 3");
        boardFour.makeMove(3, "4 4");
        boardFour.makeMove(4, "5 4");
        assertTrue(boardFour.validMove(1, "5 2"));
        assertTrue(boardFour.validMove(1, "6 3"));
        assertTrue(boardFour.validMove(1, "6 4"));
        assertTrue(boardFour.validMove(1, "5 5"));
        assertTrue(boardFour.validMove(1, "4 5"));
        assertTrue(boardFour.validMove(1, "3 4"));
    }
    
    @Test
    public void fourPlayerJumpWithWalls() throws Exception {
	//ASCII diagrams: top left is always "4 3"
	
	assertTrue(boardFour.checkValid(1, "4 4 v"));
	        
        // O
        // O
        // O
        // O
        boardFour.makeMove(1, "4 3");
        boardFour.makeMove(2, "4 4");
        boardFour.makeMove(3, "4 5");
        boardFour.makeMove(4, "4 6");
        Set<Vertex> validMoves1 = boardFour.getValidMoves(1);
        Set<Vertex> validMoves2 =boardFour.getValidMoves(1);
        Set<Vertex> validMoves3 =boardFour.getValidMoves(1);
        Set<Vertex> validMoves4 =boardFour.getValidMoves(1);
        assertTrue(boardFour.validMove(1, "3 3"));
        assertTrue(boardFour.validMove(1, "3 4"));
        assertTrue(boardFour.validMove(1, "3 5"));
        assertTrue(boardFour.validMove(1, "3 6"));
        assertTrue(boardFour.validMove(1, "4 7"));
        assertTrue(boardFour.validMove(1, "5 6"));
        assertFalse(boardFour.validMove(1, "5 5"));
        assertFalse(boardFour.validMove(1, "5 4"));
        assertTrue(boardFour.validMove(1, "5 3"));
        
        // OO
        // O
        // O
        boardFour.makeMove(1, "4 3");
        boardFour.makeMove(2, "5 3");
        boardFour.makeMove(3, "4 4");
        boardFour.makeMove(4, "4 5");
        assertTrue(boardFour.validMove(1, "5 2"));
        assertTrue(boardFour.validMove(1, "6 3"));
        assertTrue(boardFour.validMove(1, "5 4"));
        assertFalse(boardFour.validMove(1, "5 5"));
        assertTrue(boardFour.validMove(1, "4 6"));
        assertTrue(boardFour.validMove(1, "3 5"));
        assertTrue(boardFour.validMove(1, "3 4"));

        // O
        // OO
        //  O
        boardFour.makeMove(1, "4 3");
        boardFour.makeMove(2, "4 4");
        boardFour.makeMove(3, "5 4");
        boardFour.makeMove(4, "5 5");
        assertTrue(boardFour.validMove(1, "5 3"));
        assertFalse(boardFour.validMove(1, "6 4"));
        assertFalse(boardFour.validMove(1, "6 5"));
        assertFalse(boardFour.validMove(1, "5 6"));
        assertTrue(boardFour.validMove(1, "4 5"));
        assertTrue(boardFour.validMove(1, "3 4"));
        
        assertTrue(boardFour.checkValid(1, "5 3 h"));
        
        // OOO
        //  O
        boardFour.makeMove(1, "4 3");
        boardFour.makeMove(2, "5 3");
        boardFour.makeMove(3, "6 3");
        boardFour.makeMove(4, "5 4");
        assertTrue(boardFour.validMove(1, "5 2"));
        assertTrue(boardFour.validMove(1, "6 2"));
        assertTrue(boardFour.validMove(1, "7 3"));
        assertFalse(boardFour.validMove(1, "6 4"));
        assertFalse(boardFour.validMove(1, "5 5"));
        assertTrue(boardFour.validMove(1, "4 4"));        
        
        // OO
        // OO
        boardFour.makeMove(1, "4 3");
        boardFour.makeMove(2, "5 3");
        boardFour.makeMove(3, "4 4");
        boardFour.makeMove(4, "5 4");
        assertTrue(boardFour.validMove(1, "5 2"));
        assertTrue(boardFour.validMove(1, "6 3"));
        assertFalse(boardFour.validMove(1, "6 4"));
        assertFalse(boardFour.validMove(1, "5 5"));
        assertTrue(boardFour.validMove(1, "4 5"));
        assertTrue(boardFour.validMove(1, "3 4"));
    }
    @Test
    public void testKick() throws Exception{
        assertEquals(4,boardFour.getPlayerCount());
        boardFour.kick(1);
        assertEquals(3,boardFour.getPlayerCount());
        boardFour.kick(2);
        assertEquals(2,boardFour.getPlayerCount());
        boardFour.kick(3);
        assertEquals(1,boardFour.getPlayerCount());
    }
    
    @Test
    public void testHasWon() throws Exception{
        boardFour.makeMove(1, "4 8");
        assertTrue(boardFour.hasWon(1));
        boardFour.makeMove(1, "4 7");
        assertFalse(boardFour.hasWon(1));
        boardFour.kick(1);
        assertFalse(boardFour.hasWon(2));
        boardFour.kick(2);
        assertFalse(boardFour.hasWon(3));
        boardFour.kick(3);
        assertTrue(boardFour.hasWon(4));
    }
    
    @Test
    public void testGetShortestPath() throws Exception{
        GraphPath<Vertex,Edge> p1Path = boardTwo.getShortestWinningPath(1,boardTwo.board).getPath();
        GraphPath<Vertex,Edge> p2Path = boardTwo.getShortestWinningPath(2,boardTwo.board).getPath();
        assertEquals("should be the same length",(int)p1Path.getWeight(), (int)p2Path.getWeight());
        boardTwo.checkValid(1,"4 7 h");
        boardTwo.checkValid(1,"2 7");
        boardTwo.checkValid(1, "5 7 V");
        boardTwo.checkValid(1, "0 6");
        p2Path = boardTwo.getShortestWinningPath(2,boardTwo.board).getPath();
        assertNotEquals("should Not be the same length",(int)p1Path.getWeight(), (int)p2Path.getWeight());
    }
    
    @Test
    public void testPathLengthAfterWall() throws Exception{
        int pathLength = (int)boardTwo.getShortestWinningPath(1,boardTwo.board).getPathLength();
        int pathLengthAfterWall = boardTwo.pathLengthAfterWall(1, "4 0 H");
        assertNotEquals(pathLength,pathLengthAfterWall);
        
    }
}
