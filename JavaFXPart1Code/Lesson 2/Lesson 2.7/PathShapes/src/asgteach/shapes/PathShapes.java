// PathShapes.java - Path shapes
package asgteach.shapes;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.QuadCurveTo;
import javafx.stage.Stage;

public class PathShapes extends Application {
    
    @Override
    public void start(Stage stage) {
        Group root = new Group();

        MoveTo move1 = new MoveTo(100, 150);
        QuadCurveTo quadCurve = new QuadCurveTo();
        quadCurve.setX(200);
        quadCurve.setY(150);
        quadCurve.setControlX(150);
        quadCurve.setControlY(50);
        
        Path path1 = new Path(
                new PathElement[]{ move1, quadCurve });
        path1.setFill(Color.WHITE);
        path1.setStrokeWidth(3);

        MoveTo move2 = new MoveTo(200, 150);
        LineTo line1 = new LineTo(100, 150);
        LineTo line2 = new LineTo(150, 275);
        LineTo line3 = new LineTo(200, 150);
        
        Path path2 = new Path(new PathElement[]{ 
            move2, line1, line2, line3 });
        path2.setFill(Color.GOLDENROD);
        path2.setStrokeWidth(3);
        
        root.getChildren().addAll(path1, path2);
        
        Scene scene = new Scene(root, 300, 400, Color.LIGHTGREEN);
        stage.setTitle("Path Shapes");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
