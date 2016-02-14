// LineShapes.java - Line shapes
package asgteach.shapes;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

public class LineShapes extends Application {
    
    @Override
    public void start(Stage stage) {
        
        Group root = new Group();
        
        Line line1 = new Line(20, 50, 400, 50);
        line1.setStroke(Color.GREEN);
        line1.setStrokeWidth(10);
        
        Line line2 = new Line(20, 80, 400, 80);
        line2.setStroke(Color.BLUE);
        line2.setStrokeWidth(10);
        line2.setStrokeLineCap(StrokeLineCap.ROUND);
        
        Line line3 = new Line(20, 110, 400, 110);
        line3.setStroke(Color.RED);
        line3.getStrokeDashArray().addAll(20d, 10d);
        line3.setStrokeWidth(5);
        
        root.getChildren().addAll(line1, line2, line3);
        
        Scene scene = new Scene(root, 450, 200, Color.LIGHTYELLOW);
        stage.setTitle("Line Shapes");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
