// MyButton.java - Button control
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyButton extends Application {

    private final VBox vbox = new VBox(40);
    
    @Override
    public void start(Stage stage) {
                  
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
      
        Label label = new Label("Change Background Color");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        
        Button yellowButton = makeButton("Yellow");
        Button cyanButton = makeButton("Cyan");
        //yellowButton.setDefaultButton(true);
        //cyanButton.setCancelButton(true);
        
        HBox buttons = new HBox(40, yellowButton, cyanButton); 
        buttons.setAlignment(Pos.CENTER);
        buttons.setEffect(dropShadow);
        
        vbox.setStyle("-fx-background-color: palegreen");
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, buttons);
        
        Scene scene = new Scene(vbox, 400, 200);
        stage.setTitle("Button Control");
        
        stage.setScene(scene);
        stage.show();
    }
    
    private Button makeButton(String colorName) {
        Button button = new Button(colorName);
        Image image = new Image("resources/" + colorName + ".png");
        button.setGraphic(new ImageView(image));
        button.setOnAction(event -> vbox.setStyle("-fx-background-color: " 
                    + colorName));
        return button;
    }

    public static void main(String[] args) {
        launch();
    }
}
