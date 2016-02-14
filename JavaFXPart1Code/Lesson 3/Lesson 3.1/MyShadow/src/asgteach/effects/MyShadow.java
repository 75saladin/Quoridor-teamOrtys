// MyShadow.java - Shadow effect
package asgteach.effects;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Shadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyShadow extends Application {
    
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        
        Shadow shadow = new Shadow();
//        shadow.setWidth(11);
//        shadow.setHeight(11);
        shadow.setRadius(5);
//        shadow.setColor(Color.DARKBLUE);
        
        Text text = new Text(150, 50, "Shadow");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        text.setFill(Color.DARKRED);
        text.setEffect(shadow);
        
        Circle circle = new Circle(180, 120, 40, Color.DODGERBLUE);
        circle.setEffect(shadow);
        
        Rectangle rectangle = new Rectangle(260, 80, 80, 80);
        rectangle.setFill(Color.LIGHTGREEN);
        rectangle.setEffect(shadow);
        
        root.getChildren().addAll(text, circle, rectangle);
//        root.setEffect(shadow);
        
        Scene scene = new Scene(root, 500, 200, Color.LIGHTYELLOW);
        stage.setTitle("Shadow Effect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
