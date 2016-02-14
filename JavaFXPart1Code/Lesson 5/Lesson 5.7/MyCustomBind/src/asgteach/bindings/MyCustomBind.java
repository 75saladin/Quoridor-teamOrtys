// MyCustomBind.java - Custom binding for Rectangle size, opacity
package asgteach.bindings;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyCustomBind extends Application {

    @Override
    public void start(Stage stage) {
               
        DropShadow dropShadow = new DropShadow();                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        Rectangle rectangle = new Rectangle(100, 50, Color.RED);
        rectangle.setArcHeight(30);
        rectangle.setArcWidth(30);
        
        Text text = new Text();
        text.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        
        StackPane stackPane = new StackPane(rectangle, text);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setEffect(dropShadow);

        final Scene scene = new Scene(stackPane, 400, 200, Color.LIGHTGREEN);
        stage.setTitle("Custom Binding"); 
        
        rectangle.widthProperty().bind(scene.widthProperty().divide(2));
        rectangle.heightProperty().bind(scene.heightProperty().divide(2));
        
        DoubleBinding opacityBinding = new DoubleBinding() {
            {
                super.bind(scene.widthProperty(), scene.heightProperty());
            }
            
            @Override
            protected double computeValue() {
                double opacity = (scene.getWidth() + scene.getHeight()) / 1000;
                return (opacity > 1.0) ? 1.0 : opacity;
            }
            
        };
        rectangle.opacityProperty().bind(opacityBinding);
        text.textProperty().bind(opacityBinding.asString());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
