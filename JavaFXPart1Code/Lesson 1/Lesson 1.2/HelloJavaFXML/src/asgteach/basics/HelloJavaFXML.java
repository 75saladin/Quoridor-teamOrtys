// HelloJavaFXML.java - Main application
package asgteach.basics;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloJavaFXML extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        // Load components from FMXL file
        Parent root = FXMLLoader.load(getClass().getResource("HelloJavaFX.fxml"));
        
        // Create Scene with background color
        Scene scene = new Scene(root, 500, 250, Color.LIGHTYELLOW);
        
        // Set scene on stage with title
        stage.setTitle("Hello JavaFXML");
        stage.setScene(scene);
        stage.show();
    }
      
    public static void main(String[] args) {
        launch(args);
    }
}
