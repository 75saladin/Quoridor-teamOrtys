// MyStackPane.java - StackPane layout
package asgteach.layouts;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyStackPane extends Application {      

    @Override
    public void start(Stage stage) {
        
        DropShadow dropShadow = new DropShadow();                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        Rectangle square1 = new Rectangle(225, 225, Color.ORANGE);
        square1.setArcHeight(30);
        square1.setArcWidth(30);
        square1.setEffect(dropShadow);
        
        Rectangle square2 = new Rectangle(150, 150, Color.LIGHTGREEN);
        square2.setArcHeight(30);
        square2.setArcWidth(30);
        square2.setEffect(dropShadow);
        
        Circle circle = new Circle(50, Color.GREEN);
        circle.setEffect(dropShadow);
        
        Text text = new Text("Center");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        text.setFill(Color.WHITE);
        text.setEffect(dropShadow);
        
        StackPane stackPane = new StackPane(square1, square2, circle, text);
        //stackPane.setEffect(dropShadow);

        Scene scene = new Scene(stackPane, 400, 400, Color.LIGHTSKYBLUE);
        stage.setTitle("StackPane Layout");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
