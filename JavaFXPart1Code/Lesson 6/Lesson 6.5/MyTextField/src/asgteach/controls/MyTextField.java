// MyTextField.java - TextField control
package asgteach.controls;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyTextField extends Application {
    
    @Override
    public void start(Stage stage) {
               
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
      
        Label inputLabel = new Label("Phone Number");
        inputLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        
        TextField textField = new TextField();
        textField.setMaxWidth(150);
        textField.setEffect(dropShadow);
        
        HBox firstBox = new HBox(15, inputLabel, textField); 
        firstBox.setAlignment(Pos.CENTER);
        
        Label validLabel = new Label();
        validLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        
        Label outputLabel = new Label();
        outputLabel.setMaxWidth(150);
        outputLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        
        HBox secondBox = new HBox(15, validLabel, outputLabel); 
        secondBox.setAlignment(Pos.CENTER);
        
        VBox vbox = new VBox(40, firstBox, secondBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: palegreen");
        
        Scene scene = new Scene(vbox, 400, 200);
        stage.setTitle("TextField Control");
        
        outputLabel.textProperty().bind(textField.textProperty());
        
        textField.setOnAction(event -> {
            String input = textField.getText();
            Pattern pattern = Pattern.compile("^\\d{3}[\\.-]?\\d{3}[\\.-]?\\d{4}$");
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches())
                validLabel.setText("Valid Phone:");
            else
                validLabel.setText("Invalid Phone:");
        });
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
