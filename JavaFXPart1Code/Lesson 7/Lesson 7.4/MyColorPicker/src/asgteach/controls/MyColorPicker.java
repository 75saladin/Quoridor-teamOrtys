// MyColorPicker.java - ColorPicker control
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyColorPicker extends Application {

    @Override
    public void start(Stage stage) {

        Label label = new Label("Choose a Color");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(event -> label.setTextFill(colorPicker.getValue()));
        
        VBox vbox = new VBox(30, label, colorPicker);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #e6e6e6");

        Scene scene = new Scene(vbox, 400, 200);
        stage.setTitle("ColorPicker Control"); 
        
        //label.textFillProperty().bind(colorPicker.valueProperty());
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
