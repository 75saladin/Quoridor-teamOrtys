// MyFluentBind.java - Fluent binding for Rectangle size, opacity
package asgteach.bindings;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MyFluentBind extends Application {

    @Override
    public void start(Stage stage) {
               
        DropShadow dropShadow = new DropShadow();                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        Rectangle rectangle = new Rectangle(100, 50, Color.RED);
        rectangle.setArcHeight(30);
        rectangle.setArcWidth(30);
        
        StackPane stackPane = new StackPane(rectangle);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setEffect(dropShadow);

        Scene scene = new Scene(stackPane, 400, 200, Color.LIGHTGREEN);
        stage.setTitle("Fluent Binding"); 
        
        rectangle.widthProperty().bind(scene.widthProperty().divide(2));
        rectangle.heightProperty().bind(scene.heightProperty().divide(2));
        
        rectangle.opacityProperty().bind(
                scene.widthProperty().add(scene.heightProperty())
                        .divide(1000));

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
