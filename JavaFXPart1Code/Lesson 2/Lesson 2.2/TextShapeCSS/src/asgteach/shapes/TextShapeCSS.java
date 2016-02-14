// TextShapeCSS.java - Text shape with CSS
package asgteach.shapes;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextShapeCSS extends Application {
    
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 650, 200, Color.LIGHTYELLOW);
        String cssFile = getClass().getResource("Text.css").toExternalForm();
        scene.getStylesheets().add(cssFile);
        
        Text text = new Text(50, 100, "Here's a Text String");
        text.setId("mytext");
        
        root.getChildren().add(text);
        
        stage.setTitle("Text Shape with CSS");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
