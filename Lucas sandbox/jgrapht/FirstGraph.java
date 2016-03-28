import org.jgrapht.*;
import org.jgrapht.graph.*; //This was necessary, despite the first line

//compiling in a way that allows you to import classes from the third-party library's jar file:
//      javac -classpath ...../jgrapht-0.9.1/jgrapht-core-0.9.1.jar FirstGraph.java

public class FirstGraph {
    public static void main(String[] args) {
        UndirectedGraph<String, DefaultEdge> ude = 
            new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
        
        //This will be a simple graph of four classmates. Edge = they know each other.
        String a = "Ada";
        String b = "Bob";
        String c = "Chris";
        String d = "Dan";
        
        ude.addVertex(a);
        ude.addVertex(b);
        ude.addVertex(c);
        ude.addVertex(d);
        
        ude.addEdge(a, b); //Ada knows Bob (not directional!)
        ude.addEdge(a, c); //Ada knows Chris
        ude.addEdge(a, d); //Ada knows Dan
        ude.addEdge(b, d); //Bob knows Dan
    }
}