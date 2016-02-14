// MyVBox.java - VBox layout
package asgteach.layouts;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MyVBox extends Application {

    @Override
    public void start(Stage stage) {
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));

        VBox vbox = new VBox(50);
        vbox.setAlignment(Pos.CENTER);
        
        Circle circle = new Circle(50, Color.BLUE);
        
        Rectangle square = new Rectangle(100, 100, Color.RED);
        square.setArcWidth(30);
        square.setArcHeight(30);
   
        vbox.getChildren().addAll(circle, square);
        vbox.setEffect(dropShadow);

        Scene scene = new Scene(vbox, 200, 400, Color.LIGHTGREEN);
        stage.setTitle("VBox Layout");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
