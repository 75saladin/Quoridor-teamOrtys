// MyToggleButton.java - ToggleButton control
package asgteach.controls;
import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyToggleButton extends Application {

    private final VBox vbox = new VBox(40);
    private final ToggleGroup toggleGroup = new ToggleGroup();
    
    @Override
    public void start(Stage stage) {
             
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
      
        Label label = new Label("Change Background Color");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        
        ToggleButton yellowButton = makeToggleButton("Yellow");
        ToggleButton cyanButton = makeToggleButton("Cyan");
        ToggleButton orangeButton = makeToggleButton("Orange");
        
        HBox buttons = new HBox(40, yellowButton, cyanButton, orangeButton); 
        buttons.setAlignment(Pos.CENTER);
        buttons.setEffect(dropShadow);
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, buttons);
        vbox.setStyle("-fx-background-color: palegreen");
        
        toggleGroup.getToggles().addAll(yellowButton, cyanButton, orangeButton);
        
        Scene scene = new Scene(vbox, 400, 200);
        stage.setTitle("ToggleButton Control");
        
        StringBinding colorBinding = new StringBinding() {
            {
                super.bind(toggleGroup.selectedToggleProperty());
            }

            @Override
            protected String computeValue() {
                Toggle toggle = toggleGroup.getSelectedToggle();
                return "-fx-background-color: " + (toggle != null ?
                    (String)toggle.getUserData() : "palegreen");
            }
        };
        
        //vbox.styleProperty().bind(colorBinding);
        
        stage.setScene(scene);
        stage.show();
    }
    
    private ToggleButton makeToggleButton(String colorName) {
        ToggleButton button = new ToggleButton(colorName);
        button.setUserData(colorName);
        button.setOnAction(event -> {
            String bgColor = toggleGroup.getSelectedToggle() != null ? 
                    colorName : "palegreen";
            vbox.setStyle("-fx-background-color: " + bgColor);
        });
        return button;
    }

    public static void main(String[] args) {
        launch();
    }
}
