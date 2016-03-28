import org.jgrapht.*;

public class GridGraphTest {
    
    public static void main(String[] args) {
	
	UndirectedGraph<BoardNode, DefaultEdge> udg = 
	    new SimpleGraph<BoardNode, DefaultEdge>(DefaultEdge.class);
	    
	VertexFactory<BoardNode> factory = 
	    new ClassBasedVertexFactory<BoardNode>(BoardNode.class);
	    
	GridGraphGenerator<BoardNode, DefaultEdge> gen = 
	    new GridGraphGenerator<BoardNode, DefaultEdge>(8, 8);
	    
	gen.generateGraph(udg, factory, null);
    }

}

public class BoardNode {
    public int col;
    public int row;
    
    public BoardNode(int col, int row) {
	this.col = col;
	this.row = row;
    }
    
    public BoardNode()  {
	this(-1, -1);
    }
    
    //In order to be the class that is passed to ClassBasedVertexFactory, it 
    //must have a method newInstance(), or else the VertexFactory will fail.
    //GridGraphGenerator will call VertexFactory.createVertex(), which in turn
    //calls BoardNode.newInstance(). It is required of us that we create this
    //method in such a way that it returns nodes in the correct order to be
    //added to the grid graph. GridGraphGenerator generates a grid starting at
    //the top left, moving right then down. Therefore, our first node must be, 
    //as per protocol.txt, (0,0), then (1,0), then (2,0)... (8,0), then (1,0),
    //then (1,1), (1,2)... and so on. (The last node wil be (8, 8)).
    
    //As it turns out, all classes have a newInstance() inherited from Class.
    //It calls the empty constuctor, which is all it can really do since
    //newInstance() gets called fresh every time. This is a problem: how do we
    //get it to return a unique, correct BoardNode every time?? The way I see
    //it, all we can do is have it do is make 64 identical, meaningless 
    //BoardNodes.
}