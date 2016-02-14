// SpotLight.java - Lighting effect, Spotlight
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

public class SpotLight extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        Light.Spot spotLight = new Light.Spot(200, 25, 50, 1, Color.WHITE);
        spotLight.setPointsAtX(200);    
        spotLight.setPointsAtY(400);
        spotLight.setPointsAtZ(10);

        Lighting lighting = new Lighting(spotLight);
        lighting.setSurfaceScale(5);
        lighting.setSpecularExponent(10);
        lighting.setSpecularConstant(2);

        Text text = new Text(90, 270, "Spot Light");
        text.setFont(Font.font("Tahoma", FontWeight.BOLD, 60));
        text.setFill(Color.YELLOW);
        text.setEffect(lighting);
        
        Circle circle = new Circle(250, 250, 200, Color.LIGHTGREEN);
        circle.setEffect(lighting);
        
        root.getChildren().addAll(circle, text);

        Scene scene = new Scene(root, 500, 500, Color.LIGHTYELLOW);
        stage.setTitle("SpotLight Effect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
