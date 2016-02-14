// MyComboBox.java - ComboBox control
package asgteach.controls;
import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyComboBox extends Application {

    @Override
    public void start(Stage stage) {

        DropShadow dropShadow = new DropShadow();                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
      
        Label label = new Label("Select a Font");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Tahoma", "Verdana", "Cambria");
        comboBox.setEditable(true);
        //comboBox.setPromptText("New Font");
        comboBox.setVisibleRowCount(4);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setEffect(dropShadow);
        
        HBox choices = new HBox(40, label, comboBox);
        choices.setAlignment(Pos.CENTER);
        
        Text text = new Text("Here is some text to view.");
        text.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));

        VBox vbox = new VBox(40, text, choices);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: palegreen");

        Scene scene = new Scene(vbox, 500, 300);
        stage.setTitle("ComboBox Control");

        ObjectBinding<Font> fontBinding = new ObjectBinding<Font>() {
            {
                super.bind(comboBox.valueProperty());
            }

            @Override
            protected Font computeValue() {
                String newFont = comboBox.getValue();
                if (!comboBox.getItems().contains(newFont))
                    comboBox.getItems().add(newFont);
                return Font.font(newFont, FontWeight.BOLD, 25);
            }
        };
        
        text.fontProperty().bind(fontBinding);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
