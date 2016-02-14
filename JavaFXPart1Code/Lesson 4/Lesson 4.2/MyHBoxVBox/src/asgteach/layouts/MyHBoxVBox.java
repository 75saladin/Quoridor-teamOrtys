// MyHBoxVBox.java - HBox, VBox layout
package asgteach.layouts;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MyHBoxVBox extends Application {
    
    private final Color colors[] = { Color.ORANGE, Color.BLACK, Color.RED,
            Color.PINK, Color.WHITE, Color.GREEN, Color.CYAN, Color.BLUE
    };

    @Override
    public void start(Stage stage) {
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));

        HBox hbox1 = new HBox(10);
        hbox1.setAlignment(Pos.CENTER);
        firstHBox(hbox1, 5);
        
        HBox hbox2 = new HBox(10);
        hbox2.setAlignment(Pos.CENTER);
        secondHBox(hbox2, 4);
        
        VBox vbox = new VBox(20, hbox1, hbox2);
        vbox.setAlignment(Pos.CENTER);
        vbox.setEffect(dropShadow);

        Scene scene = new Scene(vbox, 400, 200, Color.LIGHTGREEN);
        stage.setTitle("HBoxVBox Layout");
        stage.setScene(scene);
        stage.show();
    }
    
    private void firstHBox(HBox hbox, int count) {
        for (int i = 0; i < count; i++) {
            Rectangle square = new Rectangle(60, 60, colors[i]);
            square.setArcWidth(30);
            square.setArcHeight(30);
            hbox.getChildren().add(square);
        }
    }
    
    private void secondHBox(HBox hbox, int count) {
        for (int i = 0; i < count; i++) {
            if (i % 2 != 0) {
                Circle circle = new Circle(30, colors[i+3]);
                hbox.getChildren().add(circle);
                continue;
            }
            Rectangle rectangle = new Rectangle(95, 60, colors[i+3]);
            rectangle.setArcWidth(30);
            rectangle.setArcHeight(30);
            hbox.getChildren().add(rectangle);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
