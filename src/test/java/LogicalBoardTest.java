import org.junit.*;
import java.util.Random;
import java.util.Set;
import static org.junit.Assert.*;

public class LogicalBoardTest {

    LogicalBoard board;

    @Before
    public void setUp() throws Exception {
        board = new LogicalBoard(2);
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
    
    public void placeHorizontalWallShouldRemoveCorrectEdges() throws Exception {
        Vertex src = board.getVertexByCoord(1,1);
        Vertex below = board.getVertexByCoord(1,2);
        Vertex right = board.getVertexByCoord(2,1);
        Vertex belowRight = board.getVertexByCoord(2,2);
        
        assertTrue(board.board.containsEdge(src, right));
        assertTrue(board.board.containsEdge(right, belowRight));
        assertTrue(board.board.containsEdge(belowRight, below));
        assertTrue(board.board.containsEdge(below, src));
        
        board.placeWall("1 1 h");
        
        assertTrue(board.board.containsEdge(src, right));
        assertFalse(board.board.containsEdge(right, belowRight));
        assertTrue(board.board.containsEdge(belowRight, below));
        assertFalse(board.board.containsEdge(below, src));
    }
    
    public void placeVerticalWallShouldRemoveCorrectEdges() throws Exception {
        Vertex src = board.getVertexByCoord(1,1);
        Vertex below = board.getVertexByCoord(1,2);
        Vertex right = board.getVertexByCoord(2,1);
        Vertex belowRight = board.getVertexByCoord(2,2);
        
        assertTrue(board.board.containsEdge(src, right));
        assertTrue(board.board.containsEdge(right, belowRight));
        assertTrue(board.board.containsEdge(belowRight, below));
        assertTrue(board.board.containsEdge(below, src));
        
        board.placeWall("1 1 v");
        
        assertFalse(board.board.containsEdge(src, right));
        assertTrue(board.board.containsEdge(right, belowRight));
        assertFalse(board.board.containsEdge(belowRight, below));
        assertTrue(board.board.containsEdge(below, src));
    }
    
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
}