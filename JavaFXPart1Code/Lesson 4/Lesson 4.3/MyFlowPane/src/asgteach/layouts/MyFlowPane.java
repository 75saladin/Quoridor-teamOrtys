// MyFlowPane.java - FlowPane layout
package asgteach.layouts;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MyFlowPane extends Application {

    @Override
    public void start(Stage stage) {
       
        Color colors[] = { Color.ORANGE, Color.BLACK, Color.RED,
            Color.PINK, Color.WHITE, Color.GREEN, Color.CYAN, Color.BLUE
        };
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL);
        //FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.setPadding(new Insets(50, 50, 50, 50));
        flowPane.setHgap(25);
        flowPane.setVgap(25);
        flowPane.setAlignment(Pos.CENTER_LEFT);
        //flowPane.setAlignment(Pos.TOP_CENTER);
        flowPane.setEffect(dropShadow);
        
        for (int i = 0; i < colors.length; i++) {
            if (i < colors.length/2) {
                Rectangle square = new Rectangle(80, 80, colors[i]);
                square.setArcWidth(30);
                square.setArcHeight(30);
                flowPane.getChildren().add(square);
                continue;
            }
            Circle circle = new Circle(25, colors[i]);
            flowPane.getChildren().add(circle);
        }

        Scene scene = new Scene(flowPane, 420, 400, Color.LIGHTGREEN);
        stage.setTitle("FlowPane Layout");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
