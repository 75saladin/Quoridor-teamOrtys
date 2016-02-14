// MyRotateTransition.java - Rotate Transition
package asgteach.animation;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.binding.When;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MyRotateTransition extends Application {

    @Override
    public void start(Stage stage) {

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.GRAY);

        Rectangle rectangle = new Rectangle(200, 100, Color.ORANGE);
        rectangle.setStrokeWidth(5);
        rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
        rectangle.setStroke(Color.DARKGOLDENROD);
        rectangle.setEffect(dropShadow);

        Text text = new Text("My Rectangle");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        text.setEffect(new Reflection());

        StackPane stackPane = new StackPane(rectangle, text);
        stackPane.setPrefHeight(200);
        stackPane.setPrefWidth(400);

        RotateTransition rotateTransition =
                new RotateTransition(Duration.seconds(3), stackPane);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setCycleCount(4);
        //rotateTransition.setAxis(new Point3D(5, 5, 5));

        Button rotatePauseButton = new Button("Rotate");
        rotatePauseButton.setPrefWidth(80);
        rotatePauseButton.setOnAction(event -> {
            if (rotateTransition.getStatus() == Animation.Status.RUNNING)
                rotateTransition.pause();
            else
                rotateTransition.play();
        });

        rotatePauseButton.textProperty().bind(
            new When(rotateTransition.statusProperty()
                .isEqualTo(Animation.Status.RUNNING))
                    .then("Pause").otherwise("Rotate"));

        VBox vbox = new VBox(50, stackPane, rotatePauseButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: lightblue");

        Scene scene = new Scene(vbox, 400, 400);
        stage.setTitle("Rotate Transition");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
