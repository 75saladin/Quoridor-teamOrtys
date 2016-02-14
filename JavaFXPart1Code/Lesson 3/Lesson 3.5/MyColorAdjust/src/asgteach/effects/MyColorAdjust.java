// MyColorAdjust.java - ColorAdjust effect
package asgteach.effects;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MyColorAdjust extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(-0.05);
        colorAdjust.setContrast(0.1);
        colorAdjust.setBrightness(0.1);
        colorAdjust.setSaturation(0.2);

        Image myImage = new Image("resources/trees.jpg");
        ImageView imageView = new ImageView(myImage);
        imageView.setFitWidth(425);
        imageView.setPreserveRatio(true);
        imageView.setEffect(colorAdjust);
        
        root.getChildren().add(imageView);

        Scene scene = new Scene(root, 425, 340);
        stage.setTitle("ColorAdjust Effect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
