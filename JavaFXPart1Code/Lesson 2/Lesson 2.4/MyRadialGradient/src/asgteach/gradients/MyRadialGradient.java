// MyRadialGradient.java - Radial gradients
package asgteach.gradients;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MyRadialGradient extends Application {
    
    @Override
    public void start(Stage stage) {
        Group root = new Group();

        Circle circle1 = makeCircle(80,.25,.75);
        Circle circle2 = makeCircle(200,.5,.5);
        Circle circle3 = makeCircle(320,.75,.25);
        
        Scene scene = new Scene(root, 400, 200, Color.LIGHTGRAY);
        stage.setTitle("Radial Gradients");
        stage.setScene(scene);
        stage.show();
        
        root.getChildren().addAll(circle1, circle2, circle3);
    }
    
    private Circle makeCircle(double centerX, double x, double y) {
        Circle circle = new Circle(40);    // radius 40
        circle.setCenterX(centerX);
        circle.setCenterY(100);
        circle.setFill(doRadialGradient(x,y));
        return circle;
    }
    
    private RadialGradient doRadialGradient(double x, double y) {
        RadialGradient radialGradient = new RadialGradient(
                0,                      // focusAngle
                0,                      // focusDistance
                x,                      // centerX 
                y,                      // centerY 
                .5,                     // radius 
                true,                   // proportional 
                CycleMethod.NO_CYCLE,   // cycleMethod 
                new Stop(0.0, Color.WHITE), new Stop(1.0, Color.DARKGREEN)); // stops
        return radialGradient;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
