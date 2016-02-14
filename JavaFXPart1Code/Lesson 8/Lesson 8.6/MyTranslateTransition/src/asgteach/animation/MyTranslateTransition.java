// MyTranslateTransition.java - Translate Transition
package asgteach.animation;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.binding.When;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyTranslateTransition extends Application {

    @Override
    public void start(Stage stage) {

        final int sceneWidth = 400;
        final int sceneHeight = 200;

        TranslateTransition circleTransition =
                MyCircle.build(Color.CHARTREUSE, sceneWidth-100, 0.0);

        Button startPauseButton = new Button("Start");
        startPauseButton.setPrefWidth(80);
        startPauseButton.setOnAction(event -> {
            if (circleTransition.getStatus() == Animation.Status.RUNNING)
                circleTransition.pause();
            else
                circleTransition.play();
        });

        startPauseButton.textProperty().bind(
            new When(circleTransition.statusProperty()
                .isEqualTo(Animation.Status.RUNNING))
                    .then("Pause").otherwise("Start"));
        
        HBox hbox = new HBox(startPauseButton);
        hbox.setAlignment(Pos.CENTER);
        
        VBox vbox = new VBox(40, circleTransition.getNode(), hbox);
        vbox.setAlignment(Pos.CENTER_LEFT);
        vbox.setPadding(new Insets(40, 20, 20, 20));
        vbox.setStyle("-fx-background-color: "
                + "linear-gradient(aliceblue, lightblue);");

        Scene scene = new Scene(vbox, sceneWidth, sceneHeight);
        stage.setTitle("Translate Transition");
        
        scene.widthProperty().addListener(observable -> {
            Animation.Status status = circleTransition.getStatus();
            circleTransition.stop();
            circleTransition.setToX(scene.getWidth()-100.);
            if (status == Animation.Status.RUNNING)
                circleTransition.play();
        });
         
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
