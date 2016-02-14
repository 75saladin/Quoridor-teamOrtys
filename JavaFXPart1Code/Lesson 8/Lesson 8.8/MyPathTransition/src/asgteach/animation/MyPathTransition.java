// MyPathTransition.java - PathTransition
package asgteach.animation;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.beans.binding.When;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MyPathTransition extends Application {
    
    @Override
    public void start(Stage stage) {
        final double maxRate = 7.0;
        final double minRate = .3;
        final double rateDelta = .3;
        final int startX = 0;
        final int startY = 0;
        final int pathStrokeWidth = 15;

        IntegerProperty lapCounterProperty = new SimpleIntegerProperty(0);
        PathTransition pathTransition
                = MyRectangle.build(startX, startY, pathStrokeWidth);
        
        Line startLine = new Line(startX - 25, startY, startX + 10, startY);
        startLine.setStrokeWidth(4);
        startLine.setStroke(Color.BLUE);
        startLine.setStrokeLineCap(StrokeLineCap.ROUND);
        startLine.setEffect(new DropShadow(10, 5, 5, Color.GRAY));

        Text text = new Text();
        text.setFont(Font.font("Verdana", 16));
        text.setEffect(new Reflection());

        Group myGroup = new Group();
        myGroup.getChildren().addAll(pathTransition.getPath(), startLine,
                pathTransition.getNode());
        
        StackPane stackPane = new StackPane(myGroup, text);
        
        pathTransition.currentTimeProperty().addListener(
            (ObservableValue<? extends Duration> observableValue,
                Duration oldValue, Duration newValue) -> {
                    if (oldValue.greaterThan(newValue))
                        lapCounterProperty.set(lapCounterProperty.get() + 1);
                });

        text.textProperty().bind(lapCounterProperty.asString("Lap Counter: %s"));

        Button startPauseButton = new Button();
        startPauseButton.setPrefWidth(80);
        startPauseButton.setOnAction(event -> {
            if (pathTransition.getStatus() == Animation.Status.RUNNING)
                pathTransition.pause();
            else
                pathTransition.play();
        });
        
        startPauseButton.textProperty().bind(
            new When(pathTransition.statusProperty()
                .isEqualTo(Animation.Status.RUNNING))
                    .then("Pause").otherwise("Start"));

        Button fasterButton = new Button(" >> ");
        fasterButton.setOnAction(event -> {
            double currentRate = pathTransition.getRate();
            if (currentRate >= maxRate)
                return;
            pathTransition.setRate(currentRate + rateDelta);
            System.out.printf("faster rate = %.2f\n", pathTransition.getRate());
        });

        Button slowerButton = new Button(" << ");
        slowerButton.setOnAction(event -> {
            double currentRate = pathTransition.getRate();
            if (currentRate <= minRate)
                return;
            pathTransition.setRate(currentRate - rateDelta);
            System.out.printf("slower rate = %.2f\n", pathTransition.getRate());
        });

        fasterButton.disableProperty().bind(pathTransition.statusProperty()
                .isNotEqualTo(Animation.Status.RUNNING));
        slowerButton.disableProperty().bind(pathTransition.statusProperty()
                .isNotEqualTo(Animation.Status.RUNNING));

        HBox buttonBox = new HBox(20, slowerButton, startPauseButton, fasterButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        VBox vbox = new VBox(20, stackPane, buttonBox);
        vbox.setAlignment(Pos.CENTER); 
        vbox.setStyle("-fx-background-color: lightblue;");
        
        Scene scene = new Scene(vbox, 400, 300);
        stage.setTitle("Path Transition");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }   
}
