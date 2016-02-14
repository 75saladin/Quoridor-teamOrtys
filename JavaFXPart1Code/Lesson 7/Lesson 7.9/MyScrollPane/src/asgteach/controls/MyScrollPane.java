// MyScrollPane.java - ScrollPane control
package asgteach.controls;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MyScrollPane extends Application {

    @Override
    public void start(Stage stage) {
        
        Image myImage = new Image("resources/trees.jpg");
        ImageView imageView = new ImageView(myImage);
        imageView.setPreserveRatio(true);
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(imageView);
        scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        
        Scene scene = new Scene(scrollPane, 450, 350);
        stage.setTitle("ScrollPane Control"); 

        scrollPane.vvalueProperty().addListener((observableValue, 
               oldValue, newValue) -> System.out.println(newValue));
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
