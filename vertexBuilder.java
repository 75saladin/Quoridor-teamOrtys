
public class vertexBuilder{
   
    int r;
    int c;

    public vertexBuilder(){
	this.r =0;
	this.c =0;
    }
    
    public Vertex createVertex(){
	Vertex temp = new Vertex(this.r,this.y);
	this.r++;
	if(this.r == 9)
	    
    }


}


    class Vertex{

	int [] vertex= new int[2];

	public Vertex(int r, int c){
	    this.vertex[0]=r;
	    this.vertex[1]=c;
	}
    }
