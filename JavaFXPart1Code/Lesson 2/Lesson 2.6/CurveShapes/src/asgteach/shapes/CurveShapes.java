// CurveShapes.java - QuadCurve, CubicCurve shapes
package asgteach.shapes;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;

public class CurveShapes extends Application {
    
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        
        QuadCurve quadCurve = new QuadCurve();
        quadCurve.setStartX(100);
        quadCurve.setStartY(125);
        quadCurve.setEndX(200);
        quadCurve.setEndY(125);
        quadCurve.setControlX(150);
        quadCurve.setControlY(25);
        quadCurve.setFill(Color.CORAL);
        quadCurve.setStroke(Color.BLUE);

        CubicCurve cubicCurve = new CubicCurve();
        cubicCurve.setStartX(300);
        cubicCurve.setStartY(125);
        cubicCurve.setControlX1(325);
        cubicCurve.setControlY1(-25);
        cubicCurve.setControlX2(450);
        cubicCurve.setControlY2(275);
        cubicCurve.setEndX(475);
        cubicCurve.setEndY(125);
        cubicCurve.setFill(Color.CORAL);
        cubicCurve.setStroke(Color.BLUE);
        
        root.getChildren().addAll(quadCurve, cubicCurve);
        
        Scene scene = new Scene(root, 550, 250, Color.LIGHTGREEN);
        stage.setTitle("Curve Shapes");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
