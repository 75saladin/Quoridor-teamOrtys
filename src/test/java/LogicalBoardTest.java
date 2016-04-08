import org.junit.*;
import java.util.Random;
import java.util.Set;
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
        
            boardTwo.placeWall(1, wall);
            
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
            boardTwo.removeWall(wall);
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
	    
            assertTrue(boardTwo.validWall(0,wallString));
	    assertEquals(boardTwo.getEdgesToRemove(wallString).size(), 0);
	    boardTwo.removeWall(wallString);
	}
    }
    
    @Test
    public void validWallAcceptsValidWalls() throws Exception {
        Random rand = new Random();
        String direction;
        String wallString;
        for (int i=0; i<20; i++) {
            if (rand.nextInt(2)==0)
                direction = "h";
            else
                direction = "v";  
            wallString = ""+rand.nextInt(8)+" "+rand.nextInt(8)+" "+direction;
            assertTrue("Wall "+wallString+" was judged invalid on iteration "+i, boardTwo.validWall(0,wallString));
	    boardTwo.removeWall(wallString); //removing the wall on the same board makes this fail ALWAYS ON ITERATION 10
        }
        
        //testing filling board with horizontal walls (except rightmost column)
        for (int c=0; c<8; c+=2) {
            for (int r=0; r<8; r++) {
                wallString = ""+c+" "+r+" "+"h";
                assertTrue("Wall "+wallString+" was judged invalid", boardTwo.validWall(0,wallString));
            }
        }
        
        this.setUp(); //freshen up that board
        
        //testing filling board with vertical walls (except bottommost row)
        for (int r=0; r<8; r+=2) {
            for (int c=0; c<8; c++) {
                wallString = ""+c+" "+r+" "+"v";
                assertTrue("Wall "+wallString+" was judged invalid", boardTwo.validWall(0,wallString));
            }
        }
    }
    
    @Test
    public void validWallRejectsInvalidWalls() throws Exception {
	//Wall out of bounds: <col/row> >7 or <0
	assertFalse(boardTwo.validWall(1,"-1 4 v"));
	assertFalse(boardTwo.validWall(1,"4 -1 h"));
	assertFalse(boardTwo.validWall(1,"8 4 v"));
	assertFalse(boardTwo.validWall(1,"4 8 h"));

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
	    
	    assertEquals(boardTwo.edgeSet().size(), 144);
	    assertTrue(boardTwo.validWall(0, wallString));
	    assertEquals(boardTwo.edgeSet().size(), 142);
	    assertFalse(boardTwo.validWall(0, badWall));
	    boardTwo.removeWall(wallString);
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
		       boardTwo.validWall(0, wallString));
	    assertFalse("Failed on iteration "+i+" on good wall "+wallString+" and bad wall "+badWall, 
			boardTwo.validWall(0, badWall));
	    boardTwo.removeWall(wallString);
	}

    }
    
    @Test
    public void validWallRejectsWinBlockingWall() throws Exception {
	boardTwo.validWall(1, "0 0 h");
	boardTwo.validWall(1, "2 0 h");
	boardTwo.validWall(1, "4 0 h");
	boardTwo.validWall(1, "6 0 h");
	assertFalse(boardTwo.validWall(1, "7 0 v"));
	
	assertTrue(boardTwo.validWall(1, "7 1 h"));
    }
    
    @Test
    public void validWallShouldDecrementPlayerCount2Players() throws Exception {
	assertEquals("Player 1 should start with 10 walls", boardTwo.getPlayer(1).getWalls(), 10);
	assertEquals("Player 2 should start with 10 walls", boardTwo.getPlayer(2).getWalls(), 10);
	assertTrue(boardTwo.validWall(1, "0 0 h"));
	assertTrue(boardTwo.validWall(1, "2 0 h"));
	assertTrue(boardTwo.validWall(2, "4 0 h"));
	assertTrue(boardTwo.validWall(1, "6 0 h"));
	assertTrue(boardTwo.validWall(1, "7 2 v"));// will not allow wall placed?
	
	assertEquals("Player 1 should have 6 walls left", boardTwo.getPlayer(1).getWalls(), 6);
	assertEquals("Player 2 should have 9 walls left", boardTwo.getPlayer(2).getWalls(), 9);
	
	boardTwo.validWall(1, "0 4 h");
	boardTwo.validWall(1, "2 4 h");
	boardTwo.validWall(1, "4 4 h");
	boardTwo.validWall(1, "6 4 h");
	boardTwo.validWall(1, "2 6 h");
	boardTwo.validWall(1, "4 6 h");

	assertEquals("Player 1 should have no walls left", boardTwo.getPlayer(1).getWalls(), 0);
	assertFalse(boardTwo.validWall(1, "4 6 h"));
    }
    
    @Ignore
    public void validMovesAcceptsValidMoves() throws Exception {
	//Moves in a circle, testing each direction
        assertTrue(boardTwo.getVertexByCoord(4,0).isPlayerHere());
        Player one = boardTwo.getPlayer(1);
        assertEquals(boardTwo.getPlayerNum(one),1);
        assertTrue(boardTwo.getVertexByCoord(4,8).isPlayerHere());
	assertTrue(boardTwo.validMove(1, "4 1"));  
        assertEquals(boardTwo.getPlayer(1).getR(),1);
        assertEquals(boardTwo.getPlayer(1).getC(),4);
        assertFalse(boardTwo.getVertexByCoord(4,0).isPlayerHere());
	assertTrue(boardTwo.validMove(1, "3 1"));
        assertEquals(boardTwo.getPlayer(1).getR(),1);
        assertEquals(boardTwo.getPlayer(1).getC(),3);
	assertTrue(boardTwo.validMove(1, "4 1"));
        assertEquals(boardTwo.getPlayer(1).getR(),1);
        assertEquals(boardTwo.getPlayer(1).getC(),4);
        assertFalse(boardTwo.getVertexByCoord(4,0).isPlayerHere());
	assertTrue(boardTwo.validMove(1, "4 0"));	
        assertEquals(boardTwo.getPlayer(1).getR(),0);
        assertEquals(boardTwo.getPlayer(1).getC(),4);
    }
    
    @Test
    public void validMovesRejectsInvalidMoves() throws Exception {
	//Moves too far
	assertFalse(boardTwo.checkValid(1, "4 2"));
	assertFalse(boardTwo.checkValid(1, "6 0"));
	assertFalse("should not be able to move to "+boardTwo.getVertexByCoord(2, 0).toString(),
                    boardTwo.checkValid(1, "2 0"));
	
	//Tries to jump over a horizontal wall then a vertical wall
	assertTrue(boardTwo.checkValid(1, "3 0 h"));
	assertFalse(boardTwo.checkValid(1, "4 1"));
	assertTrue(boardTwo.validWall(1, "4 0 v"));
	assertFalse(boardTwo.checkValid(1, "5 0"));
	
	//Tries to leave the board
	assertFalse(boardTwo.validMove(1, "4 -1"));
	for (int i=4; i<9; i++)
	    boardTwo.validMove(1, i+" 0");
	assertFalse(boardTwo.validMove(1, "9 0"));
    }    
    
    @Test
    public void validMovesRejectsMoreInvalidMoves() throws Exception {
	//Move to current location
	assertFalse(boardTwo.validMove(1, "4 0"));
	
	//Can't move onto player 2
	for (int i=0; i<8; i++)
	    boardTwo.validMove(1, "4 "+i);
	assertFalse(boardTwo.validMove(1, "4 8"));
    }
    
    @Test
    public void jumping() throws Exception {
	boardTwo.makeMove(1, "4 4");
	boardTwo.makeMove(2, "4 5");
        assertTrue(boardTwo.getVertexByCoord(4, 5).here);
        assertTrue(boardTwo.getVertexByCoord(4, 4).here);
	assertTrue(boardTwo.validMove(1, "4 6"));
	boardTwo.makeMove(1, "4 4");
	//assertTrue(boardTwo.validMove(1, "5 5"));
	boardTwo.makeMove(1, "4 4");
	//assertTrue(boardTwo.validMove(1, "3 5"));
    }
    
    @Test
    public void jumpingOverAWallIsInvalid() throws Exception {
	boardTwo.makeMove(1, "4 4");
	boardTwo.makeMove(2, "4 5");
	assertTrue(boardTwo.validWall(1, "4 4 h"));// will not allow wall placement
	assertFalse(boardTwo.validMove(1, "4 6"));
    }
    
    @Test
    public void theMegaJump() throws Exception {
	boardFour.makeMove(1, "4 3");
	boardFour.makeMove(2, "4 4");
	boardFour.makeMove(3, "4 5");
	boardFour.makeMove(4, "4 6");
	
	assertTrue(boardFour.validMove(1, "4 7"));
	
    }
    
    @Test
    public void megaJumpingOverAWallIsInvalid() throws Exception {
	boardFour.makeMove(1, "4 3");
	boardFour.makeMove(2, "4 4");
	boardFour.makeMove(3, "4 5");
	boardFour.makeMove(4, "4 6");
	boardFour.validWall(1, "4 4 h");
	
	assertFalse(boardFour.validMove(1, "4 7"));
	
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
}
