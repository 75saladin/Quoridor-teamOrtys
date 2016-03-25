import org.junit.*;
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
        assertEquals("Board has incorrect number of verticies", board.board.vertexSet().size(), 81);
        assertEquals("Board has incorrect number of edges", board.board.edgeSet().size(), 144);
    }
}