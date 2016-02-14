// MyDropShadow.java - DropShadow effect
package asgteach.effects;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyDropShadow extends Application {
    
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        Text text = new Text(100, 50, "Drop Shadow");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        text.setFill(Color.DARKGREEN);             
        
        Circle circle = new Circle(180, 120, 40, Color.DODGERBLUE);
        
        Rectangle rectangle = new Rectangle(260, 80, 80, 80);
        rectangle.setFill(Color.LIGHTGREEN);
        
        root.getChildren().addAll(text, circle, rectangle);
        root.setEffect(dropShadow);
        
        Scene scene = new Scene(root, 500, 200, Color.LIGHTYELLOW);
        stage.setTitle("DropShadow Effect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
