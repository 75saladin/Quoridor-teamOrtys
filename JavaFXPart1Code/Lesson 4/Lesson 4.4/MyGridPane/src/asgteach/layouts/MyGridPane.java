// MyGridPane.java - GridPane layout
package asgteach.layouts;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MyGridPane extends Application {

    @Override
    public void start(Stage stage) {
       
        Color colors[] = { Color.ORANGE, Color.BLACK, Color.RED,
            Color.PINK, Color.WHITE, Color.GREEN, 
            Color.CYAN, Color.BLUE, Color.MAGENTA
        };
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(25);
        gridPane.setVgap(25);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setEffect(dropShadow);
        //gridPane.setGridLinesVisible(true);
        
        int index = 0;
        gridPane.add(new Circle(50, colors[index++]), 0, 0);
        gridPane.add(new Rectangle(50, 220, colors[index++]), 1, 0, 1, 2);
        gridPane.add(new Circle(40, colors[index++]), 2, 0);
        gridPane.add(new Rectangle(50, 70, colors[index++]), 0, 1);
        gridPane.add(new Rectangle(60, 40, colors[index++]), 2, 1);
        gridPane.add(new Rectangle(180, 100, colors[index++]), 0, 2, 2, 1);
        gridPane.add(new Circle(30, colors[index++]), 2, 2);
        
        gridPane.getChildren().stream().forEach((node) -> {
            GridPane.setHalignment(node, HPos.CENTER);
            GridPane.setValignment(node, VPos.CENTER);
        });

        Scene scene = new Scene(gridPane, 400, 450, Color.LIGHTGREEN);
        stage.setTitle("GridPane Layout");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
