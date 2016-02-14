// MyBlend.java - Blend effect
package asgteach.effects;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MyBlend extends Application {
    
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        
        Circle red = new Circle(250, 150, 50, Color.rgb(255, 0, 0, 1.0));
        Circle green = new Circle(200, 100, 50, Color.rgb(0, 255, 0, 0.5));
        Circle blue = new Circle(300, 100, 50, Color.rgb(0, 0, 255, 0.5));
        
        Blend blend = new Blend();
        blend.setMode(BlendMode.ADD);
        
        root.getChildren().addAll(red, green, blue);
        root.setEffect(blend);
        
        Scene scene = new Scene(root, 500, 250, Color.LIGHTYELLOW);
        stage.setTitle("Blend Effect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
