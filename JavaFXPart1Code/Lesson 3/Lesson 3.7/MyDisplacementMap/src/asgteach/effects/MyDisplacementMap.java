// MyDisplacementMap.java - MyDisplacementMap effect
package asgteach.effects;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.FloatMap;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyDisplacementMap extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        FloatMap map = new FloatMap(220, 100);
        for (int i = 0; i < map.getWidth(); i++) {
            double value = Math.sin(i/30.0 * Math.PI) / 10.0;
            for (int j = 0; j < map.getHeight(); j++)
                map.setSamples(i, j, 0.0f, (float)value);
        }

        DisplacementMap displacementMap = new DisplacementMap(map);
        //displacementMap.setScaleY(.3);

        Text text = new Text(65, 110, "Groovy Text");
        text.setFont(Font.font("Tahoma", FontWeight.BOLD, 50));
        text.setFill(Color.FIREBRICK);
        text.setEffect(displacementMap);
        
        root.getChildren().add(text);

        Scene scene = new Scene(root, 450, 200, Color.LIGHTYELLOW);
        stage.setTitle("DisplacementMap Effect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
