// MyRadioButton.java - RadioButton control
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyRadioButton extends Application {

    private final StackPane stackPane = new StackPane();
    private final DropShadow dropShadow = new DropShadow();
    
    @Override
    public void start(Stage stage) {
                        
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
      
        Label label = new Label("Change Background Color");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        
        RadioButton yellowButton = makeRadioButton("Yellow");
        RadioButton cyanButton = makeRadioButton("Cyan");
        RadioButton orangeButton = makeRadioButton("Orange");
        
        VBox buttons = new VBox(20, label,
                yellowButton, cyanButton, orangeButton);
        buttons.setAlignment(Pos.CENTER_LEFT);
        buttons.setMaxWidth(225);
        
        stackPane.setStyle("-fx-background-color: palegreen");
        stackPane.getChildren().add(buttons);
        
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(yellowButton, cyanButton, orangeButton);
        
        Scene scene = new Scene(stackPane, 400, 200);
        stage.setTitle("RadioButton Control");
        
        stage.setScene(scene);
        stage.show();
    }
    
    private RadioButton makeRadioButton(String colorName) {
        RadioButton button = new RadioButton(colorName);
        button.setEffect(dropShadow);
        button.setOnAction(event -> stackPane.setStyle("-fx-background-color: " 
                + colorName));
        return button;
    }

    public static void main(String[] args) {
        launch();
    }
}
