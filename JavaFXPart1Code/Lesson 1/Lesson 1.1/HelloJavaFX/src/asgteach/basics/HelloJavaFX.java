//  HelloJavaFX.java - Hello from JavaFX
package asgteach.basics;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloJavaFX extends Application {
    
    @Override 
    public void start(Stage stage) {
        StackPane root = new StackPane();

        // Create DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setOffsetY(3.0);
        
        // Create rounded Rectangle
        Rectangle rectangle = new Rectangle(400, 60, Color.LIGHTSKYBLUE);
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);
        rectangle.setEffect(dropShadow);
        
        // Create Text object
        Text text = new Text("Hello from JavaFX !");
        text.setFill(Color.DARKSLATEGRAY);
        text.setFont(Font.font(null, FontWeight.BOLD, 32));
        text.setEffect(dropShadow);
        
        // Add Rectangle and Text to StackPane
        root.getChildren().addAll(rectangle, text);
         
        // Fade Text node in and out
        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), text);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setCycleCount(Animation.INDEFINITE);
        fade.setAutoReverse(true);
        fade.play();
        
        // Create Scene with background color
        Scene scene = new Scene(root, 500, 250, Color.LIGHTYELLOW);
        
        // Set scene on stage with title
        stage.setTitle("Hello JavaFX");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
