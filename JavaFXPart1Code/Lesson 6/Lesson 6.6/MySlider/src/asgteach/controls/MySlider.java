// MySlider.java - Slider control
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MySlider extends Application {

    private final SepiaTone sepiaTone = new SepiaTone();
    private final int sceneWidth = 450;
    private final int sceneHeight = 350;
    
    @Override
    public void start(Stage stage) {
        
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15, 10, 20, 10));
        borderPane.setStyle("-fx-background-color: lightgray");
        borderPane.setCenter(drawImage());
        borderPane.setLeft(drawBorder(""));
        borderPane.setRight(drawBorder(""));
        borderPane.setTop(drawBorder("Tree Photo"));  
        borderPane.setBottom(drawSlider("Sepia Tone"));

        Scene scene = new Scene(borderPane, sceneWidth, sceneHeight);
        stage.setTitle("Slider Control");
        stage.setScene(scene);
        stage.show();
    }
    
    private Region drawImage() {
        Image myImage = new Image("resources/trees.jpg");
        ImageView imageView = new ImageView(myImage);
        imageView.setFitHeight(sceneHeight-120);
        imageView.setPreserveRatio(true);
        imageView.setEffect(sepiaTone);
        return new StackPane(imageView);
    }
    
    private Region drawBorder(String myText) {
        Text text = new Text(myText);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        return new StackPane(text);
    }
    
    private Region drawSlider(String myText) {
        Label sepiaLabel = new Label(myText);
        sepiaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        Slider slider = new Slider(0, 1, 0);
        slider.setMinorTickCount(1);
        slider.setMajorTickUnit(.5);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        Label sepiaValue = new Label("value");
        sepiaValue.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        HBox hbox = new HBox(25, sepiaLabel, slider, sepiaValue);
        hbox.setAlignment(Pos.CENTER);
        
        sepiaTone.levelProperty().bind(slider.valueProperty());
        sepiaValue.textProperty().bind(slider.valueProperty().asString("%.2f"));
        return hbox;
    }

    public static void main(String[] args) {
        launch();
    }
}
