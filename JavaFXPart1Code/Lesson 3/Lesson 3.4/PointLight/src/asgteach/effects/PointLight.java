// PointLight.java - Lighting effect, Pointlight
package asgteach.effects;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PointLight extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        Light.Point pointLight = new Light.Point(200, 100, 50, Color.WHITE);
        
        Lighting lighting = new Lighting(pointLight);
        lighting.setSurfaceScale(5);
        lighting.setSpecularExponent(5);

        Text text = new Text(80, 270, "Point Light");
        text.setFont(Font.font("Tahoma", FontWeight.BOLD, 60));
        text.setFill(Color.YELLOW);
        text.setEffect(lighting);
        
        Circle circle = new Circle(250, 250, 200, Color.LIGHTGREEN);
        circle.setEffect(lighting);
        
        root.getChildren().addAll(circle, text);

        Scene scene = new Scene(root, 500, 500, Color.LIGHTYELLOW);
        stage.setTitle("PointLight Effect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
