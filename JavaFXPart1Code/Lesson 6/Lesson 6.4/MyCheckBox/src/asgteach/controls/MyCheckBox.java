// MyCheckBox.java - CheckBox control
package asgteach.controls;
import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyCheckBox extends Application {

    @Override
    public void start(Stage stage) {

        DropShadow dropShadow = new DropShadow();                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
      
        Label choose = new Label("Please Choose Your Item");
        choose.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

        CheckBox shoes = new CheckBox("Shoes");
        CheckBox hat = new CheckBox("Hat");
        CheckBox belt = new CheckBox("Belt");
        //belt.setIndeterminate(true);
        //belt.setAllowIndeterminate(true);
                
        VBox boxes = new VBox(20, shoes, hat, belt);
        boxes.setEffect(dropShadow);
        
        Label selections = new Label();
        selections.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

        VBox vbox = new VBox(20, choose, boxes, selections);
        vbox.setAlignment(Pos.CENTER_LEFT);
        vbox.setMaxWidth(225);
        
        StackPane stackPane = new StackPane(vbox);
        stackPane.setStyle("-fx-background-color: palegreen");

        Scene scene = new Scene(stackPane, 400, 220);
        stage.setTitle("CheckBox Control");

        StringBinding choiceBinding = new StringBinding() {
            CheckBox[] checkBoxes = { shoes, hat, belt };
            {
                for (CheckBox checkBox : checkBoxes)
                    super.bind(checkBox.selectedProperty());
            }

            @Override
            protected String computeValue() {
                StringBuilder choices = new StringBuilder();
                for (CheckBox checkBox : checkBoxes)
                    if (checkBox.isSelected())
                        choices.append(checkBox.getText()).append(" ");
                return choices.toString();
            }
        };
        
        selections.textProperty().bind(choiceBinding);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
