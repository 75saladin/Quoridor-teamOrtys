
import java.util.Set;

public class LogicalBoardTest{

    
    public static void main(String[]args){
        LogicalBoard board = new LogicalBoard(2);
        Set<Vertex> vertexSet = board.getVertexSet();
        for(Vertex v : vertexSet)
            System.out.println(v);
        
    }
}
