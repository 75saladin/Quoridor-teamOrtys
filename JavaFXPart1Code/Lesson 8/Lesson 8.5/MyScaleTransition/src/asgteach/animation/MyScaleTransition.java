// MyScaleTransition.java - Scale Transition
package asgteach.animation;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MyScaleTransition extends Application {

    @Override
    public void start(Stage stage) {
        
        DropShadow dropShadow = new DropShadow();                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        Label label = new Label("Find Your Puzzles");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        label.setUnderline(true);
        label.setTextFill(Color.NAVY);
        label.setGraphic(new ImageView(new Image("resources/cube.png")));
        label.setGraphicTextGap(20);
        label.setWrapText(true);
        label.setScaleX(.5);
        label.setScaleY(.5);
        label.setOpacity(.75);
        
        ScaleTransition scaleTransition = 
                new ScaleTransition(Duration.seconds(1), label);
        
        StackPane stackPane = new StackPane(label);
        stackPane.setStyle("-fx-background-color: lightblue");
        
        Scene scene = new Scene(stackPane, 400, 200);
        stage.setTitle("Scale Transition");
             
        label.setOnMouseEntered(event -> {
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.playFromStart();
        });
        
        label.setOnMouseExited(event -> {
            scaleTransition.setToX(.5);
            scaleTransition.setToY(.5);
            scaleTransition.playFromStart();
        });
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
