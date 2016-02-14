// MyLabel.java - Label control
package asgteach.controls;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MyLabel extends Application {

    @Override
    public void start(Stage stage) {

        Label label = new Label("Find Your Puzzles");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        label.setUnderline(true);
        label.setTextFill(Color.NAVY);
        label.setGraphic(new ImageView(new Image("resources/cube.png")));
        label.setGraphicTextGap(20);
        label.setWrapText(true);
        //label.setContentDisplay(ContentDisplay.TOP);
        //label.setTextAlignment(TextAlignment.CENTER);
        
        StackPane stackPane = new StackPane(label);
        stackPane.setStyle("-fx-background-color: palegreen");

        Scene scene = new Scene(stackPane, 400, 200);
        stage.setTitle("Label Control"); 
        
        label.opacityProperty().bind(
                scene.widthProperty().add(scene.heightProperty())
                        .divide(1000));
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
