// MySepiaTone.java - SepiaTone effect
package asgteach.effects;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ImageInput;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MySepiaTone extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        SepiaTone sepiaTone = new SepiaTone();
        sepiaTone.setLevel(.8);

        Image myImage = new Image("resources/trees.jpg", 425, 340, true, true);
        ImageInput imageInput = new ImageInput(myImage, 10, 10);
        sepiaTone.setInput(imageInput);
        root.setEffect(sepiaTone);
 
        Scene scene = new Scene(root, 445, 360);
        stage.setTitle("SepiaTone Effect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
