// MyFadeTransition.java - Fade Transition
package asgteach.animation;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
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

public class MyFadeTransition extends Application {

    @Override
    public void start(Stage stage) {

        final Double startOpacity = 1.0;
        final Double endOpacity = 0.2;

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

        FadeTransition fadeTransition =
                new FadeTransition(Duration.seconds(2), stackPane);

        fadeTransition.setOnFinished(event -> {
            System.out.format("End opacity = %.1f\n",
                    fadeTransition.getNode().getOpacity());
        });

        Button fadeButton = new Button("Fade");
        fadeButton.setOnAction(event -> {
            if (fadeTransition.getNode().getOpacity() == startOpacity)
                fadeTransition.setToValue(endOpacity);
            else
                fadeTransition.setToValue(startOpacity);
            fadeTransition.playFromStart();
        });

        fadeButton.disableProperty().bind(fadeTransition.statusProperty()
                .isEqualTo(Animation.Status.RUNNING));
        
        VBox vbox = new VBox(stackPane, fadeButton); 
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: lightblue");

        Scene scene = new Scene(vbox, 400, 300);
        stage.setTitle("Fade Transition");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
