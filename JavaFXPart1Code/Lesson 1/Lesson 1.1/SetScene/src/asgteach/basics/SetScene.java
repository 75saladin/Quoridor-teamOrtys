//  SetScene.java - Hello from JavaFX
package asgteach.basics;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SetScene extends Application {
    
    @Override 
    public void start(Stage stage) {
 
        // Create root Group
        Group root = new Group();
        
        // Create Scene with background color
        Scene scene = new Scene(root, 500, 250, Color.LIGHTGREEN);
        
        // Set scene on stage with title
        stage.setTitle("Hello JavaFX");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
