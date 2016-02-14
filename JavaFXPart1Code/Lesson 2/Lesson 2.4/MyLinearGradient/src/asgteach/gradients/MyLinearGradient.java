// MyLinearGradient.java - Linear Gradients
package asgteach.gradients;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MyLinearGradient extends Application {
    
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        
        Rectangle rectangle1 = makeRectangle(40, 0, 1);     // vertical
        Rectangle rectangle2 = makeRectangle(200, 1, 0);    // horizontal
        Rectangle rectangle3 = makeRectangle(360, .1, .6);  // diagonal
        
        root.getChildren().addAll(rectangle1, rectangle2, rectangle3);
        
        Scene scene = new Scene(root, 600, 500, Color.LIGHTGRAY);
        stage.setTitle("Linear Gradients");
        stage.setScene(scene);
        stage.show();
    }
    
    private Rectangle makeRectangle(double y, double scaleX, double scaleY) {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(180);
        rectangle.setY(y);
        rectangle.setWidth(220);
        rectangle.setHeight(100);
        rectangle.setArcHeight(30);
        rectangle.setArcWidth(30);
        rectangle.setFill(doLinearGradient(scaleX, scaleY));
        return rectangle;
    }
    
    private LinearGradient doLinearGradient(double scaleX, double scaleY) {
        LinearGradient linearGradient = new LinearGradient(
                180,                        // startX
                40,                         // startY
                180 + scaleX*220,           // endX
                40 + scaleY*100,            // endY
                false,                      // proportional
                CycleMethod.REFLECT,        // cycleMethod
                new Stop(0.0, Color.ORANGE), new Stop(1.0, Color.BROWN));   // stops
        return linearGradient;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
