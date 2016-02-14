// MyImageInput.java - ImageInput effect
package asgteach.effects;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MyImageInput extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        
        Image myImage = new Image("resources/trees.jpg", 425, 340, true, true);
        ImageInput imageInput = new ImageInput(myImage, 10, 10);

        GaussianBlur blur = new GaussianBlur(3);
        blur.setInput(imageInput);
        
        root.setEffect(blur);

        Scene scene = new Scene(root, 445, 360);
        stage.setTitle("ImageInput Effect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
