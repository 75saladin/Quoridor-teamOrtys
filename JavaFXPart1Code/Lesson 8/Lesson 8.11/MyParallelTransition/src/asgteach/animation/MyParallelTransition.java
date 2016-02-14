// MyParallelTransition.java - Parallel Transition
package asgteach.animation;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.beans.binding.When;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MyParallelTransition extends Application {
    
    @Override
    public void start(Stage stage) {
        
        Group circle1 = MyCircle.build(Color.CRIMSON, "1", 90, 60);
        Group circle2 = MyCircle.build(Color.CHARTREUSE, "2", 310, 60);
        Group circle3 = MyCircle.build(Color.ORANGE, "3", 310, 190);
        Group circle4 = MyCircle.build(Color.DODGERBLUE, "4", 90, 190);
        
        Group circleGroup = new Group(circle1, circle2, circle3, circle4);
        StackPane stackPane = new StackPane(circleGroup);
        
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                MyTransition.buildX(circle1, 220),
                MyTransition.buildY(circle2, 130),
                MyTransition.buildX(circle3, -220),
                MyTransition.buildY(circle4, -130));
        
        SequentialTransition seqTransition = new SequentialTransition();
        seqTransition.setCycleCount(Animation.INDEFINITE);
        seqTransition.setAutoReverse(true);
        seqTransition.getChildren().addAll(
                new PauseTransition(Duration.millis(175)),
                parallelTransition,
                new PauseTransition(Duration.millis(175))
        );
        
        Button startPauseButton = new Button("Start");
        startPauseButton.setPrefWidth(80);
        startPauseButton.setOnAction(event -> {
            if (seqTransition.getStatus() == Animation.Status.RUNNING)
                seqTransition.pause();
            else
                seqTransition.play();
        });
        
        startPauseButton.textProperty().bind(
            new When(seqTransition.statusProperty()
                .isEqualTo(Animation.Status.RUNNING))
                    .then("Pause").otherwise("Start"));
        
        VBox vbox = new VBox(20, stackPane, startPauseButton); 
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: "
                + "linear-gradient(aliceblue, lightblue);");
        
        Scene scene = new Scene(vbox, 420, 300);
        stage.setTitle("Parallel Transition");
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
